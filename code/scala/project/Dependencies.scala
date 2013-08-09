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

  // Commons
  val configrity    = "org.streum"                    %% "configrity-core" % "1.0.0"
  val commonsCodec  = "commons-codec"                 %  "commons-codec"   % "1.8"
  val commonsIO     = "commons-io"                    %  "commons-io"      % "2.4"
  val etbSlug       = "hr.element.etb"                %% "etb-slug"        % "0.0.3"
  val etbUtil       = "hr.element.etb"                %% "etb-util"        % "0.2.19"
  val mimeTypes     = "hr.element.onebyseven.common"  %  "mimetypes"       % "2012-02-12"
  val scalaIo       = "com.github.scala-incubator.io" %% "scala-io-file"   % "0.4.2"

  // Other
  val dispatch = "net.databinder.dispatch" %% "dispatch-core"  % "0.11.0"
  val javaMail = "javax.mail"              %  "mail"           % "1.4"
}
