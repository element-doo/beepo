package hr.element.beepo.client
package sms

object Sms {
  def apply(phone: Phone, messageText: String): Sms =
    Sms(Seq(phone), MessageText(messageText))
}

case class Sms(
    phones: Seq[Phone]
  , body: MessageText) extends xml.SmsXMLConverter {

  def add(phone: Phone, otherPhones: Phone*): Sms  =
    copy(phones = this.phones.:+(phone) ++ otherPhones)

  def add(phone: String, otherPhones: String*): Sms  =
    add(Phone(phone), otherPhones.map(Phone(_)): _*)

// -----------------------------------------------------------------------------

  def persist() =
    Task(this).persist()

  def persist(requestID: String) =
    Task(requestID, this).persist()

  def send() =
    Task(this).send()

  def send(requestID: String) =
    Task(requestID, this).send()
}
