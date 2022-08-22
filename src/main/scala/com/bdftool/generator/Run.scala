package com.bdftool.generator

import com.bdftool.configuration.ArgumentsParser
import com.bdftool.mapper.FakerTypesMapper
import com.bdftool.parser.ConfigParser
import org.apache.spark.sql.SparkSession

import java.nio.file.Paths

//noinspection ScalaCustomHdfsFormat
object Run extends App {

  implicit val spark: SparkSession = SparkSession
    .builder()
    .master("local[*]")
    .getOrCreate()

  val params = ArgumentsParser.parse(args)

  for {
    conf <- ConfigParser.fromSource(params.configPath)
    batchSize    = conf.batchSize.getOrElse(1000)
    outputPath   = Paths.get(conf.outputDir, conf.outputFileName).toString
    format       = conf.writeFormat
    n            = conf.rowNum
    inOneFile    = conf.oneFile.getOrElse(false)
    writeOptions = conf.writeOptions.getOrElse(Map())
    steps        = (n / batchSize).toInt
    fields       = FakerTypesMapper.configToFunctions(conf)

  } yield Generator.generateAndWriteData(fields,batchSize,steps,n, writeOptions,format,outputPath, inOneFile)



}
