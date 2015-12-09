import com.typesafe.sbt.GitPlugin
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.packager.docker.DockerPlugin
import com.typesafe.sbt.packager.{ Keys => PackagerKeys }
import de.heikoseeberger.sbtheader.{ HeaderKey, HeaderPlugin }
import de.heikoseeberger.sbtheader.license.Apache2_0
import sbt._
import sbt.Keys._
import scalariform.formatter.preferences.{ AlignSingleLineCaseStatements, DoubleIndentClassDeclaration }

object Build extends AutoPlugin {

  override def requires = plugins.JvmPlugin && HeaderPlugin && GitPlugin

  override def trigger = allRequirements

  override def projectSettings =
    List(
      // Core settings
      organization := "de.heikoseeberger",
      licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
      scalaVersion := Version.Scala,
      crossScalaVersions := List(scalaVersion.value),
      scalacOptions ++= List(
        "-unchecked",
        "-deprecation",
        "-language:_",
        "-target:jvm-1.8",
        "-encoding", "UTF-8"
      ),
      unmanagedSourceDirectories.in(Compile) := List(scalaSource.in(Compile).value),
      unmanagedSourceDirectories.in(Test) := List(scalaSource.in(Test).value),

      // Scalariform settings
      SbtScalariform.autoImport.preferences := SbtScalariform.autoImport.preferences.value
        .setPreference(AlignSingleLineCaseStatements, true)
        .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
        .setPreference(DoubleIndentClassDeclaration, true),

      // Git settings
      GitPlugin.autoImport.git.useGitDescribe := true,

      // Header settings
      HeaderKey.headers := Map("scala" -> Apache2_0("2015", "Heiko Seeberger")),

      // Docker settings
      PackagerKeys.maintainer.in(DockerPlugin.autoImport.Docker) := "Heiko Seeberger",
      PackagerKeys.daemonUser.in(DockerPlugin.autoImport.Docker) := "root",
      DockerPlugin.autoImport.dockerBaseImage                    := "java:8",
      DockerPlugin.autoImport.dockerRepository                   := Some("hseeberger")
    )
}
