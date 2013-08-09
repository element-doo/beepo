import sbt._
import Keys._

object Model extends Build with BuildHelper {
  lazy val generated = (
    project("Model-Generated")
    inject(
      ngsCore
    )
  )

  lazy val services = (
    project("Model-Services")
    inject(
      Model.generated
    )
  )
}
