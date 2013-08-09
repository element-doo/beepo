package hr.element.beepo.client

import email._
import sms._
import io._
import xml._

sealed trait Action
case object Persist extends Action

case object Send extends Action {
  def apply(id: String, otherIDs: String*): Send =
    Send(id +: otherIDs)
}

case class Send(val ids: Seq[String]) extends xml.SendXMLConverter with Communicator {
  def send(): String =
    send(Send)
}

object Task {
  def apply(email: Email): Task =
    Task(None, Seq(email), Nil)

  def apply(sms: Sms): Task =
    Task(None, Nil, Seq(sms))

  def apply(email: Email, Sms: Sms): Task =
    Task(None, Seq(email), Seq(Sms))

  def apply(requestID: String, email: Email): Task =
    Task(Some(requestID), Seq(email), Nil)

  def apply(requestID: String, Sms: Sms): Task =
    Task(Some(requestID), Nil, Seq(Sms))

  def apply(requestID: String, email: Email, Sms: Sms): Task =
    Task(Some(requestID), Seq(email), Seq(Sms))
}

case class Task(
    requestID: Option[String]
  , emails: Seq[Email]
  , smses: Seq[Sms]) extends xml.TaskXMLConverter with Communicator {

  def setRequestID(requestID: String) =
    copy(requestID = Some(requestID))

  def add(email: Email, otherEmails: Email*) =
    copy(emails = (emails :+ email) ++ otherEmails)

  def add(sms: Sms, otherSmses: Sms*) =
    copy(smses = (smses :+ sms) ++ otherSmses)

  def persist(): String =
    send(Persist)

  def send(): String =
    send(Send)
}
