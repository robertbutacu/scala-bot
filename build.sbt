name := "scala-bot"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= {
  val akkaVersion = "2.4.12"
  Seq(
    "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.5",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion)
}