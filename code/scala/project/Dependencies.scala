import sbt._

trait Dependencies {
  // DSL: NGS Scala
  val ngsInterfaces      = "hr.ngs" %% "ngs-core"       % "0.3.15"
  val jacksonAnnotations = "com.fasterxml.jackson.core" % "jackson-annotations" % "2.2.2"
  val ngsCore            = "hr.ngs" %% "ngs-core"       % "0.3.16-1"
  val ngsUtil            = "hr.ngs" %% "ngs-util"       % "0.3.16-1"
 
  // Web (Lift + Jetty)
  val liftWebkit = "net.liftweb" %% "lift-webkit"  % "2.5.1"
  val jetty      = "org.eclipse.jetty" % "jetty-webapp" % "8.1.12.v20130726" % "container"
  val jettyOrbit = "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container" artifacts Artifact("javax.servlet", "jar", "jar")
}
