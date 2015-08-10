name := "regexmacro"

organization := "org.sazabi"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

crossScalaVersions := Seq(scalaVersion.value, "2.10.5")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scalatest" %% "scalatest" % "2.2.5" % "test")

incOptions := incOptions.value.withNameHashing(true)

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
  "-language:experimental.macros")

publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/solar/regexmacro</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt"</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:solar/regexmacro.git</url>
    <connection>scm:git:git@github.com:solar/regexmacro.git</connection>
  </scm>
  <developers>
    <developer>
      <id>solar</id>
      <name>Shinpei Okamura</name>
      <url>https://github.com/solar</url>
    </developer>
  </developers>)
