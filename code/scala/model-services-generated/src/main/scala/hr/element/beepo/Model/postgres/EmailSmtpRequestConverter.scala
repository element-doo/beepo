package hr.element.beepo
package Model.postgres

import hr.ngs.patterns._

import org.pgscala.converters._
import org.pgscala.util._
import hr.ngs.patterns._

object EmailSmtpRequestConverter {

  private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  def fromPGString(record: String, locator: IServiceLocator): Model.EmailSmtpRequest = {
    val items = PGRecord.unpack(record)
    Model.EmailSmtpRequest.buildInternal(
      _locator = locator
    , URI = items(URIPos)
    , ID = PGIntConverter.fromPGString(items(IDPos))
    , taskID = PGUUIDConverter.fromPGString(items(taskIDPos))
    , taskURI = items(taskURIPos)
    , task = null
    , from = items(fromPos)
    , to = postgres.Utils.parseIndexedSeq(items(toPos), PGStringConverter.fromPGString)
    , replyTo = postgres.Utils.parseIndexedSeq(items(replyToPos), PGStringConverter.fromPGString)
    , cc = postgres.Utils.parseIndexedSeq(items(ccPos), PGStringConverter.fromPGString)
    , bcc = postgres.Utils.parseIndexedSeq(items(bccPos), PGStringConverter.fromPGString)
    , subject = items(subjectPos)
    , textBody = if (items(textBodyPos) != null && items(textBodyPos).nonEmpty) Some(items(textBodyPos)) else None
    , htmlBody = if (items(htmlBodyPos) != null && items(htmlBodyPos).nonEmpty) Some(items(htmlBodyPos)) else None
    , attachments = postgres.Utils.parseIndexedSeq(items(attachmentsPos), it => Model.postgres.AttachmentConverter.fromPGString(it, locator))
    , status = if (items(statusPos) != null && items(statusPos).nonEmpty) Some(Model.postgres.RequestStatusConverter.fromPGString(items(statusPos), locator)) else None
    )
  }

  def fromPGStringExtended(record: String, locator: IServiceLocator): Model.EmailSmtpRequest = {
    val items = PGRecord.unpack(record)
    Model.EmailSmtpRequest.buildInternal(
      _locator = locator
    , URI = items(URIPosExtended)
    , ID = PGIntConverter.fromPGString(items(IDPosExtended))
    , taskID = PGUUIDConverter.fromPGString(items(taskIDPosExtended))
    , taskURI = items(taskURIPosExtended)
    , task = null
    , from = items(fromPosExtended)
    , to = postgres.Utils.parseIndexedSeq(items(toPosExtended), PGStringConverter.fromPGString)
    , replyTo = postgres.Utils.parseIndexedSeq(items(replyToPosExtended), PGStringConverter.fromPGString)
    , cc = postgres.Utils.parseIndexedSeq(items(ccPosExtended), PGStringConverter.fromPGString)
    , bcc = postgres.Utils.parseIndexedSeq(items(bccPosExtended), PGStringConverter.fromPGString)
    , subject = items(subjectPosExtended)
    , textBody = if (items(textBodyPosExtended) != null && items(textBodyPosExtended).nonEmpty) Some(items(textBodyPosExtended)) else None
    , htmlBody = if (items(htmlBodyPosExtended) != null && items(htmlBodyPosExtended).nonEmpty) Some(items(htmlBodyPosExtended)) else None
    , attachments = postgres.Utils.parseIndexedSeq(items(attachmentsPosExtended), it => Model.postgres.AttachmentConverter.fromPGStringExtended(it, locator))
    , status = if (items(statusPosExtended) != null && items(statusPosExtended).nonEmpty) Some(Model.postgres.RequestStatusConverter.fromPGStringExtended(items(statusPosExtended), locator)) else None
    )
  }

  def toPGString(item: Model.EmailSmtpRequest): String = {
    val items = new Array[String](columnCount)
    items(URIPos) = item.URI
    items(IDPos) = PGIntConverter.toPGString(item.ID)
    items(taskIDPos) = PGUUIDConverter.toPGString(item.taskID)
    items(taskURIPos) = item.taskURI
    items(fromPos) = item.from
    items(toPos) = postgres.Utils.createIndexedSeq(item.to, PGStringConverter.toPGString _)
    items(replyToPos) = postgres.Utils.createIndexedSeq(item.replyTo, PGStringConverter.toPGString _)
    items(ccPos) = postgres.Utils.createIndexedSeq(item.cc, PGStringConverter.toPGString _)
    items(bccPos) = postgres.Utils.createIndexedSeq(item.bcc, PGStringConverter.toPGString _)
    items(subjectPos) = item.subject
    items(textBodyPos) = if (item.textBody.isDefined) item.textBody.get else null
    items(htmlBodyPos) = if (item.htmlBody.isDefined) item.htmlBody.get else null
    items(attachmentsPos) = postgres.Utils.createIndexedSeq(item.attachments, Model.postgres.AttachmentConverter.toPGString _)
    items(statusPos) = if(item.status.isDefined) Model.postgres.RequestStatusConverter.toPGString(item.status.get) else null
    PGRecord.pack(items)
  }

