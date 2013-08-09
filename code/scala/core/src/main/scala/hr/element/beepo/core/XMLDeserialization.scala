package hr.element.beepo
package core


import scala.xml._
import org.joda.time._
import Model._
import java.util.UUID
import net.liftweb.util.Helpers.tryo
import org.apache.commons.codec.binary.Base64

object UnpackXml {
  def unapply(node: Elem):
    Option[(Task, IndexedSeq[EmailSmtpRequest], IndexedSeq[SmsIptRequest])] =
    {
      node match {
        case XmlToTask(task) =>
          Some(task, XmlToEmailSmtpRequest(task), XmlToSmsIptRequest(task))
        case _ => None
      }
    }
}

object XmlToTask {
  def apply(node: Elem): Task =
  {
    println(node)


    Task(
        requestID = (node \ "requestID").headOption.map(_.text)
      , payload =node
    )
  }
  def unapply(node: Elem): Option[Task] =
    node.label match {
      case "Task" =>
        tryo{ apply(node) }


      case _ =>
        None
    }
}

object XmlToEmailSmtpRequest {
  def apply(task: Task): IndexedSeq[EmailSmtpRequest] =
    (for(e <- task.payload \ "emails" \ "EmailSmtpRequest") yield {
      EmailSmtpRequest(
          task        = task // task
        , from        = e \ "from" text
        , to          = e \ "to" \ "string" map(_.text) toIndexedSeq
        , replyTo     = e \ "replyTo" map(_.text) toIndexedSeq
        , cc          = e \ "cc" \ "string" map(_.text) toIndexedSeq
        , bcc         = e \ "bcc" \ "string" map(_.text) toIndexedSeq
        , subject     = e \ "subject" text
        , textBody    = (e \ "textBody" headOption) map(_.text)
        , htmlBody    = (e \ "htmlBody" headOption) map(_.text)
        , attachments = (for (a <- e \ "attachments" \ "Attachment") yield {
            Attachment(
              a \ "fileName" text
            , a \ "mimeType" text
            , Base64.decodeBase64(a \ "bytes" text)
            )
          }) toIndexedSeq
      )
    }).toIndexedSeq
}

object XmlToSmsIptRequest {
  def apply(task: Task): IndexedSeq[SmsIptRequest] =
    (for{
        s <- task.payload \ "smses" \ "SmsIptRequest"
        messageText <- (s \ "messageText")
        phone <- (s \ "phone" \ "string")
    } yield {
      SmsIptRequest(
        task = task
      , phone = phone text
      , messageText = messageText text
      )
    }).toIndexedSeq
}

object XmlToIdList {
  def unapply(node: Elem): Option[IndexedSeq[String]] =
    Some(node \ "string" map(_.text) toIndexedSeq)
}