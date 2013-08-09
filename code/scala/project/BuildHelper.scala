import sbt._
import Keys._

trait BuildHelper extends Default with Dependencies {
  protected def project(id: String) = Project(
    id.toLowerCase
  , file(id.toLowerCase)
  , settings = scalaSettings ++ Seq(
      version := "0.0.0-SNAPSHOT"
    , name := "Beepo-" + id
    , organization := "hr.element.beepo"
    , initialCommands := "import hr.element.beepo._"
    )
  )
}
