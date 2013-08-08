import sbt._

object Dependencies {
  // Web
  private val liftVersion = "2.5.1"
  val liftCommon = "net.liftweb"       %% "lift-common"  % liftVersion
  val liftUtil   = "net.liftweb"       %% "lift-util"    % liftVersion
  val liftWebkit = "net.liftweb"       %% "lift-webkit"  % liftVersion
  val jetty      = "org.eclipse.jetty" %  "jetty-webapp" % "8.1.12.v20130726" % "container"
}
