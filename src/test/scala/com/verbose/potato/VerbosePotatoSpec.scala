package com.verbose.potato

import com.verbose.potato.VerbosePotato._
import eu.timepit.refined.auto._
import eu.timepit.refined.types.numeric.PosInt
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.TableFor2

final class VerbosePotatoSpec extends AnyFunSuiteLike with Matchers {

  test("ordinal indicator suffix for a number") {
    val table: TableFor2[PosInt, String] =
      Table(
        ("number", "suffix"),
        (1, "1st"),
        (22, "22nd"),
        (33, "33rd"),
        (44, "44th"),
        (55, "55th"),
        (66, "66th"),
        (77, "77th"),
        (88, "88th"),
        (99, "99th"),
        (110, "110th")
      )

      forAll(table)((number, suffix) => ordinalIndicatorSuffix(number) shouldBe suffix)
  }

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
