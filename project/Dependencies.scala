import sbt._

object Dependencies {

  object V {
    // Scala
    val scala = "2.12.16"

    // read config
    val tsConfig = "1.4.2"

    // parse args
    val scopt = "4.0.1"

    // spark
    val spark = "2.4.8"

    // fake data generation
    val faker = "0.4"

    //parse JSON
    val circe = "0.14.1"

    // Unit tests
    val scalatest = "3.2.12"
  }

  val commonDependencies = List(
    "com.typesafe"      % "config" % V.tsConfig,
    "com.github.scopt" %% "scopt"  % V.scopt
  )

  val fakeDataGeneration = List(
    "it.bitbl" %% "scala-faker" % V.faker
  )

  val jsonParsingDependencies: List[ModuleID] = List(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % V.circe)

  val sparkDependencies = List(
    "org.apache.spark" %% "spark-core" % V.spark,
    "org.apache.spark" %% "spark-sql"  % V.spark,
  )

  val testDependencies = List(
    "org.scalactic" %% "scalactic" % V.scalatest,
    "org.scalatest" %% "scalatest" % V.scalatest % "test"
  )

  val all: List[ModuleID] = sparkDependencies ++
    testDependencies ++
    commonDependencies ++
    fakeDataGeneration ++
    jsonParsingDependencies

}
