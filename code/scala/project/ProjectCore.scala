import sbt._
import Keys._

import Helpers._
import Dependencies._

object Core extends Build {
  lazy val root = project(
    "Core",
    ProjectFlavor.Scala,
    Seq(ngsUtil),
    Seq(Model.services)
  ) settings (
    Web.settings(9630): _*
  )
}
