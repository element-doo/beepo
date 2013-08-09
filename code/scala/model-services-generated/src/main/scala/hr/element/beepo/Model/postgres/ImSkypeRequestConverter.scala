package hr.element.beepo
package Model.postgres

import hr.ngs.patterns._

import org.pgscala.converters._
import org.pgscala.util._
import hr.ngs.patterns._

object ImSkypeRequestConverter {

  private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  def fromPGString(record: String, locator: IServiceLocator): Model.ImSkypeRequest = {
    val items = PGRecord.unpack(record)
    Model.ImSkypeRequest.buildInternal(
      _locator = locator
    , URI = items(URIPos)
    , ID = PGIntConverter.fromPGString(items(IDPos))
    , taskID = PGUUIDConverter.fromPGString(items(taskIDPos))
    , taskURI = items(taskURIPos)
    , task = null
    , status = if (items(statusPos) != null && items(statusPos).nonEmpty) Some(Model.postgres.RequestStatusConverter.fromPGString(items(statusPos), locator)) else None
    )
  }

  def fromPGStringExtended(record: String, locator: IServiceLocator): Model.ImSkypeRequest = {
    val items = PGRecord.unpack(record)
    Model.ImSkypeRequest.buildInternal(
      _locator = locator
    , URI = items(URIPosExtended)
    , ID = PGIntConverter.fromPGString(items(IDPosExtended))
    , taskID = PGUUIDConverter.fromPGString(items(taskIDPosExtended))
    , taskURI = items(taskURIPosExtended)
    , task = null
    , status = if (items(statusPosExtended) != null && items(statusPosExtended).nonEmpty) Some(Model.postgres.RequestStatusConverter.fromPGStringExtended(items(statusPosExtended), locator)) else None
    )
  }

  def toPGString(item: Model.ImSkypeRequest): String = {
    val items = new Array[String](columnCount)
    items(URIPos) = item.URI
    items(IDPos) = PGIntConverter.toPGString(item.ID)
    items(taskIDPos) = PGUUIDConverter.toPGString(item.taskID)
    items(taskURIPos) = item.taskURI
    items(statusPos) = if(item.status.isDefined) Model.postgres.RequestStatusConverter.toPGString(item.status.get) else null
    PGRecord.pack(items)
  }

  def toPGStringExtended(item: Model.ImSkypeRequest): String = {
    val items = new Array[String](extendedColumnCount)
    items(URIPosExtended) = item.URI
    items(IDPosExtended) = PGIntConverter.toPGString(item.ID)
    items(taskIDPosExtended) = PGUUIDConverter.toPGString(item.taskID)
    items(taskURIPosExtended) = item.taskURI
    items(statusPosExtended) = if(item.status.isDefined) Model.postgres.RequestStatusConverter.toPGString(item.status.get) else null
    PGRecord.pack(items)
  }

  private var columnCount = -1
  private var extendedColumnCount = -1

  def initializeProperties() {

    postgresUtils.getIndexes("Model", "ImSkypeRequest_entity").get("URI") match {
      case Some(index) => URIPos = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Model.ImSkypeRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_ImSkypeRequest_type-").get("URI") match {
      case Some(index) => URIPosExtended = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Model.ImSkypeRequest. Check if database is out of sync with code!""")
    }
    columnCount = postgresUtils.getColumnCount("Model", "ImSkypeRequest_entity")
    extendedColumnCount = postgresUtils.getColumnCount("Model", "-ngs_ImSkypeRequest_type-")
    postgresUtils.getIndexes("Model", "ImSkypeRequest_entity").get("ID") match {
      case Some(index) => IDPos = index - 1
      case None => logger.error("""Couldn't find column "ID" in type Model.ImSkypeRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_ImSkypeRequest_type-").get("ID") match {
      case Some(index) => IDPosExtended = index - 1
      case None => logger.error("""Couldn't find column "ID" in type Model.ImSkypeRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "ImSkypeRequest_entity").get("taskID") match {
      case Some(index) => taskIDPos = index - 1
      case None => logger.error("""Couldn't find column "taskID" in type Model.ImSkypeRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_ImSkypeRequest_type-").get("taskID") match {
      case Some(index) => taskIDPosExtended = index - 1
      case None => logger.error("""Couldn't find column "taskID" in type Model.ImSkypeRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "ImSkypeRequest_entity").get("taskURI") match {
      case Some(index) => taskURIPos = index - 1
      case None => logger.error("""Couldn't find column "taskURI" in type Model.ImSkypeRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_ImSkypeRequest_type-").get("taskURI") match {
      case Some(index) => taskURIPosExtended = index - 1
      case None => logger.error("""Couldn't find column "taskURI" in type Model.ImSkypeRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "ImSkypeRequest_entity").get("status") match {
      case Some(index) => statusPos = index - 1
      case None => logger.error("""Couldn't find column "status" in type Model.ImSkypeRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_ImSkypeRequest_type-").get("status") match {
      case Some(index) => statusPosExtended = index - 1
      case None => logger.error("""Couldn't find column "status" in type Model.ImSkypeRequest. Check if database is out of sync with code!""")
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
  private var statusPos = -1
  private var statusPosExtended = -1
}
