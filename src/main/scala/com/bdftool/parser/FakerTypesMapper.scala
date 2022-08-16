package com.bdftool.parser

import faker._

object FakerTypesMapper extends App {

  def configToFunctions(config: ConfigTypes.Config): Map[String, () => String] =
    config.fields.map(f => f.name -> TypeMapper.mapType(f.fType)).toMap
}
