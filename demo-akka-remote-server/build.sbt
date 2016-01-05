name := "demo-akka-remote-server"

libraryDependencies ++= List(
  Library.akkaKryo,
  Library.akkaRemote,
  Library.akkaTestkit % "test",
  Library.scalaCheck  % "test",
  Library.scalaTest   % "test"
)

initialCommands := """|import de.heikoseeberger.demoakkaremote.server._""".stripMargin
