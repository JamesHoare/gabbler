organization := "name.heikoseeberger"

name := "gabbler"

version := "1.0.0"

scalaVersion := "2.10.2"

// TODO Remove as soon as spray-json is on Maven Cental
resolvers += "spray repo" at "http://repo.spray.io"

// TODO Remove as soon as spray is final and on Maven Cental
resolvers += "spray nightlies repo" at "http://nightlies.spray.io"

libraryDependencies ++= Dependencies.gabbler

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-language:_",
  "-target:jvm-1.6",
  "-encoding", "UTF-8"
)
