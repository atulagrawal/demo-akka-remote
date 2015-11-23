lazy val demoAkkaRemote = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning)

name := "demo-akka-remote"

libraryDependencies ++= List(
  Library.scalaCheck % "test",
  Library.scalaTest  % "test"
)

initialCommands := """|import de.heikoseeberger.demoakkaremote._""".stripMargin
