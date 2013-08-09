package hr.element.beepo
package Model.postgres

import hr.ngs.patterns._

import org.pgscala.converters._
import org.pgscala.util._
import hr.ngs.patterns._

object RequestStatusConverter {

  private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  def fromPGString(record: String, locator: IServiceLocator): Model.RequestStatus = {
    val items = PGRecord.unpack(record)
    Model.RequestStatus(
      queuedAt = PGDateTimeConverter.fromPGString(items(queuedAtPos))
    , sentAt = if (items(sentAtPos) != null && items(sentAtPos).nonEmpty) Some(PGDateTimeConverter.fromPGString(items(sentAtPos))) else None
    , status = items(statusPos)
    , serverResponse = if (items(serverResponsePos) != null && items(serverResponsePos).nonEmpty) Some(items(serverResponsePos)) else None
    )
  }

  def fromPGStringExtended(record: String, locator: IServiceLocator): Model.RequestStatus = {
    val items = PGRecord.unpack(record)
    Model.RequestStatus(
      queuedAt = PGDateTimeConverter.fromPGString(items(queuedAtPosExtended))
    , sentAt = if (items(sentAtPosExtended) != null && items(sentAtPosExtended).nonEmpty) Some(PGDateTimeConverter.fromPGString(items(sentAtPosExtended))) else None
    , status = items(statusPosExtended)
    , serverResponse = if (items(serverResponsePosExtended) != null && items(serverResponsePosExtended).nonEmpty) Some(items(serverResponsePosExtended)) else None
    )
  }

  def toPGString(item: Model.RequestStatus): String = {
    val items = new Array[String](columnCount)
    items(queuedAtPos) = PGDateTimeConverter.toPGString(item.queuedAt)
    items(sentAtPos) = if (item.sentAt.isDefined) PGDateTimeConverter.toPGString(item.sentAt.get) else null
    items(statusPos) = item.status
    items(serverResponsePos) = if (item.serverResponse.isDefined) item.serverResponse.get else null
    PGRecord.pack(items)
  }

  def toPGStringExtended(item: Model.RequestStatus): String = {
    val items = new Array[String](extendedColumnCount)
    items(queuedAtPosExtended) = PGDateTimeConverter.toPGString(item.queuedAt)
    items(sentAtPosExtended) = if (item.sentAt.isDefined) PGDateTimeConverter.toPGString(item.sentAt.get) else null
    items(statusPosExtended) = item.status
    items(serverResponsePosExtended) = if (item.serverResponse.isDefined) item.serverResponse.get else null
    PGRecord.pack(items)
  }

  private var columnCount = -1
  private var extendedColumnCount = -1

  def initializeProperties() {

    columnCount = postgresUtils.getColumnCount("Model", "RequestStatus")
    extendedColumnCount = postgresUtils.getColumnCount("Model", "-ngs_RequestStatus_type-")
    postgresUtils.getIndexes("Model", "RequestStatus").get("queuedAt") match {
      case Some(index) => queuedAtPos = index - 1
      case None => logger.error("""Couldn't find column "queuedAt" in type Model.RequestStatus. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_RequestStatus_type-").get("queuedAt") match {
      case Some(index) => queuedAtPosExtended = index - 1
      case None => logger.error("""Couldn't find column "queuedAt" in type Model.RequestStatus. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "RequestStatus").get("sentAt") match {
      case Some(index) => sentAtPos = index - 1
      case None => logger.error("""Couldn't find column "sentAt" in type Model.RequestStatus. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_RequestStatus_type-").get("sentAt") match {
      case Some(index) => sentAtPosExtended = index - 1
      case None => logger.error("""Couldn't find column "sentAt" in type Model.RequestStatus. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "RequestStatus").get("status") match {
      case Some(index) => statusPos = index - 1
      case None => logger.error("""Couldn't find column "status" in type Model.RequestStatus. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_RequestStatus_type-").get("status") match {
      case Some(index) => statusPosExtended = index - 1
      case None => logger.error("""Couldn't find column "status" in type Model.RequestStatus. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "RequestStatus").get("serverResponse") match {
      case Some(index) => serverResponsePos = index - 1
      case None => logger.error("""Couldn't find column "serverResponse" in type Model.RequestStatus. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_RequestStatus_type-").get("serverResponse") match {
      case Some(index) => serverResponsePosExtended = index - 1
      case None => logger.error("""Couldn't find column "serverResponse" in type Model.RequestStatus. Check if database is out of sync with code!""")
    }
  }

  private var queuedAtPos = -1
  private var queuedAtPosExtended = -1
  private var sentAtPos = -1
  private var sentAtPosExtended = -1
  private var statusPos = -1
  private var statusPosExtended = -1
  private var serverResponsePos = -1
  private var serverResponsePosExtended = -1
}
