package com.bdftool.parser

object ConfigTypes {
  case class Config(writeFormat: String,
                    outputDir: String,
                    outputFileName: String,
                    fields: List[Field],
                    rowNum: Long,
                    batchSize: Option[Int],
                    oneFile: Option[Boolean],
                    writeOptions: Option[Map[String, String]]
  )

  case class Field(name: String, fType: String)
}
