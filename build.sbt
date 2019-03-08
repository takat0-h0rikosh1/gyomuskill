lazy val akkaHttpVersion = "10.1.7"
lazy val akkaVersion = "2.5.20"

lazy val root = (project in file(".")).aggregate(application, portDatabase, domain)
  .settings(
    inThisBuild(
      List(
        organization := "com.example",
        scalaVersion := "2.12.7"
      )),
    name := "akka-http-sample",
  )

lazy val domain = project in file("domain")

lazy val application = (project in file("application")).dependsOn(domain)

lazy val portDatabase = Project(
  id = "port-database",
  base = file("port/secondary/database")
).dependsOn(application).settings(
 libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.3.0",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.3.0",
      "com.typesafe" % "config" % "1.3.2",
      "com.h2database" % "h2" % "1.0.60"
    )
)

lazy val portWebService = Project(
  id = "port-web-service",
  base = file("port/primary/restApi")
).settings(
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  )
)
