name := """play-serialio"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

resolvers ++= Seq(
 "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
"Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
"Typesafe Snapshots repository" at "http://repo.typesafe.com/typesafe/snapshots/",
"Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
"Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "com.github.akileev" %% "akka-serial-io" % "1.0.2",
  specs2 % Test,
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.+",
  "com.typesafe.akka" %% "akka-actor" % "2.4.+",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.+"
)

