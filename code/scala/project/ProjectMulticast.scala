import sbt._
import Keys._

// Web plugin
import com.earldouglas.xsbtwebplugin.WebPlugin._
import com.earldouglas.xsbtwebplugin.PluginKeys._

object Multicast extends Build with BuildHelper {
  lazy val root = (
    project("Multicast")
    inject(
      configrity
    , commonsIO
    , etbSlug
    , etbUtil
    , dispatch
    , ngsUtil
    , Model.servicesPlus
    )
  )
}
