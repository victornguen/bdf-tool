package com.bdftool.functions

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import scala.util.Random

object DataGeneration {
  def genUUID(length: Int): String = {
    assert(length > 0)
    (0 to length / 32)
      .map(_ => UUID.randomUUID())
      .foldLeft(""){(acc, uuid) => acc + uuid.toString} take length
  }

  def genDateBetween(from:String, to:String): LocalDate ={
    import java.time.temporal.ChronoUnit.DAYS
    val dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val fromDate = LocalDate.parse(from, dtf)
    val toDate= LocalDate.parse(to, dtf)
    val diff = DAYS.between(fromDate, toDate)
    val random = new Random(System.nanoTime)
    fromDate.plusDays(random.nextInt(diff.toInt))
  }

  def genNaturalNumber(length:Int): String = Random.alphanumeric.filter(_.isDigit).take(length).mkString

  def genBigInt(nBits:Int): BigInt = BigInt.apply(nBits, Random.self)

  def genAlphanumeric(length:Int): String = Random.alphanumeric.take(length).mkString


}
