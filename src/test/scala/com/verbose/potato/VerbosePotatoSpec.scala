package com.verbose.potato

import eu.timepit.refined.auto._
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.TableFor2

final class VerbosePotatoSpec extends AnyFunSuiteLike {

  val table: TableFor2[Int, String] =
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
    forAll(table)((number, suffix) => ordinalIndicatorSuffix(number) == suffix)
  }

}
