package com.bdftool.generator

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

import scala.collection.immutable.ListMap

object Generator {
  def generateData(fields: ListMap[String, () => String], nRows: Int): List[Seq[String]] = {
    val  data  =  (1 to nRows).toList.map { _ =>
      fields.map { case (name, func) => func() }
    }
    data.map(_.toSeq)
  }

  def generateSchema(fields:ListMap[String, () => String]): StructType = StructType(
    fields.map{case (k, _) => StructField(k, StringType, nullable = true)}.toList
    )

  def createDataframe(fields:ListMap[String, () => String], nRows:Int)(implicit spark:SparkSession): DataFrame = {
    val schema = generateSchema(fields)
    val data = generateData(fields, nRows)
    val rows = data.map(x => Row(x:_*))
    val rdd = spark.sparkContext.parallelize(rows)

    spark.createDataFrame(rdd, schema)
  }


}
