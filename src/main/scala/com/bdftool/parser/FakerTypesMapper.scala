package com.bdftool.parser

import faker._

object FakerTypesMapper extends App {
  def getFunctionForType[F](name:String): () => String = () =>
  TypeMaps.all(name)


  val typeWithNumericParam = "(?<name>\\w+)\\((?<arg>\\d+)\\)".r

  def lorem(str: String) = str match {
    case typeWithNumericParam(name, arg) =>
  }

  def uname() = Internet.user_name

  val r = getFunctionForType("username")
  val words = getFunctionForType("words")
  println(r())
  println(r())
  println(r())
  println(r())
  println(words())
  println(words())
  println(words())
}
