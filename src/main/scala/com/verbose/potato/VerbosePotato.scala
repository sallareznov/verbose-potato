package com.verbose.potato

import eu.timepit.refined.types.numeric.PosInt

import java.time.DayOfWeek._
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import scala.math.Ordering.Implicits.infixOrderingOps
import scala.util.Try

object VerbosePotato {

  def ordinalIndicatorSuffix(n: PosInt): String =
    n.value % 10 match {
      case 1 => s"${n}st"
      case 2 => s"${n}nd"
      case 3 => s"${n}rd"
      case _ => s"${n}th"
    }

  def numberOfSundaysBetween(dateFrom: String, dateTo: String): Either[String, Long] =
    (parseDate(dateFrom), parseDate(dateTo)) match {
      case (Some(d1), Some(d2)) if d1 > d2 => Left(s"dateFrom is after dateTo ($dateFrom < $dateTo)")
      case (Some(d1), Some(d2))            =>
        val isThereARemainingSunday = d1.getDayOfWeek == SUNDAY || d2.getDayOfWeek == SUNDAY || d2.getDayOfWeek < d1.getDayOfWeek
        if (isThereARemainingSunday) Right(1 + ChronoUnit.WEEKS.between(d1, d2)) else Right(ChronoUnit.WEEKS.between(d1, d2))
      case _                               => Left("invalid format for one or both dates")
    }

  def parseDate(d: String): Option[LocalDate] = Try(LocalDate.parse(d, DateTimeFormatter.ofPattern("dd-MM-yyyy"))).toOption

}
