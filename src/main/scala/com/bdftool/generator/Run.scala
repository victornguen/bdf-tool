package com.bdftool.generator

import com.bdftool.parser.{ConfigParser, ConfigTypes, FakerTypesMapper}
import io.circe
import org.apache.spark.sql.{SaveMode, SparkSession}
import com.bdftool.implicits._
import java.nio.file.Paths

//noinspection ScalaCustomHdfsFormat
object Run extends App {

  implicit val spark: SparkSession = SparkSession
    .builder()
    .master("local[*]")
    .getOrCreate()
  val conf: Either[circe.Error, ConfigTypes.Config] =
    ConfigParser.fromSource("testData/config/testconf.json")
  conf.map { conf =>
    // setting variables from conf
    val batchSize    = conf.batchSize.getOrElse(1000)
    val outputPath   = Paths.get(conf.outputDir, conf.outputFileName).toString
    val format       = conf.writeFormat
    val n            = conf.rowNum
    val inOneFile    = conf.oneFile.getOrElse(false)
    val writeOptions = conf.writeOptions.getOrElse(Map())
    val steps: Int   = (n / batchSize).toInt

    // mapping fields from config to Faker functions
    val fields = FakerTypesMapper.configToFunctions(conf)

    // generating and writing data
    if (!inOneFile)
      (1 to steps).map { _ =>
        Generator
          .createDataframe(fields, batchSize)
          .writeWithOptions(writeOptions, format, SaveMode.Append, outputPath)
      } andThen { _ =>
        Generator
          .createDataframe(fields, (n % batchSize).toInt)
          .writeWithOptions(writeOptions, format, SaveMode.Append, outputPath)
      }
    else
      ((1 to steps)
        .map { i =>
          Generator.createDataframe(fields, batchSize)
        }
        .reduceLeft(_ union _) union Generator.createDataframe(fields, (n % batchSize).toInt))
        .writeWithOptions(writeOptions, format, SaveMode.Overwrite, outputPath)
  }
}
