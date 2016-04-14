import sbt._

object Version {
  final val Akka       = "2.4.4"
  final val AkkaKryo   = "0.4.0"
  final val Scala      = "2.11.8"
  final val ScalaCheck = "1.12.5"
  final val ScalaTest  = "2.2.6"
}

object Library {
  val akkaActor   = "com.typesafe.akka"     %% "akka-actor"              % Version.Akka
  val akkaKryo    = "com.github.romix.akka" %% "akka-kryo-serialization" % Version.AkkaKryo
  val akkaRemote  = "com.typesafe.akka"     %% "akka-remote"             % Version.Akka
  val akkaTestkit = "com.typesafe.akka"     %% "akka-testkit"            % Version.Akka
  val scalaCheck  = "org.scalacheck"        %% "scalacheck"              % Version.ScalaCheck
  val scalaTest   = "org.scalatest"         %% "scalatest"               % Version.ScalaTest
}
