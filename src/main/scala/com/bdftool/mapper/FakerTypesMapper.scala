package com.bdftool.mapper

import com.bdftool.parser.ConfigTypes

import scala.collection.immutable.ListMap

object FakerTypesMapper extends App {

  def configToFunctions(config: ConfigTypes.Config): ListMap[String, () => String] = ListMap(
    config.fields.map(f => f.name -> TypeMapper.mapType(f.fType)).toArray: _*
  )
}
