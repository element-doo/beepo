package hr.element.beepo.client
package xml

import scala.xml.Elem

trait XMLConverter {
  def toXml: Elem
}
