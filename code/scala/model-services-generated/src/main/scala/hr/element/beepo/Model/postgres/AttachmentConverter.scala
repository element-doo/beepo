package hr.element.beepo
package Model.postgres

import hr.ngs.patterns._

import org.pgscala.converters._
import org.pgscala.util._
import hr.ngs.patterns._

object AttachmentConverter {

  private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  def fromPGString(record: String, locator: IServiceLocator): Model.Attachment = {
    val items = PGRecord.unpack(record)
    Model.Attachment(
      fileName = items(fileNamePos)
    , mimeType = items(mimeTypePos)
    , bytes = PGByteArrayConverter.fromPGString(items(bytesPos))
    )
  }

  def fromPGStringExtended(record: String, locator: IServiceLocator): Model.Attachment = {
    val items = PGRecord.unpack(record)
    Model.Attachment(
      fileName = items(fileNamePosExtended)
    , mimeType = items(mimeTypePosExtended)
    , bytes = PGByteArrayConverter.fromPGString(items(bytesPosExtended))
    )
  }

  def toPGString(item: Model.Attachment): String = {
    val items = new Array[String](columnCount)
    items(fileNamePos) = item.fileName
    items(mimeTypePos) = item.mimeType
    items(bytesPos) = PGByteArrayConverter.toPGString(item.bytes)
    PGRecord.pack(items)
  }

  def toPGStringExtended(item: Model.Attachment): String = {
    val items = new Array[String](extendedColumnCount)
    items(fileNamePosExtended) = item.fileName
    items(mimeTypePosExtended) = item.mimeType
    items(bytesPosExtended) = PGByteArrayConverter.toPGString(item.bytes)
    PGRecord.pack(items)
  }

  private var columnCount = -1
  private var extendedColumnCount = -1

  def initializeProperties() {

    columnCount = postgresUtils.getColumnCount("Model", "Attachment")
    extendedColumnCount = postgresUtils.getColumnCount("Model", "-ngs_Attachment_type-")
    postgresUtils.getIndexes("Model", "Attachment").get("fileName") match {
      case Some(index) => fileNamePos = index - 1
      case None => logger.error("""Couldn't find column "fileName" in type Model.Attachment. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_Attachment_type-").get("fileName") match {
      case Some(index) => fileNamePosExtended = index - 1
      case None => logger.error("""Couldn't find column "fileName" in type Model.Attachment. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "Attachment").get("mimeType") match {
      case Some(index) => mimeTypePos = index - 1
      case None => logger.error("""Couldn't find column "mimeType" in type Model.Attachment. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_Attachment_type-").get("mimeType") match {
      case Some(index) => mimeTypePosExtended = index - 1
      case None => logger.error("""Couldn't find column "mimeType" in type Model.Attachment. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "Attachment").get("bytes") match {
      case Some(index) => bytesPos = index - 1
      case None => logger.error("""Couldn't find column "bytes" in type Model.Attachment. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_Attachment_type-").get("bytes") match {
      case Some(index) => bytesPosExtended = index - 1
      case None => logger.error("""Couldn't find column "bytes" in type Model.Attachment. Check if database is out of sync with code!""")
    }
  }

  private var fileNamePos = -1
  private var fileNamePosExtended = -1
  private var mimeTypePos = -1
  private var mimeTypePosExtended = -1
  private var bytesPos = -1
  private var bytesPosExtended = -1
}
