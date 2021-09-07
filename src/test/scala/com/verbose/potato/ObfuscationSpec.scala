package com.verbose.potato

import com.verbose.potato.Obfuscation._
import eu.timepit.refined.auto._
import eu.timepit.refined.types.string.NonEmptyString
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.TableFor2

final class ObfuscationSpec extends AnyFunSuiteLike with Matchers {

  test("email obfuscation") {
    val validEmails: TableFor2[NonEmptyString, String] =
      Table(
        ("email", "result"),
        ("simple@example.com", "s****e@example.com"),
        ("very.common@example.com", "v*********n@example.com"),
        ("disposable.style.email.with+symbol@example.com", "d********************************l@example.com"),
        ("other.email-with-hyphen@example.com", "o*********************n@example.com"),
        ("fully-qualified-domain@example.com", "f********************n@example.com"),
        ("user.name+tag+sorting@example.com", "u*******************g@example.com"),
        ("x@example.com", "x@example.com"),
        ("example-indeed@strange-example.com", "e************d@strange-example.com"),
        ("test/test@test.com", "t*******t@test.com"),
        ("example@s.example", "e*****e@s.example"),
        (""""john..doe"@example.org""", """"*********"@example.org"""),
        ("mailhost!username@example.org", "m***************e@example.org"),
        ("user%example.com@example.org", "u**************m@example.org"),
        ("user-@example.org", "u***-@example.org")
      )

    val invalidEmails: List[NonEmptyString] =
      List(
        "Abc.example.com",
        "A@b@c@example.com",
        """a"b(c)d,e:f;g<h>i[j\k]l@example.com""",
        """just"not"right@example.com""",
        """this is"not\allowed@example.com""",
        """this\ still\"not\\allowed@example.com""",
        "i_like_underscore@but_its_not_allowed_in_this_part.example.com",
        "QA[icon]CHOCOLATE[icon]@test.com"
      )

    forAll(validEmails)((s, result) => obfuscate(s) shouldBe Right(result))

    invalidEmails.map(obfuscate(_) shouldBe Left("cannot obfuscate value"))
  }

  test("phone number obfuscation") {
    val validPhoneNumbers: TableFor2[NonEmptyString, String] =
      Table(
        ("phone number", "result"),
        ("+44 123 456 789", "+**-***-**6-789"),
        ("+49 172 7470791", "+**-***-***0791"),
        ("+0710101010", "+******1010"),
        ("0710101010", "******1010"),
        ("+07 10 10 10 10", "+**-**-**-10-10")
      )

    val invalidPhoneNumbers: List[NonEmptyString] =
      List(
        "+49 621 60-6641516",
        "+49 621 60-41516",
        "8-1-6641516",
        "61 41516",
        "6641516",
        "41516"
      )

    forAll(validPhoneNumbers)((s, result) => obfuscate(s) shouldBe Right(result))

    invalidPhoneNumbers.map(obfuscate(_) shouldBe Left("cannot obfuscate value"))
  }

}
