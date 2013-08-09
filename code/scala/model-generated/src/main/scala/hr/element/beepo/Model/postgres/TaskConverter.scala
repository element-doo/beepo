package hr.element.beepo
package Model.postgres

import hr.ngs.patterns._

import org.pgscala.converters._
import org.pgscala.util._
import hr.ngs.patterns._

object TaskConverter {

  private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  def fromPGString(record: String, locator: IServiceLocator): Model.Task = {
    val items = PGRecord.unpack(record)
    Model.Task.buildInternal(
      _locator = locator
    , URI = items(URIPos)
    , ID = PGUUIDConverter.fromPGString(items(IDPos))
    , requestID = if (items(requestIDPos) != null && items(requestIDPos).nonEmpty) Some(items(requestIDPos)) else None
    , payload = PGElemConverter.fromPGString(items(payloadPos))
    , receivedAt = PGDateTimeConverter.fromPGString(items(receivedAtPos))
    )
  }

  def fromPGStringExtended(record: String, locator: IServiceLocator): Model.Task = {
    val items = PGRecord.unpack(record)
    Model.Task.buildInternal(
      _locator = locator
    , URI = items(URIPosExtended)
    , ID = PGUUIDConverter.fromPGString(items(IDPosExtended))
    , requestID = if (items(requestIDPosExtended) != null && items(requestIDPosExtended).nonEmpty) Some(items(requestIDPosExtended)) else None
    , payload = PGElemConverter.fromPGString(items(payloadPosExtended))
    , receivedAt = PGDateTimeConverter.fromPGString(items(receivedAtPosExtended))
    )
  }

  def toPGString(item: Model.Task): String = {
    val items = new Array[String](columnCount)
    items(URIPos) = item.URI
    items(IDPos) = PGUUIDConverter.toPGString(item.ID)
    items(requestIDPos) = if (item.requestID.isDefined) item.requestID.get else null
    items(payloadPos) = PGElemConverter.toPGString(item.payload)
    items(receivedAtPos) = PGDateTimeConverter.toPGString(item.receivedAt)
    PGRecord.pack(items)
  }

  def toPGStringExtended(item: Model.Task): String = {
    val items = new Array[String](extendedColumnCount)
    items(URIPosExtended) = item.URI
    items(IDPosExtended) = PGUUIDConverter.toPGString(item.ID)
    items(requestIDPosExtended) = if (item.requestID.isDefined) item.requestID.get else null
    items(payloadPosExtended) = PGElemConverter.toPGString(item.payload)
    items(receivedAtPosExtended) = PGDateTimeConverter.toPGString(item.receivedAt)
    PGRecord.pack(items)
  }

  private var columnCount = -1
  private var extendedColumnCount = -1

  def initializeProperties() {

    postgresUtils.getIndexes("Model", "Task_entity").get("URI") match {
      case Some(index) => URIPos = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Model.Task_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_Task_type-").get("URI") match {
      case Some(index) => URIPosExtended = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Model.Task. Check if database is out of sync with code!""")
    }
    columnCount = postgresUtils.getColumnCount("Model", "Task_entity")
    extendedColumnCount = postgresUtils.getColumnCount("Model", "-ngs_Task_type-")
    postgresUtils.getIndexes("Model", "Task_entity").get("ID") match {
      case Some(index) => IDPos = index - 1
      case None => logger.error("""Couldn't find column "ID" in type Model.Task_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_Task_type-").get("ID") match {
      case Some(index) => IDPosExtended = index - 1
      case None => logger.error("""Couldn't find column "ID" in type Model.Task. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "Task_entity").get("requestID") match {
      case Some(index) => requestIDPos = index - 1
      case None => logger.error("""Couldn't find column "requestID" in type Model.Task_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_Task_type-").get("requestID") match {
      case Some(index) => requestIDPosExtended = index - 1
      case None => logger.error("""Couldn't find column "requestID" in type Model.Task. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "Task_entity").get("payload") match {
      case Some(index) => payloadPos = index - 1
      case None => logger.error("""Couldn't find column "payload" in type Model.Task_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_Task_type-").get("payload") match {
      case Some(index) => payloadPosExtended = index - 1
      case None => logger.error("""Couldn't find column "payload" in type Model.Task. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "Task_entity").get("receivedAt") match {
      case Some(index) => receivedAtPos = index - 1
      case None => logger.error("""Couldn't find column "receivedAt" in type Model.Task_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_Task_type-").get("receivedAt") match {
      case Some(index) => receivedAtPosExtended = index - 1
      case None => logger.error("""Couldn't find column "receivedAt" in type Model.Task. Check if database is out of sync with code!""")
    }
  }

  private var URIPos = -1
  private var URIPosExtended = -1

  def buildURI(ID: java.util.UUID) : String = {
    val _uriParts = new Array[String](1)
    _uriParts(0) = PGUUIDConverter.toPGString(ID)
    postgres.Utils.buildURI(_uriParts)
  }
  private var IDPos = -1
  private var IDPosExtended = -1
  private var requestIDPos = -1
  private var requestIDPosExtended = -1
  private var payloadPos = -1
  private var payloadPosExtended = -1
  private var receivedAtPos = -1
  private var receivedAtPosExtended = -1
}
