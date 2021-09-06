package com.verbose.potato

import com.verbose.potato.VerbosePotato._
import eu.timepit.refined.auto._
import eu.timepit.refined.types.numeric.PosInt
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.TableFor2

final class VerbosePotatoSpec extends AnyFunSuiteLike with Matchers {

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

  test("ordinal indicator suffix") {
    forAll(table)((number, suffix) => ordinalIndicatorSuffix(number) shouldBe suffix)
  }

}
