package com.bdftool.parser

import faker._

object TypeMaps {

  val internetMap = Map(
    "username"      -> Internet.user_name,
    "email"         -> Internet.email,
    "domain_name"   -> Internet.domain_name,
    "domain_suffix" -> Internet.domain_suffix,
    "domain_word"   -> Internet.domain_word,
    "free_email"    -> Internet.free_email,
    "ip_v4"         -> Internet.ip_v4_address,
    "ip_v6"         -> Internet.ip_v6_address
  )

  val companyMap = Map(
    "company_name"   -> Company.name,
    "company_suffix" -> Company.suffix
  )

  val addressMap = Map(
    "street_address"                -> Address.street_address(false),
    "street_name"                   -> Address.street_name,
    "street_suffix"                 -> Address.street_suffix,
    "secondary_address"             -> Address.secondary_address,
    "city"                          -> Address.city,
    "building_number"               -> Address.building_number,
    "city_prefix"                   -> Address.city_prefix,
    "city_suffix"                   -> Address.city_suffix,
    "country"                       -> Address.country,
    "country_code"                  -> Address.country_code,
    "latitude"                      -> Address.latitude,
    "longitude"                     -> Address.longitude,
    "state_abbr"                    -> Address.state_abbr,
    "zip_code"                      -> Address.zip_code(""),
    "street_address_with_secondary" -> Address.street_address(true),
    "time_zone"                     -> Address.time_zone
  )

  val geoMap = Map(
    "coords" -> Geo.coords.toString()
  )

  val loremMap = Map(
    "words"      -> Lorem.words().mkString(" "),
    "paragraph"  -> Lorem.paragraph(3),
    "paragraphs" -> Lorem.paragraphs().mkString(" "),
    "sentence"   -> Lorem.sentence(4),
    "sentences"  -> Lorem.sentences().mkString(" ")
  )

  val phoneNumberMap = Map(
    "phone_number" -> PhoneNumber.phone_number
  )

  val nameMap = Map(
    "name"       -> Name.name,
    "first_name" -> Name.first_name,
    "last_name"  -> Name.last_name,
    "suffix"     -> Name.suffix,
    "prefix"     -> Name.prefix
  )

  val all =
    internetMap ++
      companyMap ++
      addressMap ++
      geoMap ++
      loremMap ++
      phoneNumberMap ++
      nameMap

  def mapType(str:String): () => String = () => str match {
    case "username"      => Internet.user_name
    case "email"         => Internet.email
    case "domain_name"   => Internet.domain_name
    case "domain_suffix" => Internet.domain_suffix
    case "domain_word"   => Internet.domain_word
    case "free_email"    => Internet.free_email
    case "ip_v4"         => Internet.ip_v4_address
    case "ip_v6"         => Internet.ip_v6_address
    case "words"      => Lorem.words().mkString
    case "paragraph"  => Lorem.paragraph()
    case "paragraphs" => Lorem.paragraphs().mkString
    case "sentence"   => Lorem.sentence()
    case "sentences"  => Lorem.sentences().mkString
  }

}
