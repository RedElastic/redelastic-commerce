name := """reactive-commerce"""

version := "0.0.1-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean, SwaggerPlugin)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs,
  cache,
  javaWs,
  evolutions,
  filters,
  "com.typesafe.akka" %% "akka-cluster" % "2.5.0",
  "com.typesafe.akka" %% "akka-persistence" % "2.5.0",
  "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.51",
  "io.javaslang" % "javaslang" % "2.0.3",
  "org.webjars" % "swagger-ui" % "2.2.0"
)

routesGenerator := InjectedRoutesGenerator

swaggerDomainNameSpaces := Seq("models")
