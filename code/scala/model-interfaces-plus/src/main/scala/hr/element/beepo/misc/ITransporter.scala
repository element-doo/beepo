package hr.element.beepo
package misc

import javax.mail.Message

trait ITransporter {
  def send(msg: Message)
}