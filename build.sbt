ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := Dependencies.V.scala

lazy val root = (project in file("."))
  .settings(
    name := "bdf-tool",
  )
  .settings(BuildSettings.assemblySettings)
  .settings(libraryDependencies ++= Dependencies.all)
