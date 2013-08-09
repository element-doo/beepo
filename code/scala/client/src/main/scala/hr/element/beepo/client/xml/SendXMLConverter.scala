package hr.element.beepo.client
package xml

import email._
import sms._

import scala.xml._

import hr.element.etb.Pimps._

trait SendXMLConverter extends XMLConverter { this: Send =>
  def toXml =
<Send>
{for(id <- ids) yield <string>{ id }</string>}
</Send>.prettyPrint
}
