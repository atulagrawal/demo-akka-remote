name := "demo-akka-remote-server-protocol"

libraryDependencies ++= List(
  Library.akkaActor
)

initialCommands := """|import de.heikoseeberger.demoakkaremote.server.protocol""".stripMargin
