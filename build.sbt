name := """reactive-commerce"""

version := "1.0-SNAPSHOT"

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
  "io.javaslang" % "javaslang" % "2.0.3",
  "org.webjars" % "swagger-ui" % "2.2.0"
)

routesGenerator := InjectedRoutesGenerator

swaggerDomainNameSpaces := Seq("models")
