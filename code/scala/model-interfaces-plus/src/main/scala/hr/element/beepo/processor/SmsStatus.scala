package hr.element.beepo.processor

sealed trait SmsStatus{
  val status: String
}

sealed trait SendStatus   extends SmsStatus
sealed trait CheckStatus  extends SmsStatus

case class Sent(msgId: Long) extends SendStatus{
  val status = "Success."
  override val toString = "Message sent."
}
case class SmsError(code: Int, description: String) extends SendStatus{
  val status = "Fail."
  override val toString = "Server responded with code %s: %s." format (code, description)
}

case object Received extends CheckStatus{
  val status = "UnDefined."
}

case class NotReceived(code: Int, description: String) extends CheckStatus{
  val status = "UnDefined."
}
