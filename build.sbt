name := "regexmacro"

organization := "org.sazabi"

scalaVersion := "2.11.8"

crossScalaVersions := Seq(scalaVersion.value, "2.10.6")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided",
  "org.typelevel" %% "macro-compat" % "1.1.1",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
)

libraryDependencies ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, major)) if major >= 11 => Seq(
      "org.scala-lang.modules" %% "scala-java8-compat" % "0.7.0")

    case Some((2, 10)) => Seq(
      "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided",
      "org.scalamacros" %% "quasiquotes" % "2.1.0")
  }
}

incOptions := incOptions.value.withNameHashing(true)
updateOptions := updateOptions.value.withCachedResolution(true)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, major)) if major >= 11 =>
      Seq("-Ybackend:GenBCode", "-Ydelambdafy:method", "-target:jvm-1.8")
    case _ => Seq.empty
  }
}

releasePublishArtifactsAction := PgpKeys.publishSigned.value
releaseCrossBuild := true

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
