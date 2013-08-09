import sbt._
import Keys._

// Web plugin
import com.earldouglas.xsbtwebplugin.WebPlugin._
import com.earldouglas.xsbtwebplugin.PluginKeys._

object Client extends Build with BuildHelper {
  lazy val root = (
    project("Client")
    inject(
      dispatch
    , configrity
    , commonsCodec
    , etbUtil
    , mimeTypes
    , scalaIo
    )
  )
}
