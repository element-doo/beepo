import sbt._
import Keys._

import Helpers._
import Dependencies._

object Core extends Build {
  lazy val root = project(
    "Core",
    ProjectFlavor.Scala,
    Seq(),
    Seq()
  ) settings (
    Web.settings(9630) ++
    Seq(
      unmanagedSourceDirectories in Compile <<= (scalaSource in Compile) { mainSource =>
        val generatedSource = mainSource.getParentFile.getParentFile / "generated" / "scala"
        mainSource :: generatedSource :: Nil
      }
    ): _*
  )
}
