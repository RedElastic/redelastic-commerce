name := """reactive-commerce"""

version := "0.0.1-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean, SwaggerPlugin)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaWs,
  cache,
  filters,
  "com.typesafe.akka" %% "akka-cluster" % "2.4.17",
  "com.typesafe.akka" %% "akka-persistence" % "2.4.17",
  "com.typesafe.akka" %% "akka-cluster-sharding" % "2.4.17",
  "com.typesafe.akka" %% "akka-distributed-data-experimental" % "2.4.17",
  "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.26",
  "de.heikoseeberger" %% "constructr" % "0.17.0",
  "com.lightbend.constructr" %% "constructr-coordination-zookeeper" % "0.3.3",
  "io.javaslang" % "javaslang" % "2.0.3",
  "org.webjars" % "swagger-ui" % "2.2.0"
)

routesGenerator := InjectedRoutesGenerator

swaggerDomainNameSpaces := Seq("models")

//Docker config

maintainer := "Red Elastic"

dockerRepository := Some("jasongoodwin")

dockerExposedPorts in Docker := Seq(9000)