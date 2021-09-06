package com.verbose.potato

import com.verbose.potato.VerbosePotato._
import eu.timepit.refined.auto._
import eu.timepit.refined.types.numeric.PosInt
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.TableFor2

import java.time.LocalDate

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
    numberOfSundaysBetween(date("01-05-2021"), date("30-05-2021")) shouldBe 5
  }

  private def date(d: String): LocalDate = LocalDate.parse(d)

}
