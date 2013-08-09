package hr.element.beepo
package sms

import dispatch.{:/, Http}
import scala.io.Source
import scala.xml._
import hr.element.etb.slug._
import org.apache.commons.io.IOUtils
import processor._
import dispatch.Defaults._
import scala.concurrent.Await
import scala.concurrent.duration._
import hr.element.etb.Pimps._

object IPTSender extends Logger {

  import Repositories._
  private def sid   = iptCred.serviceId
  private def pass  = iptCred.password
  private def sender= iptCred.sender
  private def host  = uris.iptHost
  private def port  = uris.iptPort

  val url = :/(host, port) / "smssoap" / "sender.asmx"
  val req = url <:< Map("content-type" -> "application/soap+xml; charset=utf-8") << (_: String)

  def sendSMS2(receiver: String, message: String): Either[Exception, Elem] ={
    logger.info("Sending SMS %s to %s with %s account." format (message, receiver, sender ))
    postRequest(envelope.SendSMS2(receiver, SMSifier(message), sid, pass))
  }

  def userSendSMS(receiver: String, message: String) =
    postRequest(envelope.UserSendSMS(receiver, SMSifier(message), sid, pass))


  def getReturnCode(messageLogId: Long) = {
    logger.info("Checking message %s status with %s account." format (messageLogId, sender ))
    postRequest(envelope.CheckMessage(messageLogId, sid, pass))
  }

  private def postRequest(
      msg   : String
    ): Either[Exception, Elem] = {
    val hX = new Http
    println(msg)
    try {
      val r = req(msg)
      val resp = Await.result(hX(r), 30 seconds)
      val body = resp.getResponseBody("utf8")




/*      resp.
      r >> { iS => new String(IOUtils.toByteArray(iS), "UTF-8") } )
      hX.shutdown()
      println(resp)
      *
      */
      Right(body.toElem)


    } catch {
      case e: Exception =>
        hX.shutdown()
        Left(e)
    }
  }
}

