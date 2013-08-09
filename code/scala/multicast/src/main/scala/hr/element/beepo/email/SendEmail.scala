package hr.element.beepo
package email

import Model._
import javax.mail._
import util.ByteArrayDataSource
import javax.activation._
import javax.mail.internet._
import javax.naming.{Context, InitialContext}
import javax.activation.FileDataSource
import javax.activation.DataHandler
import javax.mail.util.ByteArrayDataSource
import java.io.File
import processor._
import java.util.Properties
import scala.xml.XML

object SendEmail extends IEmailSender with Logger {

  def apply(email: Model.EmailSmtpRequest) = send(email)

  def apply(emails: IndexedSeq[Model.EmailSmtpRequest]) = send(emails)

  def send(emails: IndexedSeq[Model.EmailSmtpRequest]): IndexedSeq[EmailStatus] =
    emails.map{send(_)}

  def send(e: EmailSmtpRequest) =
    blockingSendMail(
        e.from
      , e.to.head
      , e.replyTo
      , e.subject
      , e.cc
      , e.bcc
      , e.textBody
      , e.htmlBody
      , e.attachments
      )

  def blockingSendMail(
      from: String
    , to: String
    , replyTo: Seq[String]
    , subject: String
    , cc: Seq[String]
    , bcc: Seq[String]
    , textBody: Option[String]
    , htmlBody: Option[String]
    , attachments: IndexedSeq[Attachment]
    ): EmailStatus = {

    val props = System.getProperties

    import Repositories._
    val auth = new javax.mail.Authenticator(){
      override protected def getPasswordAuthentication() = {
        new PasswordAuthentication(emailCreds.username, emailCreds.password)
      }
    }

    props.put("mail.smtp.host", emailCreds.smtpHost);
    props.put("mail.smtp.port", emailCreds.smtpPort);
    props.put("mail.smtp.auth", "true");

// yahoo :
//  props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//  props.put("mail.smtp.ssl", "true");

// gmail
//    props.put("mail.smtp.starttls.enable", "true");
//    props.put("mail.transport.protocol", "smtp")
//    props.put("mail.smtp.host", "smtp.gmail.com")
//    props.put("mail.smtp.port","25") //default
//    props.put("mail.smtp.auth", "false")
    val session = Session.getInstance( props, auth)
    val message = new MimeMessage(session)

    message.setFrom(new InternetAddress(from))
    message.setRecipients(Message.RecipientType.TO, to)
    message.addRecipients(
        Message.RecipientType.CC
      , cc.map{ new InternetAddress(_) }.toArray[Address]
      )
    message.addRecipients(
        Message.RecipientType.BCC
      , bcc.map{ new InternetAddress(_) }.toArray[Address]
      )

    message.setReplyTo(replyTo.map{new InternetAddress(_)} toArray)
    message.setSubject(MimeUtility.encodeText(subject, "utf-8", "Q"))

    if (textBody.isDefined & htmlBody.isEmpty & attachments.isEmpty)
    {
      message.setText(textBody.get, "UTF-8")
    }
    else
    {
      val multiPart = new MimeMultipart("alternative")
      for( body <- textBody )
      {
        val bp = new MimeBodyPart
        bp.setText(body, "UTF-8")
        multiPart.addBodyPart(bp)
      }

      multiPart.addBodyPart(buildHtmlPart(htmlBody, attachments))
      message.setContent(multiPart)
    }
    try
    {
      Repositories.transport.send(message)
      Success
    }
    catch
    {
      case e =>
        EmailError(e)
    }
  }

  private def buildHtmlPart(
    html: Option[String]
  , attachments: IndexedSeq[Attachment]
  ) = {
    val bp = new MimeBodyPart
    val mix = new MimeMultipart("related")
    val imgs = html.map{
      body =>
        val bp = new MimeBodyPart
        bp.setContent(body, "text/html;charset=UTF-8")
        mix.addBodyPart(bp)
        attachments.map(_.fileName).filter(body.contains(_))
      }.getOrElse(IndexedSeq.empty)

    for( attach <- attachments )
    {
      val bp = new MimeBodyPart
      bp.setDataHandler(new DataHandler(
          new ByteArrayDataSource(attach.bytes, attach.mimeType)
          ))
      if (imgs.contains(attach.fileName))
        bp.setDisposition("inline")
      else
        bp.setDisposition("attachment")
      bp.setFileName(attach.fileName)
      bp.setContentID("<"+attach.fileName+">")
      mix.addBodyPart(bp)
    }
    bp.setContent(mix)
    bp
  }
}
