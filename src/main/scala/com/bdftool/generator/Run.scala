package com.bdftool.generator

import com.bdftool.parser.{ConfigParser, FakerTypesMapper}
import org.apache.spark.sql.SparkSession

object Run {

  val spark:SparkSession = SparkSession.builder()
    .master("local[*]")
    .getOrCreate()
  val conf = ConfigParser.fromSource(getClass.getResource("testconf.json"))
  val r = conf.map{ conf =>
    FakerTypesMapper.configToFunctions(conf)
  }.map{ fields =>
    Generator.generateData(fields, 100)
  }.map{ println
  }
}
