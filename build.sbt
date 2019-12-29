name := "scala-jsonator"

version := "0.1"

scalaVersion := "2.12.10"

val compilerOptions = Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-unchecked",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xfuture",
  "-Ywarn-unused-import",
  "-Ypartial-unification",
  "-Xfatal-warnings"
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

val defaultSettings = Seq(
  version := "0.2.1",
  scalaVersion := "2.12.10",
  organization := "pl.muninn",
  scalacOptions := compilerOptions
)

val circeVersion = "0.12.3"
val playJsonVersion = "2.8.1"
val json4SVersion = "3.6.7"


lazy val core =
  (project in file("core"))
    .settings(defaultSettings: _*)

lazy val circe =
  (project in file("circe"))
    .settings(defaultSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        "io.circe" %% "circe-core" % circeVersion,
        "io.circe" %% "circe-parser" % circeVersion
      )
    )
    .dependsOn(core)

lazy val play =
  (project in file("play"))
    .settings(defaultSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        "com.typesafe.play" %% "play-json" % playJsonVersion
      )
    )
    .dependsOn(core)

lazy val json4sNative =
  (project in file("json4sNative"))
    .settings(defaultSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        "org.json4s" %% "json4s-native" % json4SVersion
      )
    )
    .dependsOn(core)

lazy val json4sJackson =
  (project in file("json4sJackson"))
    .settings(defaultSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        "org.json4s" %% "json4s-jackson" % json4SVersion
      )
    )
    .dependsOn(core)

lazy val root =
  (project in file("."))
    .settings(defaultSettings: _*)
    .settings(
      name := "scala-jsonator",
      sbtVersion := "1.3.6",
      libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "3.0.5" % "test",
        "io.circe" %% "circe-generic" % circeVersion
      )
    ).dependsOn(core, circe, play, json4sNative, json4sJackson)