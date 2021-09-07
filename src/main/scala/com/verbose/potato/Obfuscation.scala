package com.verbose.potato

import cats.implicits._
import eu.timepit.refined.auto._
import eu.timepit.refined.types.string.NonEmptyString

import scala.util.matching.Regex

object Obfuscation {

  val email: Regex =
    """(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])""".r

  final val phoneNumber: Regex = """\+?(?: *\d){9,}""".r

  def obfuscate: NonEmptyString => Either[String, String] = {
    case s if email.matches(s)       => Right(obfuscateEmail(s))
    case s if phoneNumber.matches(s) => Right(obfuscatePhoneNumber(s))
    case _                           => Left("cannot obfuscate value")
  }

  private def obfuscateEmail(s: NonEmptyString): String = {
    val Array(localPart, domainName)  = s.toLowerCase.split("@")
    val localPartFirstCharacter       = localPart.headOption.fold("")(_.toString)
    val localPartLastCharacter        = localPart.tail.lastOption.fold("")(_.toString)
    val localPartObfuscatedCharacters = localPart.drop(1).dropRight(1).map(_ => '*')
    s"$localPartFirstCharacter$localPartObfuscatedCharacters$localPartLastCharacter@$domainName"
  }

  private def obfuscatePhoneNumber(s: NonEmptyString): String = {
    val indexOfFirstOfLastFourNumbers =
      s.value.zipWithIndex    // characters with their indexes
        .filter(_._1.isDigit) // only keep digits
        .reverse              // reverse the list
        .apply(3)             // get the fourth digit of the reverse list (i.e the first of the last four digits of the original number
        ._2                   // get the index of that number

    val (firstPart, secondPart) = s.value.splitAt(indexOfFirstOfLastFourNumbers)

    val obfuscatedFirstPart: String =
      firstPart.map {
        case ' '            => '-'
        case c if c.isDigit => '*'
        case c              => c
      }

    val obfuscatedSecondPart: String =
      secondPart.map {
        case ' ' => '-'
        case c   => c
      }

    s"$obfuscatedFirstPart$obfuscatedSecondPart"
  }

}
