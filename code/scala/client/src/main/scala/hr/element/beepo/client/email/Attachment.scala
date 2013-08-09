package hr.element.beepo.client
package email

case class Attachment(
    filename: String
  , mimeType: String
  , bytes: Array[Byte]) extends xml.AttachmentXMLConverter

import scalax.file.Path

object Attachment {
  def apply(path: Path): Attachment =
    Attachment(path.name, path.byteArray)

  def apply(filename: String, content: Array[Byte]): Attachment =
    Attachment(filename, MimeType.fromFileName(filename), content)
}

import hr.element.onebyseven.common.MimeType.{ values => mimes }

object MimeType {
  val Default = "application/octet-stream"

  def fromFileName(filename: String) =
    filename lastIndexOf '.' match {
      case x if x > 0 =>
        val ext = filename substring (x + 1) toLowerCase;
        mimes find(_.extension == ext) map(_.mimeType) getOrElse(Default)

      case _ =>
        Default
    }
}
