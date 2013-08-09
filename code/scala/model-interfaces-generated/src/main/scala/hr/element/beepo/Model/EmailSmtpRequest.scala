package hr.element.beepo
package Model

import hr.ngs.patterns._
import com.fasterxml.jackson.annotation._

class EmailSmtpRequest private(
    @transient private val _locator: Option[IServiceLocator]
  , private var _URI: String
  , private var _ID: Int
  , private var _taskID: java.util.UUID
  , private var _taskURI: String
  , @transient private var _task: Model.Task
  , private var _from: String
  , private var _to: IndexedSeq[String]
  , private var _replyTo: IndexedSeq[String]
  , private var _cc: IndexedSeq[String]
  , private var _bcc: IndexedSeq[String]
  , private var _subject: String
  , private var _textBody: Option[String]
  , private var _htmlBody: Option[String]
  , private var _attachments: IndexedSeq[Model.Attachment]
  , private var _status: Option[Model.RequestStatus]
  ) extends IIdentifiable
    with Serializable {

  @JsonGetter("URI")
  def URI = {

    _URI
  }

  private[Model]def URI_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _URI = value

  }

  override def hashCode = URI.hashCode
  override def equals(o: Any) = o match {
    case c: EmailSmtpRequest => c.URI == URI
    case _ => false
  }

  override def toString = "EmailSmtpRequest("+ URI +")"

  @JsonGetter("ID")
  def ID = {

    _ID
  }

  def ID_= (value: Int) {
    //TODO missing primitive type distinction require(value ne null)

    _ID = value

  }

  @JsonGetter("taskID")
  def taskID = {

    _taskID
  }

  protected[beepo]def taskID_= (value: java.util.UUID) {
    //TODO missing primitive type distinction require(value ne null)

    _taskID = value

  }

  @JsonGetter("taskURI")
  def taskURI = {

    _taskURI
  }

  private[beepo]def taskURI_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _taskURI = value

  }

  def task = {

    if(_locator.isDefined) {
      if (_task == null || _task.URI != taskURI)
        _task = _locator.get[Model.ITaskRepository].find(taskURI).getOrElse(null)
    }
    _task
  }

  def task_= (value: Model.Task) {
    //TODO missing primitive type distinction require(value ne null)

    _task = value

        if(taskID != value.ID)
          taskID = value.ID
    _taskURI = value.URI
  }

  @JsonGetter("from")
  def from = {

    _from
  }

  def from_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _from = value

  }

  @JsonGetter("to")
  def to = {

    _to
  }

  def to_= (value: IndexedSeq[String]) {
    //TODO missing primitive type distinction require(value ne null)

    _to = value

  }

  @JsonGetter("replyTo")
  def replyTo = {

    _replyTo
  }

  def replyTo_= (value: IndexedSeq[String]) {
    //TODO missing primitive type distinction require(value ne null)

    _replyTo = value

  }

  @JsonGetter("cc")
  def cc = {

    _cc
  }

  def cc_= (value: IndexedSeq[String]) {
    //TODO missing primitive type distinction require(value ne null)

    _cc = value

  }

  @JsonGetter("bcc")
  def bcc = {

    _bcc
  }

  def bcc_= (value: IndexedSeq[String]) {
    //TODO missing primitive type distinction require(value ne null)

    _bcc = value

  }

  @JsonGetter("subject")
  def subject = {

    _subject
  }

  def subject_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _subject = value

  }

  @JsonGetter("textBody")
  def textBody = {

    _textBody
  }

  def textBody_= (value: Option[String]) {
    //TODO missing primitive type distinction require(value ne null)

    _textBody = value

  }

  @JsonGetter("htmlBody")
  def htmlBody = {

    _htmlBody
  }

  def htmlBody_= (value: Option[String]) {
    //TODO missing primitive type distinction require(value ne null)

    _htmlBody = value

  }

  @JsonGetter("attachments")
  def attachments = {

    _attachments
  }

  def attachments_= (value: IndexedSeq[Model.Attachment]) {
    //TODO missing primitive type distinction require(value ne null)

    _attachments = value

  }

  @JsonGetter("status")
  def status = {

    _status
  }

  def status_= (value: Option[Model.RequestStatus]) {
    //TODO missing primitive type distinction require(value ne null)

    _status = value

  }

  @JsonCreator private def this(
    @JacksonInject("locator") _locator: IServiceLocator
  , @JsonProperty("URI") URI: String
  , @JsonProperty("ID") ID: Int
  , @JsonProperty("taskID") taskID: java.util.UUID
  , @JsonProperty("taskURI") taskURI: String
  , @JsonProperty("from") from: String
  , @JsonProperty("to") to: IndexedSeq[String]
  , @JsonProperty("replyTo") replyTo: IndexedSeq[String]
  , @JsonProperty("cc") cc: IndexedSeq[String]
  , @JsonProperty("bcc") bcc: IndexedSeq[String]
  , @JsonProperty("subject") subject: String
  , @JsonProperty("textBody") textBody: Option[String]
  , @JsonProperty("htmlBody") htmlBody: Option[String]
  , @JsonProperty("attachments") attachments: IndexedSeq[Model.Attachment]
  , @JsonProperty("status") status: Option[Model.RequestStatus]
  ) =
    this(_locator = Some(_locator), _URI = URI, _ID = ID, _taskID = taskID, _taskURI = taskURI, _task = null, _from = from, _to = to, _replyTo = replyTo, _cc = cc, _bcc = bcc, _subject = subject, _textBody = textBody, _htmlBody = htmlBody, _attachments = attachments, _status = status)

}

