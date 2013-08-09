package hr.element.beepo
package core

import net.liftweb._
import http._
import common._
import rest._
import util._
import Helpers._
import hr.element.etb.Pimps._
import scala.xml._
import Repositories._


object MockRest extends RestHelper with Logger {

  serve {
        case req @ Req("mock" :: Nil, _, PostRequest) =>
          println("api mock :%s" format req)
          PlainTextResponse("received: %s" format req)
  }

}