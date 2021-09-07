package com.verbose.potato

import eu.timepit.refined.types.string.NonEmptyString

import java.time.DayOfWeek._
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import scala.math.Ordering.Implicits._
import scala.util.Try

object NumberOfSundays {

  def numberOfSundaysBetween(dateFrom: NonEmptyString, dateTo: NonEmptyString): Either[String, Long] =
    (parseDate(dateFrom), parseDate(dateTo)) match {
      case (Some(d1), Some(d2)) if d1 > d2 => Left(s"dateFrom is after dateTo ($dateFrom < $dateTo)")
      case (Some(d1), Some(d2))            =>
        val isThereARemainingSunday = d1.getDayOfWeek == SUNDAY || d2.getDayOfWeek == SUNDAY || d2.getDayOfWeek < d1.getDayOfWeek
        if (isThereARemainingSunday) Right(1 + ChronoUnit.WEEKS.between(d1, d2)) else Right(ChronoUnit.WEEKS.between(d1, d2))
      case _                               => Left("invalid format for one or both dates")
    }

  private def parseDate(d: NonEmptyString): Option[LocalDate] = Try(
    LocalDate.parse(d.value, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
  ).toOption

}
