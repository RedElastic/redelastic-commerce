name := """redelastic-commerce"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs,
  cache,
  javaWs,
  evolutions,
  "io.javaslang" % "javaslang" % "2.0.3"
)

routesGenerator := InjectedRoutesGenerator
