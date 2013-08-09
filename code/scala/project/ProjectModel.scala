import sbt._
import Keys._

object Model extends Build with BuildHelper {
  lazy val interfacesGenerated = (
    project("Model-Interfaces-Generated")
    inject(
      ngsInterfaces
    , jacksonAnnotations
    )
  )

  lazy val servicesGenerated = (
    project("Model-Services-Generated")
    inject(
      ngsCore
    , Model.interfacesGenerated
    )
  )

  lazy val interfacesPlus = (
    project("Model-Interfaces-Plus")
    inject(
      javaMail
    , Model.interfacesGenerated
    )
  )

  lazy val servicesPlus = (
    project("Model-Services-Plus")
    inject(
      commonsCodec
    , Model.interfacesPlus
    , Model.servicesGenerated
    )
  )
}
