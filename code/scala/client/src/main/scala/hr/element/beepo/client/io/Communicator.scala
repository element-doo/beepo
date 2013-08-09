package hr.element.beepo.client
package io

import dispatch._
import scala.xml.Elem

import org.streum.configrity._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

private object Communicator {
  val path = sys.props("user.home") + "/.config/beepo/client.config"

  val cfg = Configuration.load(path).detach("beepo")
  val host = cfg[String]("host")
  val port = cfg[Int]("port")
  val user = cfg[String]("user")
  val password = cfg[String]("password")
}

trait Communicator { this: xml.XMLConverter =>
  import Communicator._

  protected def send(action: Action): String = {
    val url = :/(host, port) / "api"

    val params = Map(
      "user"     -> user
    , "password" -> password
    , "action"   -> action.toString
    , "payload"  -> toXml.toString
    )

    val hX = new Http()
    try {
      val fut = hX(url << params OK as.String)
      Await.result(fut, 30 seconds)
    }
    finally {
      hX.shutdown()
    }
  }
}
