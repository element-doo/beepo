package hr.element.beepo
package Model

import hr.ngs.patterns._
import com.fasterxml.jackson.annotation._

class SmsIptRequest private(
    @transient private val _locator: Option[IServiceLocator]
  , private var _URI: String
  , private var _ID: Int
  , private var _taskID: java.util.UUID
  , private var _taskURI: String
  , @transient private var _task: Model.Task
  , private var _phone: String
  , private var _messageText: String
  , private var _status: Option[Model.RequestStatus]
  , private var _messageLogID: Option[Long]
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
    case c: SmsIptRequest => c.URI == URI
    case _ => false
  }

  override def toString = "SmsIptRequest("+ URI +")"

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

  @JsonGetter("phone")
  def phone = {

    _phone
  }

  def phone_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _phone = value

  }

  @JsonGetter("messageText")
  def messageText = {

    _messageText
  }

  def messageText_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _messageText = value

  }

  @JsonGetter("status")
  def status = {

    _status
  }

  def status_= (value: Option[Model.RequestStatus]) {
    //TODO missing primitive type distinction require(value ne null)

    _status = value

  }

  @JsonGetter("messageLogID")
  def messageLogID = {

    _messageLogID
  }

  def messageLogID_= (value: Option[Long]) {
    //TODO missing primitive type distinction require(value ne null)

    _messageLogID = value

  }

  @JsonCreator private def this(
    @JacksonInject("locator") _locator: IServiceLocator
  , @JsonProperty("URI") URI: String
  , @JsonProperty("ID") ID: Int
  , @JsonProperty("taskID") taskID: java.util.UUID
  , @JsonProperty("taskURI") taskURI: String
  , @JsonProperty("phone") phone: String
  , @JsonProperty("messageText") messageText: String
  , @JsonProperty("status") status: Option[Model.RequestStatus]
  , @JsonProperty("messageLogID") messageLogID: Option[Long]
  ) =
    this(_locator = Some(_locator), _URI = URI, _ID = ID, _taskID = taskID, _taskURI = taskURI, _task = null, _phone = phone, _messageText = messageText, _status = status, _messageLogID = messageLogID)

}

object SmsIptRequest {

  def apply(
    ID: Int = 0
  , task: Model.Task
  , phone: String = ""
  , messageText: String = ""
  , status: Option[Model.RequestStatus] = None
  , messageLogID: Option[Long] = None
  ) = {
    if (task == null) throw new IllegalArgumentException("""Argument "task" cannot be null""")
    new SmsIptRequest(
      _locator = None
    , _URI = java.util.UUID.randomUUID.toString
    , _ID = ID
    , _taskID = task.ID
    , _taskURI = task.URI
    , _task = task
    , _phone = phone
    , _messageText = messageText
    , _status = status
    , _messageLogID = messageLogID)
  }

  private[Model] def buildInternal(_locator: IServiceLocator
    , URI: String
    , ID: Int
    , taskID: java.util.UUID
    , taskURI: String
    , task: Model.Task
    , phone: String
    , messageText: String
    , status: Option[Model.RequestStatus]
    , messageLogID: Option[Long]) =
    new SmsIptRequest(
      _locator = Some(_locator)
    , _URI = URI
    , _ID = ID
    , _taskID = taskID
    , _taskURI = taskURI
    , _task = task
    , _phone = phone
    , _messageText = messageText
    , _status = status
    , _messageLogID = messageLogID)
}
