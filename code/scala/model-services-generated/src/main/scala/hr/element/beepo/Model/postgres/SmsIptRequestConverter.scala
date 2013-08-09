package hr.element.beepo
package Model.postgres

import hr.ngs.patterns._

import org.pgscala.converters._
import org.pgscala.util._
import hr.ngs.patterns._

object SmsIptRequestConverter {

  private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  def fromPGString(record: String, locator: IServiceLocator): Model.SmsIptRequest = {
    val items = PGRecord.unpack(record)
    Model.SmsIptRequest.buildInternal(
      _locator = locator
    , URI = items(URIPos)
    , ID = PGIntConverter.fromPGString(items(IDPos))
    , taskID = PGUUIDConverter.fromPGString(items(taskIDPos))
    , taskURI = items(taskURIPos)
    , task = null
    , phone = items(phonePos)
    , messageText = items(messageTextPos)
    , status = if (items(statusPos) != null && items(statusPos).nonEmpty) Some(Model.postgres.RequestStatusConverter.fromPGString(items(statusPos), locator)) else None
    , messageLogID = if (items(messageLogIDPos) != null && items(messageLogIDPos).nonEmpty) Some(PGLongConverter.fromPGString(items(messageLogIDPos))) else None
    )
  }

  def fromPGStringExtended(record: String, locator: IServiceLocator): Model.SmsIptRequest = {
    val items = PGRecord.unpack(record)
    Model.SmsIptRequest.buildInternal(
      _locator = locator
    , URI = items(URIPosExtended)
    , ID = PGIntConverter.fromPGString(items(IDPosExtended))
    , taskID = PGUUIDConverter.fromPGString(items(taskIDPosExtended))
    , taskURI = items(taskURIPosExtended)
    , task = null
    , phone = items(phonePosExtended)
    , messageText = items(messageTextPosExtended)
    , status = if (items(statusPosExtended) != null && items(statusPosExtended).nonEmpty) Some(Model.postgres.RequestStatusConverter.fromPGStringExtended(items(statusPosExtended), locator)) else None
    , messageLogID = if (items(messageLogIDPosExtended) != null && items(messageLogIDPosExtended).nonEmpty) Some(PGLongConverter.fromPGString(items(messageLogIDPosExtended))) else None
    )
  }

  def toPGString(item: Model.SmsIptRequest): String = {
    val items = new Array[String](columnCount)
    items(URIPos) = item.URI
    items(IDPos) = PGIntConverter.toPGString(item.ID)
    items(taskIDPos) = PGUUIDConverter.toPGString(item.taskID)
    items(taskURIPos) = item.taskURI
    items(phonePos) = item.phone
    items(messageTextPos) = item.messageText
    items(statusPos) = if(item.status.isDefined) Model.postgres.RequestStatusConverter.toPGString(item.status.get) else null
    items(messageLogIDPos) = if (item.messageLogID.isDefined) PGLongConverter.toPGString(item.messageLogID.get) else null
    PGRecord.pack(items)
  }

  def toPGStringExtended(item: Model.SmsIptRequest): String = {
    val items = new Array[String](extendedColumnCount)
    items(URIPosExtended) = item.URI
    items(IDPosExtended) = PGIntConverter.toPGString(item.ID)
    items(taskIDPosExtended) = PGUUIDConverter.toPGString(item.taskID)
    items(taskURIPosExtended) = item.taskURI
    items(phonePosExtended) = item.phone
    items(messageTextPosExtended) = item.messageText
    items(statusPosExtended) = if(item.status.isDefined) Model.postgres.RequestStatusConverter.toPGString(item.status.get) else null
    items(messageLogIDPosExtended) = if (item.messageLogID.isDefined) PGLongConverter.toPGString(item.messageLogID.get) else null
    PGRecord.pack(items)
  }

  private var columnCount = -1
  private var extendedColumnCount = -1

