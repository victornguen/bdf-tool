package com.bdftool.parser

import com.bdftool.functions.using
import com.bdftool.parser.ConfigTypes._
import io.circe._
import io.circe.parser._
import io.circe.generic.auto._

import java.net.{URI, URL}
import scala.io.Source

object ConfigParser {
  def decodeJsonConfig(jsonString: String): Either[Error, Config] = decode[Config](jsonString)
  def decodeField(jsonString: String): Either[Error, Field]       = decode[Field](jsonString)

  def fromSource(path: String, encoding: String = "UTF-8"): Either[Error, Config] =
    using(Source.fromFile(path, encoding)) { source =>
      decodeJsonConfig(source.mkString)
    }

  def fromSource(url: URL): Either[Error, Config] = fromSource(url.getPath)
  def fromSource(uri: URI): Either[Error, Config] = fromSource(uri.getPath)

}
