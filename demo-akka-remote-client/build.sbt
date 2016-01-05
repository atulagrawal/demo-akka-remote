name := "demo-akka-remote-client"

libraryDependencies ++= List(
  Library.akkaKryo,
  Library.akkaRemote
)

initialCommands := """|import de.heikoseeberger.demoakkaremote.server._""".stripMargin
