name := "demo-akka-remote-client"

libraryDependencies ++= List(
  Library.akkaRemote,
  Library.akkaTestkit % "test",
  Library.scalaCheck  % "test",
  Library.scalaTest   % "test"
)

initialCommands := """|import de.heikoseeberger.demoakkaremote.server._""".stripMargin
