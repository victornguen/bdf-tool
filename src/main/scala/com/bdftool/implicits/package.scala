package com.bdftool

import org.apache.spark.sql.{DataFrame, SaveMode}

package object implicits {
  // noinspection ScalaCustomHdfsFormat
  implicit class DataFrameOps(df: DataFrame) {
    def writeWithOptions(writeOptions: Map[String, String], format: String, mode: SaveMode, outputPath: String): Unit = df
      .coalesce(1)
      .write
      .options(writeOptions)
      .format(format)
      .mode(mode)
      .save(outputPath)
  }
}
