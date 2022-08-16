package com.bdftool.generator

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import shapeless._
import syntax.std.traversable._

object Generator {
  def generateData(fields: Map[String, () => String], nRows: Long) = {
    val  data  =  (0 to nRows).toList.map { _ =>
      fields.map { case (name, func) => func() }
    }
    data
  }

  def generateSchema(fields:Map[String, () => String]): StructType = StructType(
    fields.keys.map(k => StructField(k, StringType, nullable = true)).toList
    )

}
