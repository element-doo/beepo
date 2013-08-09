import sbt._
import Keys._


// ==============  PUBLISHING SETTINGS  ==============
object Publishing {
  import Repositories._

  lazy val settings = Seq(
    credentials        += Credentials(Path.userHome / ".config" / "beepo" / "nexus.config"),
    publishArtifact in (Compile, packageDoc) := false,
    publishTo <<= (version) { version => Some(
      if (version endsWith "SNAPSHOT") ElementSnapshots else ElementReleases)
    }
  )
}



// ==============  WEB SETTINGS  ==============
object Web {
  import Dependencies._
  import com.earldouglas.xsbtwebplugin.WebPlugin
  import com.earldouglas.xsbtwebplugin.PluginKeys

  def settings(jettyPort: Int) =
    WebPlugin.webSettings ++ Seq(
      PluginKeys.scanDirectories in Compile := Nil,
      PluginKeys.port in WebPlugin.container.Configuration := jettyPort,
      libraryDependencies := Seq(liftCommon, liftUtil, liftWebkit, jetty)
    )
}



// ==============  DEFINES DEFAULT SETTINGS USED BY ALL PROJECTS  ==============
object Default {
  import com.typesafe.sbteclipse.plugin.EclipsePlugin
  import EclipsePlugin.{ EclipseKeys, EclipseProjectFlavor }

  val Name = "Beepo"

  lazy val javaProject =
    EclipsePlugin.settings ++ Seq(
      EclipseKeys.projectFlavor := EclipseProjectFlavor.Java,
      javacOptions := Seq(
        "-deprecation"
      , "-encoding", "UTF-8"
      , "-Xlint:unchecked"
      , "-source", "1.6"
      , "-target", "1.6"
      ),
      autoScalaLibrary := false,
      crossPaths       := false,
      unmanagedSourceDirectories in Compile <<= (javaSource in Compile)(_ :: Nil)
  )

  lazy val scalaProject =
    EclipsePlugin.settings ++ Seq(
      EclipseKeys.projectFlavor := EclipseProjectFlavor.Scala,
      scalacOptions := Seq(
        "-unchecked"
      , "-deprecation"
      , "-optimise"
      , "-encoding", "UTF-8"
      , "-Xcheckinit"
      , "-Yclosure-elim"
      , "-Ydead-code"
      , "-Xmax-classfile-name", "72"
      , "-Yrepl-sync"
      , "-Xlint"
      , "-Xverify"
      , "-Ywarn-all"
      , "-feature"
      , "-language:postfixOps"
      , "-language:implicitConversions"
      , "-language:existentials"
      ),
      unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(_ :: Nil)
  )

  lazy val settings =
    Defaults.defaultSettings ++
    Resolvers.settings ++ Seq(
      name         := Name,
      organization := "com.instantor.eds",
      version      := "0.0.0-SNAPSHOT",
      scalaVersion := "2.10.2",
      unmanagedSourceDirectories in Test := Nil
  )
}



// ==================  HELPER FUNCTIONS FOR PROJECT CREATION  ==================
object Helpers {
  // Implicit magic that makes project definitions really simple to write.
  implicit def depToFunSeq(m: ModuleID) = Seq((_: String) => m)
  implicit def depFunToSeq(fm: String => ModuleID) = Seq(fm)
  implicit def depSeqToFun(mA: Seq[ModuleID]) = mA.map(m => ((_: String) => m))
  implicit def warName2SMA(name: String) = (_: String, _: ModuleID, _: Artifact) => name

  sealed trait ProjectFlavor
  object ProjectFlavor {
    case object Java  extends ProjectFlavor
    case object Scala extends ProjectFlavor
  }

  private def flavorSettings(flavor: ProjectFlavor) =
    flavor match {
      case ProjectFlavor.Java  => Default.javaProject
      case ProjectFlavor.Scala => Default.scalaProject
    }


  // Creates a main container project (one that contains all other projects, and
  // is otherwise empty).
  def top(projectAggs: Seq[sbt.ProjectReference] = Seq()) =
    Project(
      Default.Name,
      file("."),
      settings = Default.settings
    ) aggregate(projectAggs: _*)


  // Creates a container project (one that contains sub-projects, and is
  // otherwise empty).
  def parent(
      title:       String,
      projectAggs: Seq[sbt.ProjectReference] = Seq()) =
    Project(
      title,
      file(title.replace('-', '/')),
      settings = Default.settings ++ Seq(
        name := Default.Name +"-"+ title
      )
    ) aggregate(projectAggs: _*)


  // Creates a proper project.
  def project(
      title:       String,
      flavor:      ProjectFlavor,
      deps:        Seq[Seq[String => ModuleID]] = Seq(),
      projectDeps: Seq[ClasspathDep[ProjectReference]] = Seq()) =
    Project(
      title,
      file(title.replace('-', '/')),
      settings = Default.settings ++ flavorSettings(flavor) ++ Seq(
        name := Default.Name +"-"+ title
      ) :+ (libraryDependencies <++= scalaVersion( sV =>
        for (depSeq <- deps; dep <- depSeq) yield dep(sV))
      )
    ) dependsOn(projectDeps: _*)
}
