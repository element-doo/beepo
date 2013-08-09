package hr.element.beepo.core

import net.liftweb._
import http._
import common._
import rest._
import util._
import Helpers._
import hr.element.etb.Pimps._
import scala.xml._
import Repositories._


object Api extends RestHelper with Logger {

  serve {
    case req @ Req("api" :: Nil, _, PostRequest) =>

      for {
        user <- req.param("user")
        password <-req.param("password")
        action <- req.param("action")
        payload <- req.param("payload")
        payloadXml <- tryo{ payload.toElem }
      } yield {

        //TODO: uncomment
        //if (userRepository.checkLogin(user, password)) {
            processRequest(user, action, payloadXml)
//        }
//        else {
//          UnauthorizedResponse("Invalid username / password!")
//        }
      }
  }

  def processRequest(
      user: String
    , action: String
    , payload: Elem
    ): LiftResponse = {
    import Repositories._
    infoOnProcess(user, action)

    (action, payload) match {
      case ("Persist", UnpackXml(task, emails, smss)) =>
        PlainTextResponse(taskProc.enqueue(task, emails, smss))

      case ("Send", UnpackXml(task, emails, smss)) =>
        PlainTextResponse(taskProc.send(task, emails, smss))

      case ("Send", XmlToIdList(listOfIds)) =>
        PlainTextResponse(taskProc.send(listOfIds))

      case _ =>
        BadResponse()
    }
  }

  def infoOnProcess(user: String, action: String) =
    logger.info("Processing %s for user %s". format(action, user))
}
