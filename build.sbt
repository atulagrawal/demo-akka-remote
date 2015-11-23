lazy val demoAkkaRemote = project
  .in(file("."))
  .enablePlugins(GitVersioning)
  .aggregate(demoAkkaRemoteServerProtocol, demoAkkaRemoteServer, demoAkkaRemoteClient)

lazy val demoAkkaRemoteServerProtocol = project
  .in(file("demo-akka-remote-server-protocol"))
  .enablePlugins(AutomateHeaderPlugin)

lazy val demoAkkaRemoteServer = project
  .in(file("demo-akka-remote-server"))
  .enablePlugins(AutomateHeaderPlugin, JavaAppPackaging, DockerPlugin)
  .dependsOn(demoAkkaRemoteServerProtocol)

lazy val demoAkkaRemoteClient = project
  .in(file("demo-akka-remote-client"))
  .enablePlugins(AutomateHeaderPlugin, JavaAppPackaging, DockerPlugin)
  .dependsOn(demoAkkaRemoteServerProtocol)

name := "demo-akka-remote"
