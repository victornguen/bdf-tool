package com.bdftool.generator

import com.bdftool.implicits._
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}

import scala.collection.immutable.ListMap

object Generator {
  def generateData(fields: ListMap[String, () => String], nRows: Int): List[Seq[String]] = {
    val data = (1 to nRows).toList.map { _ =>
      fields.map { case (_, func) => func() }
    }
    data.map(_.toSeq)
  }

  def generateSchema(fields: ListMap[String, () => String]): StructType = StructType(
    fields.map { case (k, _) => StructField(k, StringType, nullable = true) }.toList
  )

  def createDataframe(fields: ListMap[String, () => String], nRows: Int)(implicit
      spark: SparkSession
  ): DataFrame = {
    val schema = generateSchema(fields)
    val data   = generateData(fields, nRows)
    val rows   = data.map(x => Row(x: _*))
    val rdd    = spark.sparkContext.parallelize(rows)

    spark.createDataFrame(rdd, schema)
  }

  def makeUDFs(fields: ListMap[String, () => String]): List[UserDefinedFunction] = {
    fields.map { case (_, func) =>
      udf(() => func())
    }.toList

  }

  def generateAndWriteData(fields: ListMap[String, () => String],
                           batchSize: Int,
                           steps: Int,
                           rowNum: Long,
                           writeOptions: Map[String, String],
                           format: String,
                           outputPath: String,
                           inOneFile: Boolean
  )(implicit spark: SparkSession): Unit = {
    val remainder = (rowNum % batchSize).toInt
    // generating and writing data
    (1 to steps).foreach { _ =>
      Generator
        .createDataframe(fields, batchSize)
        .writeWithOptions(writeOptions, format, SaveMode.Append, outputPath)
    }
    if (remainder > 0)
      Generator
        .createDataframe(fields, (rowNum % batchSize).toInt)
        .writeWithOptions(writeOptions, format, SaveMode.Append, outputPath)
  }
}
