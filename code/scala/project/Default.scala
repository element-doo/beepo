import sbt._
import Keys._

trait Default {
  private object Repositories {
    val ElementNexus     = "Element Nexus"     at "http://repo.element.hr/nexus/content/groups/public/"
    val ElementReleases  = "Element Releases"  at "http://repo.element.hr/nexus/content/repositories/releases/"
    val ElementSnapshots = "Element Snapshots" at "http://repo.element.hr/nexus/content/repositories/snapshots/"
  }

  private object Resolvers {
    import Repositories._

    lazy val settings = Seq(
      resolvers := Seq(
        ElementNexus
      , ElementReleases
      , ElementSnapshots
      ),
      externalResolvers <<= resolvers map { r =>
        Resolver.withDefaultResolvers(r, mavenCentral = false)
      }
    )
  }

  private object Publishing {
    import Repositories._

    lazy val settings = Seq(
      credentials += Credentials(Path.userHome / ".config" / "beepo" / "nexus.config")
    , publishArtifact in (Compile, packageDoc) := false
    , publishTo <<= (version) { version => Some(
        if (version endsWith "SNAPSHOT") ElementSnapshots else ElementReleases)
      }
    )
  }

  private object ScalaOptions {
    val scala2_8 = Seq(
      "-unchecked"
    , "-deprecation"
    , "-optimise"
    , "-encoding", "UTF-8"
    , "-Xcheckinit"
    , "-Yclosure-elim"
    , "-Ydead-code"
    , "-Yinline"
    )

    val scala2_9 = Seq(
      // "-Xmax-classfile-name", "72"
    )

    val scala2_9_1 = Seq(
      "-Yrepl-sync"
    , "-Xlint"
    , "-Xverify"
    , "-Ywarn-all"
    )

    val scala2_10 = Seq(
      "-feature"
    , "-language:postfixOps"
    , "-language:implicitConversions"
    , "-language:existentials"
    //, "-Yinline-warnings"
    )

    def apply(version: String) = scala2_8 ++ (version match {
      case x if (x startsWith "2.10.")                => scala2_9 ++ scala2_9_1 ++ scala2_10
      case x if (x startsWith "2.9.") && x >= "2.9.1" => scala2_9 ++ scala2_9_1
      case x if (x startsWith "2.9.")                 => scala2_9
      case _ => Nil
    } )
  }

//  ---------------------------------------------------------------------------

  import com.typesafe.sbteclipse.plugin.EclipsePlugin.{ settings => eclipseSettings, _ }
  import net.virtualvoid.sbt.graph.Plugin._

  lazy val scalaSettings =
    Defaults.defaultSettings ++
    Resolvers.settings ++
    Publishing.settings ++
    eclipseSettings ++
    graphSettings ++ Seq(
      javaHome := sys.env.get("JDK16_HOME").map(file(_))
    , javacOptions := Seq(
        "-deprecation"
      , "-encoding", "UTF-8"
      , "-Xlint:unchecked"
      , "-source", "1.6"
      , "-target", "1.6"
      )

    , crossScalaVersions := Seq("2.10.2")
    , scalaVersion <<= crossScalaVersions(_.head)
    , scalacOptions <<= scalaVersion map ScalaOptions.apply

    , unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(_ :: Nil)
    , unmanagedSourceDirectories in Test <<= (scalaSource in Test)(_ :: Nil)
    , libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "2.0.M5b" % "test"
      , "junit" % "junit" % "4.11" % "test"
      )

    , publishArtifact in (Compile, packageDoc) := false
    , EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
    )

  lazy val javaSettings = scalaSettings ++ Seq(
      autoScalaLibrary := false
    , crossPaths := false

    , unmanagedSourceDirectories in Compile <<= (javaSource in Compile)(_ :: Nil)
    , unmanagedSourceDirectories in Test <<= (javaSource in Test, scalaSource in Test)(_ :: _ :: Nil)
    , EclipseKeys.projectFlavor := EclipseProjectFlavor.Java
    )

//  ---------------------------------------------------------------------------

  implicit def pimpMyProjectHost(project: Project) =
    new PimpedProjectHost(project)

  case class PimpedProjectHost(project: Project) {
    def inject(children: ProjectReferencePlus*): Project = {
      children.toList match {
        case Nil =>
          project

        case head :: tail =>
          PimpedProjectHost(head attachTo project).inject(tail: _*)
      }
    }
  }

  sealed trait ProjectReferencePlus {
    def attachTo(attachment: Project): Project
  }

  implicit def pimpMyProject(attachment: Project): ProjectReferencePlus =
    new PimpedProject(attachment)

  case class PimpedProject(attachment: Project) extends ProjectReferencePlus {
    def attachTo(original: Project) = original dependsOn attachment
  }

  implicit def pimpMyProjectRef(attachment: ProjectRef): ProjectReferencePlus =
    new PimpedProjectRef(attachment)

  case class PimpedProjectRef(attachment: ProjectRef) extends ProjectReferencePlus {
    def attachTo(original: Project) = original dependsOn attachment
  }

  implicit def pimpMyModuleID(attachment: ModuleID): ProjectReferencePlus =
    new PimpedModuleID(attachment)

  case class PimpedModuleID(attachment: ModuleID) extends ProjectReferencePlus {
    def attachTo(original: Project) = original settings(
      libraryDependencies += attachment
    )
  }
}
