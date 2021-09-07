![CI](https://github.com/sallareznov/verbose-potato/actions/workflows/scala.yml/badge.svg)

# verbose-potato

# What?

Playground project with small Scala exercises

| Module | Source | Tests |
| ------ | ------ | ----- |
| ordinal indicator suffix | [OrdinalIndicatorSuffix.scala](https://github.com/sallareznov/verbose-potato/blob/main/src/main/scala/com/verbose/potato/OrdinalIndicatorSuffix.scala) | [OrdinalIndicatorSuffixSpec.scala](https://github.com/sallareznov/verbose-potato/blob/main/src/test/scala/com/verbose/potato/OrdinalIndicatorSuffixSpec.scala) |
| number of sundays | [NumberOfSundays.scala](https://github.com/sallareznov/verbose-potato/blob/main/src/main/scala/com/verbose/potato/NumberOfSundays.scala) | [NumberOfSundaysSpec.scala](https://github.com/sallareznov/verbose-potato/blob/main/src/test/scala/com/verbose/potato/NumberOfSundaysSpec.scala) |
| obfuscation | [Obfuscation.scala](https://github.com/sallareznov/verbose-potato/blob/main/src/main/scala/com/verbose/potato/Obfuscation.scala) | [ObfuscationSpec.scala](https://github.com/sallareznov/verbose-potato/blob/main/src/test/scala/com/verbose/potato/ObfuscationSpec.scala) |

# How?

### Tests

Run `sbt test`

## Links

- [Email regex](https://html.spec.whatwg.org/multipage/input.html#valid-e-mail-address)

## Side notes

`type Email = MatchesRegex[...]` 
`type PhoneNumber = MatchesRegex[...]`

`type EmailOrPhoneNumber = String Refined Or[Email, PhoneNumber]`

`def obfuscate(s: EmailOrPhoneNumber): Either[String, String]`

The advantage of this approach is that the invalid inputs (a string which is not an email or a phone number)
would be handled at the earliest stage, the compilation. The code would be type-safer with this approach, as we won't need
to worry about invalid inputs.

I had two main issues dealing with this approach:
- the `Email` regex caused a compilation error because of a backtick in the regex.
There is a [workaround](https://stackoverflow.com/questions/55539996/scala-how-to-escape-a-backtick-in-a-literal) though, but it doesn't seem trivial.

I also could've set the project's version to Scala 3, but it would mean I would give up on the `refined` library.
Because the library has some limitations in Scala 3, especially when it comes to declaring literal values, which are converted and checked
implicitly thanks to Scala 2 macros (See https://github.com/fthomas/refined/issues/932).
I decided to stick to Scala 2 and `refined`, because of the type-safety it gives me, to filter out invalid inputs and
not having to worry about them.
