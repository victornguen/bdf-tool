package com.bdftool.mapper

import com.bdftool.functions.DataGeneration
import faker._

import java.util.UUID
import scala.util.matching.Regex

object TypeMapper {
  val typeWithNumericParam: Regex    = "(?<name>\\w+)\\((?<arg>\\d+)\\)".r
  val typeWithAlphabeticParam: Regex = "(?<name>\\w+)\\((?<arg>\\w+)\\)".r
  val typeWithTwoAlphabeticParam: Regex =
    "(?<name>\\w+)\\((?<arg1>\\w.+)\\)\\((?<arg2>\\w.+)\\)".r

  def mapType(str: String): () => String = () =>
    str match {
      case typeWithNumericParam(name, arg) =>
        name match {
          case "words"      => Lorem.words(arg.toInt).mkString(" ")
          case "paragraph"  => Lorem.paragraph(arg.toInt)
          case "paragraphs" => Lorem.paragraphs(arg.toInt).mkString(" ")
          case "sentence"   => Lorem.sentence(arg.toInt)
          case "sentences"  => Lorem.sentences(arg.toInt).mkString(" ")

          case "uuid" => DataGeneration.genUUID(arg.toInt)
          case "alphanumeric" => DataGeneration.genAlphanumeric(arg.toInt)
          case "natural" => DataGeneration.genNaturalNumber(arg.toInt)
          case "bigint" => DataGeneration.genBigInt(arg.toInt).toString()
        }
      case typeWithAlphabeticParam(name, arg) =>
        name match {
          case "zip_code" => Address.zip_code(arg)
        }
      case typeWithTwoAlphabeticParam(name, arg1, arg2) =>
        name match {
          case "date" => DataGeneration.genDateBetween(arg1, arg2).toString
        }
      case s if List("uuid", "id").contains(s)            => UUID.randomUUID().toString
      case "username"                                     => Internet.user_name
      case "email"                                        => Internet.email
      case "domain_name"                                  => Internet.domain_name
      case "domain_suffix"                                => Internet.domain_suffix
      case "domain_word"                                  => Internet.domain_word
      case "free_email"                                   => Internet.free_email
      case "ip_v4"                                        => Internet.ip_v4_address
      case "ip_v6"                                        => Internet.ip_v6_address
      case "words"                                        => Lorem.words().mkString
      case "paragraph"                                    => Lorem.paragraph()
      case "paragraphs"                                   => Lorem.paragraphs().mkString
      case "sentence"                                     => Lorem.sentence()
      case "sentences"                                    => Lorem.sentences().mkString
      case "name"                                         => Name.name
      case "first_name"                                   => Name.first_name
      case "last_name"                                    => Name.last_name
      case "suffix"                                       => Name.suffix
      case "prefix"                                       => Name.prefix
      case "company_name"                                 => Company.name
      case "company_suffix"                               => Company.suffix
      case "street_address"                               => Address.street_address(false)
      case "street_name"                                  => Address.street_name
      case "street_suffix"                                => Address.street_suffix
      case "secondary_address"                            => Address.secondary_address
      case "city"                                         => Address.city
      case "building_number"                              => Address.building_number
      case "city_prefix"                                  => Address.city_prefix
      case "city_suffix"                                  => Address.city_suffix
      case "country"                                      => Address.country
      case "country_code"                                 => Address.country_code
      case "latitude"                                     => Address.latitude
      case "longitude"                                    => Address.longitude
      case "state_abbr"                                   => Address.state_abbr
      case "zip_code"                                     => Address.zip_code("")
      case "street_address_with_secondary"                => Address.street_address(true)
      case "time_zone"                                    => Address.time_zone
      case "coords"                                       => Geo.coords.toString()
      case s if List("phone_number", "phone").contains(s) => PhoneNumber.phone_number
      case _ => throw new Exception("type unknown")
    }

}
