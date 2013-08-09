package hr.element.beepo.client
package xml

import email._
import sms._

import scala.xml._

import hr.element.etb.Pimps._

trait TaskXMLConverter extends XMLConverter { this: Task =>
  def toXml =
<Task>
{(requestID match {
  case Some(r) =>
    <requestID>{ r }</requestID>

  case _ =>
    NodeSeq.Empty
}) ++
(if (emails.nonEmpty) {
  <emails>{ emails.map{ _.toXml } }</emails>
}
else {
  NodeSeq.Empty
}) ++
(if (smses.nonEmpty) {
  <smses>{ smses.map{ _.toXml } }</smses>
}
else {
  NodeSeq.Empty
})}
</Task>.prettyPrint
}