  def initializeProperties() {

    postgresUtils.getIndexes("Model", "SmsIptRequest_entity").get("URI") match {
      case Some(index) => URIPos = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Model.SmsIptRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_SmsIptRequest_type-").get("URI") match {
      case Some(index) => URIPosExtended = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Model.SmsIptRequest. Check if database is out of sync with code!""")
    }
    columnCount = postgresUtils.getColumnCount("Model", "SmsIptRequest_entity")
    extendedColumnCount = postgresUtils.getColumnCount("Model", "-ngs_SmsIptRequest_type-")
    postgresUtils.getIndexes("Model", "SmsIptRequest_entity").get("ID") match {
      case Some(index) => IDPos = index - 1
      case None => logger.error("""Couldn't find column "ID" in type Model.SmsIptRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_SmsIptRequest_type-").get("ID") match {
      case Some(index) => IDPosExtended = index - 1
      case None => logger.error("""Couldn't find column "ID" in type Model.SmsIptRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "SmsIptRequest_entity").get("taskID") match {
      case Some(index) => taskIDPos = index - 1
      case None => logger.error("""Couldn't find column "taskID" in type Model.SmsIptRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_SmsIptRequest_type-").get("taskID") match {
      case Some(index) => taskIDPosExtended = index - 1
      case None => logger.error("""Couldn't find column "taskID" in type Model.SmsIptRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "SmsIptRequest_entity").get("taskURI") match {
      case Some(index) => taskURIPos = index - 1
      case None => logger.error("""Couldn't find column "taskURI" in type Model.SmsIptRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_SmsIptRequest_type-").get("taskURI") match {
      case Some(index) => taskURIPosExtended = index - 1
      case None => logger.error("""Couldn't find column "taskURI" in type Model.SmsIptRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "SmsIptRequest_entity").get("phone") match {
      case Some(index) => phonePos = index - 1
      case None => logger.error("""Couldn't find column "phone" in type Model.SmsIptRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_SmsIptRequest_type-").get("phone") match {
      case Some(index) => phonePosExtended = index - 1
      case None => logger.error("""Couldn't find column "phone" in type Model.SmsIptRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "SmsIptRequest_entity").get("messageText") match {
      case Some(index) => messageTextPos = index - 1
      case None => logger.error("""Couldn't find column "messageText" in type Model.SmsIptRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_SmsIptRequest_type-").get("messageText") match {
      case Some(index) => messageTextPosExtended = index - 1
      case None => logger.error("""Couldn't find column "messageText" in type Model.SmsIptRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "SmsIptRequest_entity").get("status") match {
      case Some(index) => statusPos = index - 1
      case None => logger.error("""Couldn't find column "status" in type Model.SmsIptRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_SmsIptRequest_type-").get("status") match {
      case Some(index) => statusPosExtended = index - 1
      case None => logger.error("""Couldn't find column "status" in type Model.SmsIptRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "SmsIptRequest_entity").get("messageLogID") match {
      case Some(index) => messageLogIDPos = index - 1
      case None => logger.error("""Couldn't find column "messageLogID" in type Model.SmsIptRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_SmsIptRequest_type-").get("messageLogID") match {
      case Some(index) => messageLogIDPosExtended = index - 1
      case None => logger.error("""Couldn't find column "messageLogID" in type Model.SmsIptRequest. Check if database is out of sync with code!""")
    }
  }

  private var URIPos = -1
  private var URIPosExtended = -1
  private var IDPos = -1
  private var IDPosExtended = -1

  def buildURI(ID: Int) : String = {
    val _uriParts = new Array[String](1)
    _uriParts(0) = PGIntConverter.toPGString(ID)
    postgres.Utils.buildURI(_uriParts)
  }
  private var taskIDPos = -1
  private var taskIDPosExtended = -1
  private var taskURIPos = -1
  private var taskURIPosExtended = -1
  private var phonePos = -1
  private var phonePosExtended = -1
  private var messageTextPos = -1
  private var messageTextPosExtended = -1
  private var statusPos = -1
  private var statusPosExtended = -1
  private var messageLogIDPos = -1
  private var messageLogIDPosExtended = -1
}