object EmailSmtpRequest {

  def apply(
    ID: Int = 0
  , task: Model.Task
  , from: String = ""
  , to: IndexedSeq[String] = IndexedSeq[String]()
  , replyTo: IndexedSeq[String] = IndexedSeq[String]()
  , cc: IndexedSeq[String] = IndexedSeq[String]()
  , bcc: IndexedSeq[String] = IndexedSeq[String]()
  , subject: String = ""
  , textBody: Option[String] = None
  , htmlBody: Option[String] = None
  , attachments: IndexedSeq[Model.Attachment] = IndexedSeq[Model.Attachment]()
  , status: Option[Model.RequestStatus] = None
  ) = {
    if (task == null) throw new IllegalArgumentException("""Argument "task" cannot be null""")
    new EmailSmtpRequest(
      _locator = None
    , _URI = java.util.UUID.randomUUID.toString
    , _ID = ID
    , _taskID = task.ID
    , _taskURI = task.URI
    , _task = task
    , _from = from
    , _to = to
    , _replyTo = replyTo
    , _cc = cc
    , _bcc = bcc
    , _subject = subject
    , _textBody = textBody
    , _htmlBody = htmlBody
    , _attachments = attachments
    , _status = status)
  }

  private[Model] def buildInternal(_locator: IServiceLocator
    , URI: String
    , ID: Int
    , taskID: java.util.UUID
    , taskURI: String
    , task: Model.Task
    , from: String
    , to: IndexedSeq[String]
    , replyTo: IndexedSeq[String]
    , cc: IndexedSeq[String]
    , bcc: IndexedSeq[String]
    , subject: String
    , textBody: Option[String]
    , htmlBody: Option[String]
    , attachments: IndexedSeq[Model.Attachment]
    , status: Option[Model.RequestStatus]) =
    new EmailSmtpRequest(
      _locator = Some(_locator)
    , _URI = URI
    , _ID = ID
    , _taskID = taskID
    , _taskURI = taskURI
    , _task = task
    , _from = from
    , _to = to
    , _replyTo = replyTo
    , _cc = cc
    , _bcc = bcc
    , _subject = subject
    , _textBody = textBody
    , _htmlBody = htmlBody
    , _attachments = attachments
    , _status = status)
}
