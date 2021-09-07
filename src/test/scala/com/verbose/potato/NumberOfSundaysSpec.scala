package com.verbose.potato

import com.verbose.potato.NumberOfSundays._
import eu.timepit.refined.auto._
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers

final class NumberOfSundaysSpec extends AnyFunSuiteLike with Matchers {

  test("number of sundays between two dates") {
    assert(numberOfSundaysBetween("01-05-2021", "30-05-2021") == Right(5), "almost whole month")
    assert(numberOfSundaysBetween("01-05-2021", "05-05-2021") == Right(1), "5 days with sunday in between")
    assert(numberOfSundaysBetween("03-05-2021", "07-05-2021") == Right(0), "4 days without sunday in between")
    assert(numberOfSundaysBetween("02-05-2021", "02-05-2021") == Right(1), "same day (sunday)")
    assert(numberOfSundaysBetween("08-05-2021", "08-05-2021") == Right(0), "same day (not sunday)")

    assert(numberOfSundaysBetween("30-05-2021", "01-05-2021").isLeft, "start before end")
    assert(numberOfSundaysBetween("30-05-2021", "2021-05-01").isLeft, "invalid date format")
  }

}
