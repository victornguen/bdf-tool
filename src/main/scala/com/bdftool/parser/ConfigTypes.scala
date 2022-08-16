package com.bdftool.parser

object ConfigTypes {
  case class Config(writeFormat: String,
                    outputFileName: String,
                    fields: List[Field],
                    rowNum: Long,
                    writeOptions: Map[String, String]
  )

  case class Field(name: String, fType: String)
}
