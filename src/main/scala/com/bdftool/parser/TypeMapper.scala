package com.bdftool.parser

import faker._

import scala.util.matching.Regex

object TypeMapper {
  val typeWithNumericParam: Regex = "(?<name>\\w+)\\((?<arg>\\d+)\\)".r

  def mapType(str: String): () => String = () =>
    str match {
      case typeWithNumericParam(name, arg) =>
        name match {
        case  "words"      => Lorem.words(arg.toInt).mkString(" ")
        case  "paragraph"  => Lorem.paragraph(arg.toInt)
        case  "paragraphs" => Lorem.paragraphs(arg.toInt).mkString(" ")
        case  "sentence"   => Lorem.sentence(arg.toInt)
        case  "sentences"  => Lorem.sentences(arg.toInt).mkString(" ")
        case "zip_code" => Address.zip_code(arg)
      }
      case "username"                      => Internet.user_name
      case "email"                         => Internet.email
      case "domain_name"                   => Internet.domain_name
      case "domain_suffix"                 => Internet.domain_suffix
      case "domain_word"                   => Internet.domain_word
      case "free_email"                    => Internet.free_email
      case "ip_v4"                         => Internet.ip_v4_address
      case "ip_v6"                         => Internet.ip_v6_address
      case "words"                         => Lorem.words().mkString
      case "paragraph"                     => Lorem.paragraph()
      case "paragraphs"                    => Lorem.paragraphs().mkString
      case "sentence"                      => Lorem.sentence()
      case "sentences"                     => Lorem.sentences().mkString
      case "name"                          => Name.name
      case "first_name"                    => Name.first_name
      case "last_name"                     => Name.last_name
      case "suffix"                        => Name.suffix
      case "prefix"                        => Name.prefix
      case "company_name"                  => Company.name
      case "company_suffix"                => Company.suffix
      case "street_address"                => Address.street_address(false)
      case "street_name"                   => Address.street_name
      case "street_suffix"                 => Address.street_suffix
      case "secondary_address"             => Address.secondary_address
      case "city"                          => Address.city
      case "building_number"               => Address.building_number
      case "city_prefix"                   => Address.city_prefix
      case "city_suffix"                   => Address.city_suffix
      case "country"                       => Address.country
      case "country_code"                  => Address.country_code
      case "latitude"                      => Address.latitude
      case "longitude"                     => Address.longitude
      case "state_abbr"                    => Address.state_abbr
      case "zip_code"                      => Address.zip_code("")
      case "street_address_with_secondary" => Address.street_address(true)
      case "time_zone"                     => Address.time_zone
      case "coords"                        => Geo.coords.toString()
      case "phone_number"                  => PhoneNumber.phone_number
    }

}
