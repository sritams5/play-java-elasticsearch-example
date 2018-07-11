name := """play-java-elasticsearch-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.11.12", "2.12.4")
val elasticVersion = "5.6.5"

libraryDependencies += guice
libraryDependencies += "org.elasticsearch" % "elasticsearch" % elasticVersion
libraryDependencies += "org.elasticsearch.client" % "transport" % elasticVersion
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.9.5"
// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.197"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
