package com.bdftool.configuration

import scopt.OptionParser

object ArgumentsParser {
  case class CommandLineParams(configPath: String = "")

  val parser: OptionParser[CommandLineParams] =
    new OptionParser[CommandLineParams]("BigData Faker Tool") {
      head("bdf-tool", "0.1.0")

      opt[String]('c', "conf")
        .required()
        .valueName("<file>")
        .action((path, conf) => conf.copy(configPath = path))
    }

  def parse(args: Array[String]): CommandLineParams =
    parser.parse(args, CommandLineParams()) match {
      case Some(params) => params
      case None         => CommandLineParams()
    }

}
