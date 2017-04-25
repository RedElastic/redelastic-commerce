name := """reactive-commerce"""

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean, SwaggerPlugin)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaWs,
  cache,
  filters,
  "com.typesafe.akka" %% "akka-cluster" % "2.5.0", //TODO play is using akka 2.4 core still.
  "com.typesafe.akka" %% "akka-persistence" % "2.5.0",
  "com.typesafe.akka" %% "akka-cluster-sharding" % "2.5.0",
  "com.typesafe.akka" %% "akka-distributed-data" % "2.5.0",
  "com.typesafe.akka" %% "akka-slf4j" % "2.5.0",
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
