import sbt._
import Keys._

import Helpers._
import Dependencies._

object Model extends Build {
  lazy val services = project(
    "Model-Services",
    ProjectFlavor.Scala,
    Seq(),
    Seq(Model.generated)
  )

  lazy val generated = project(
    "Model-Generated",
    ProjectFlavor.Scala,
    Seq(ngsCore),
    Seq()
  )
}
