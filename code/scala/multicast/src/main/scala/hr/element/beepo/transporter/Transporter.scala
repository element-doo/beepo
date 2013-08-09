package hr.element.beepo
package transporter

import javax.mail._

object Transporter extends misc.ITransporter with Logger {
  def send(msg: Message) = {
    logger.info("Sending message with subject: %s" format msg.getSubject())
    Transport.send(msg)
  }
}