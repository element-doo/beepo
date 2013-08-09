package hr.element.beepo
package Model

import hr.ngs.patterns._

case class Attachment(
    fileName: String = ""
  , mimeType: String = ""
  , bytes: Array[Byte] = Array[Byte]()
  )
