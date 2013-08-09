package hr.element.beepo
package processor

import Model._
import org.joda.time.DateTime

class TaskProcessor extends ITaskProcessor with Logger { //TODO: make messages external

  import Repositories._
  def send(
      task: Task
    , emails: IndexedSeq[EmailSmtpRequest]
    , smss: IndexedSeq[SmsIptRequest]
    ) = {
    logger.info("Proccessing task %s for send task." format(task.ID))

    println("count:" +emails.size)
    val r = taskRepository.insert(task)
    emailRepository.insert(emails)
    smsRepository.insert(smss)
    "Task uuid: %s" format (r)  + "\n"+
    emails.map{ sendEmail(_) }.mkString("\n") + "\n"+
    smss.map{ sendSms(_) }.mkString("\n")
  }

  def enqueue(
      task: Task
    , emails: IndexedSeq[EmailSmtpRequest]
    , smss: IndexedSeq[SmsIptRequest]
    ) = {
      val r = task.requestID match {
        case Some(r) =>
          require(
            Repositories.taskRepository.findByRequestIDorURI(r).isEmpty
          , "Request ID %s was not unique!" format r
          )
          Repositories.taskRepository.insert(task)
          Repositories.emailRepository.insert(emails)
          Repositories.smsRepository.insert(smss)
          r
        case _ =>
          val r = Repositories.taskRepository.insert(task)
          Repositories.emailRepository.insert(emails)
          Repositories.smsRepository.insert(smss)
          r
      }
      logger.info("Proccessing task %s for enqueueing." format(task.ID))
      r
  }

  def send(aListOfIds: IndexedSeq[String]) = {

    (
    for(task <- Repositories.taskRepository.findByRequestIDorURI(aListOfIds) )
    yield {
      logger.info("Proccessing task %s for send from que." format(task.ID))
      "Task uuid: %s" format (task.ID)  + "\n"+
      sendEmailsFor(task.ID.toString) +
      sendSmsFor(task.ID.toString)
    }) mkString("\n")

  }

  private def sendSmsFor(taskID: String) =
    Repositories.
      smsRepository.
        getSmssByTaskID(taskID).
          map{sendSms _ } mkString("\n")

  private def sendEmailsFor(taskID: String) =
    Repositories.
      emailRepository.
        getEmailsByTaskId(taskID).
          map{ sendEmail _ } mkString("\n")

  private def sendSms(smsMsg: SmsIptRequest): String =
  {
    val sendRes = sms.SendSms(smsMsg)
    smsMsg.status = Some(updateSmsStatus(smsMsg, sendRes))
    hrSmsStatus(smsMsg, sendRes)
  }

  private def sendEmail(mail: EmailSmtpRequest): String =
  {
    val sendResp = email.SendEmail(mail)
    mail.status = Some(updateEmailStatus(mail, sendResp ))
    emailRepository.update(mail)
    hrEmailStatus(mail,sendResp)
  }

  private def hrSmsStatus(sms: SmsIptRequest, s: SendStatus) =
    s match {
          case Sent(x)      =>
            "SMS message send to %s with id %s." format(sms.phone, x)
          case SmsError(c,d)=>
            "SMS message to %s failed because %s."  format(sms.phone, d)
        }

  private def hrEmailStatus(email: EmailSmtpRequest, s: EmailStatus) =
    s match {
      case Success        => "Email message sent to %s, from %s." format(email.to.mkString(";"), email.from)
      case EmailError(e)  => "Email message to %s failed because %s."  format(email.from, e.getMessage)
    }

  private def updateEmailStatus(sms: EmailSmtpRequest, resp: EmailStatus): RequestStatus = {
    val time = DateTime.now()
    val firstTime = (sms.status) match {
      case Some(status)=>
        status.queuedAt
      case None =>
        time
    }

    val (newStatus, serverResponse) = resp match {
      case Success => ("Success", None)
      case EmailError(x) => ("Fail", Some(x.getMessage))
    }

    RequestStatus(
        queuedAt  = firstTime
      , sentAt    = Some(time)
      , status    = newStatus
      , serverResponse = serverResponse
      )
  }

  private def updateSmsStatus(sms: SmsIptRequest, resp: SendStatus): RequestStatus = {
    val time = DateTime.now()
    val firstTime = (sms.status) match {
      case Some(status)=>
        status.queuedAt
      case None =>
        time
    }
    RequestStatus(
        queuedAt  = firstTime
      , sentAt    = Some(time)
      , status    = resp.status
      , serverResponse = Some(resp.toString)
      )
  }
}