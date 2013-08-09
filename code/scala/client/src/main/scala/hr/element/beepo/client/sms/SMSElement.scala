package hr.element.beepo.client
package sms

sealed abstract class SmsElement[T](value: T) {
  override val toString = value.toString
}

case class MessageText(body: String) extends SmsElement[String](body)
