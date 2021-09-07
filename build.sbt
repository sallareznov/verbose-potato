name := "verbose-potato"

scalaVersion := "2.13.6"

libraryDependencies ++=
  Seq(
    "org.typelevel" %% "cats-core" % "2.6.1",
    "eu.timepit"    %% "refined"   % "0.9.27",
    "org.scalatest" %% "scalatest" % "3.2.9" % Test
  )
