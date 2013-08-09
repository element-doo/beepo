import sbt._
import Keys._

// Web plugin
import com.earldouglas.xsbtwebplugin.WebPlugin._
import com.earldouglas.xsbtwebplugin.PluginKeys._

object Core extends Build with BuildHelper {
  lazy val root = (
    project("Core")
    inject(
      ngsUtil
    , jetty
    , jettyOrbit
    , liftWebkit
    , Model.servicesPlus
    )
    settings(Seq(
        scanDirectories in Compile := Nil
      , port in container.Configuration := 9000
      ) ++ webSettings: _*
    )
  )
}
