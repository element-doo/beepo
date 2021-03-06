package hr.element.beepo.client
package xml

import email._

import scala.xml.NodeSeq
import hr.element.etb.Pimps._

import org.apache.commons.codec.binary.Base64

trait AttachmentXMLConverter extends XMLConverter { this: Attachment =>
  def toXml =
<Attachment>
  <fileName>{ filename }</fileName>
  <mimeType>{ mimeType }</mimeType>
  <bytes>{ new String(Base64.encodeBase64Chunked(bytes), "UTF-8").trim } </bytes>
</Attachment>.prettyPrint
}