  def toPGStringExtended(item: Model.EmailSmtpRequest): String = {
    val items = new Array[String](extendedColumnCount)
    items(URIPosExtended) = item.URI
    items(IDPosExtended) = PGIntConverter.toPGString(item.ID)
    items(taskIDPosExtended) = PGUUIDConverter.toPGString(item.taskID)
    items(taskURIPosExtended) = item.taskURI
    items(fromPosExtended) = item.from
    items(toPosExtended) = postgres.Utils.createIndexedSeq(item.to, PGStringConverter.toPGString _)
    items(replyToPosExtended) = postgres.Utils.createIndexedSeq(item.replyTo, PGStringConverter.toPGString _)
    items(ccPosExtended) = postgres.Utils.createIndexedSeq(item.cc, PGStringConverter.toPGString _)
    items(bccPosExtended) = postgres.Utils.createIndexedSeq(item.bcc, PGStringConverter.toPGString _)
    items(subjectPosExtended) = item.subject
    items(textBodyPosExtended) = if (item.textBody.isDefined) item.textBody.get else null
    items(htmlBodyPosExtended) = if (item.htmlBody.isDefined) item.htmlBody.get else null
    items(attachmentsPosExtended) = postgres.Utils.createIndexedSeq(item.attachments, Model.postgres.AttachmentConverter.toPGStringExtended _)
    items(statusPosExtended) = if(item.status.isDefined) Model.postgres.RequestStatusConverter.toPGString(item.status.get) else null
    PGRecord.pack(items)
  }

  private var columnCount = -1
  private var extendedColumnCount = -1

  def initializeProperties() {

    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("URI") match {
      case Some(index) => URIPos = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("URI") match {
      case Some(index) => URIPosExtended = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
    }
    columnCount = postgresUtils.getColumnCount("Model", "EmailSmtpRequest_entity")
    extendedColumnCount = postgresUtils.getColumnCount("Model", "-ngs_EmailSmtpRequest_type-")
    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("ID") match {
      case Some(index) => IDPos = index - 1
      case None => logger.error("""Couldn't find column "ID" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("ID") match {
      case Some(index) => IDPosExtended = index - 1
      case None => logger.error("""Couldn't find column "ID" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("taskID") match {
      case Some(index) => taskIDPos = index - 1
      case None => logger.error("""Couldn't find column "taskID" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("taskID") match {
      case Some(index) => taskIDPosExtended = index - 1
      case None => logger.error("""Couldn't find column "taskID" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("taskURI") match {
      case Some(index) => taskURIPos = index - 1
      case None => logger.error("""Couldn't find column "taskURI" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("taskURI") match {
      case Some(index) => taskURIPosExtended = index - 1
      case None => logger.error("""Couldn't find column "taskURI" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("from") match {
      case Some(index) => fromPos = index - 1
      case None => logger.error("""Couldn't find column "from" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("from") match {
      case Some(index) => fromPosExtended = index - 1
      case None => logger.error("""Couldn't find column "from" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("to") match {
      case Some(index) => toPos = index - 1
      case None => logger.error("""Couldn't find column "to" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("to") match {
      case Some(index) => toPosExtended = index - 1
      case None => logger.error("""Couldn't find column "to" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("replyTo") match {
      case Some(index) => replyToPos = index - 1
      case None => logger.error("""Couldn't find column "replyTo" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("replyTo") match {
      case Some(index) => replyToPosExtended = index - 1
      case None => logger.error("""Couldn't find column "replyTo" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("cc") match {
      case Some(index) => ccPos = index - 1
      case None => logger.error("""Couldn't find column "cc" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("cc") match {
      case Some(index) => ccPosExtended = index - 1
      case None => logger.error("""Couldn't find column "cc" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("bcc") match {
      case Some(index) => bccPos = index - 1
      case None => logger.error("""Couldn't find column "bcc" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("bcc") match {
      case Some(index) => bccPosExtended = index - 1
      case None => logger.error("""Couldn't find column "bcc" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("subject") match {
      case Some(index) => subjectPos = index - 1
      case None => logger.error("""Couldn't find column "subject" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("subject") match {
      case Some(index) => subjectPosExtended = index - 1
      case None => logger.error("""Couldn't find column "subject" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("textBody") match {
      case Some(index) => textBodyPos = index - 1
      case None => logger.error("""Couldn't find column "textBody" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("textBody") match {
      case Some(index) => textBodyPosExtended = index - 1
      case None => logger.error("""Couldn't find column "textBody" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("htmlBody") match {
      case Some(index) => htmlBodyPos = index - 1
      case None => logger.error("""Couldn't find column "htmlBody" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("htmlBody") match {
      case Some(index) => htmlBodyPosExtended = index - 1
      case None => logger.error("""Couldn't find column "htmlBody" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("attachments") match {
      case Some(index) => attachmentsPos = index - 1
      case None => logger.error("""Couldn't find column "attachments" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("attachments") match {
      case Some(index) => attachmentsPosExtended = index - 1
      case None => logger.error("""Couldn't find column "attachments" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "EmailSmtpRequest_entity").get("status") match {
      case Some(index) => statusPos = index - 1
      case None => logger.error("""Couldn't find column "status" in type Model.EmailSmtpRequest_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Model", "-ngs_EmailSmtpRequest_type-").get("status") match {
      case Some(index) => statusPosExtended = index - 1
      case None => logger.error("""Couldn't find column "status" in type Model.EmailSmtpRequest. Check if database is out of sync with code!""")
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
  private var fromPos = -1
  private var fromPosExtended = -1
  private var toPos = -1
  private var toPosExtended = -1
  private var replyToPos = -1
  private var replyToPosExtended = -1
  private var ccPos = -1
  private var ccPosExtended = -1
  private var bccPos = -1
  private var bccPosExtended = -1
  private var subjectPos = -1
  private var subjectPosExtended = -1
  private var textBodyPos = -1
  private var textBodyPosExtended = -1
  private var htmlBodyPos = -1
  private var htmlBodyPosExtended = -1
  private var attachmentsPos = -1
  private var attachmentsPosExtended = -1
  private var statusPos = -1
  private var statusPosExtended = -1
}
