name := """wibbly-wobbly"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.10.4"

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  //"org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.akka23-SNAPSHOT"
  "com.typesafe.slick" %% "slick" % "2.1.0-M2",
  "com.typesafe.play" %% "play-slick" % "0.7.0-M1",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  // slick needs that for debuging
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "joda-time" % "joda-time" % "2.3",
  "org.joda" % "joda-convert" % "1.5",
  "com.github.tototoshi" %% "slick-joda-mapper" % "1.2.0-SNAPSHOT",
  "com.github.tminglei" % "slick-pg_2.10" % "0.5.3"
)
