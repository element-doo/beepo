package hr.element.beepo
package Model

import hr.ngs.patterns._
import com.fasterxml.jackson.annotation._

class Task private(
    @transient private val _locator: Option[IServiceLocator]
  , private var _URI: String
  , private var _ID: java.util.UUID
  , private var _requestID: Option[String]
  , private var _payload: scala.xml.Elem
  , private var _receivedAt: org.joda.time.DateTime
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
    case c: Task => c.URI == URI
    case _ => false
  }

  override def toString = "Task("+ URI +")"

  @JsonGetter("ID")
  def ID = {

    _ID
  }

  def ID_= (value: java.util.UUID) {
    //TODO missing primitive type distinction require(value ne null)

    _ID = value

  }

  @JsonGetter("requestID")
  def requestID = {

    _requestID
  }

  def requestID_= (value: Option[String]) {
    //TODO missing primitive type distinction require(value ne null)

    _requestID = value

  }

  @JsonGetter("payload")
  def payload = {

    _payload
  }

  def payload_= (value: scala.xml.Elem) {
    //TODO missing primitive type distinction require(value ne null)

    _payload = value

  }

  @JsonGetter("receivedAt")
  def receivedAt = {

    _receivedAt
  }

  def receivedAt_= (value: org.joda.time.DateTime) {
    //TODO missing primitive type distinction require(value ne null)

    _receivedAt = value

  }

  @JsonCreator private def this(
    @JacksonInject("locator") _locator: IServiceLocator
  , @JsonProperty("URI") URI: String
  , @JsonProperty("ID") ID: java.util.UUID
  , @JsonProperty("requestID") requestID: Option[String]
  , @JsonProperty("payload") payload: scala.xml.Elem
  , @JsonProperty("receivedAt") receivedAt: org.joda.time.DateTime
  ) =
    this(_locator = Some(_locator), _URI = URI, _ID = ID, _requestID = requestID, _payload = payload, _receivedAt = receivedAt)

}

object Task {

  def apply(
    ID: java.util.UUID = java.util.UUID.randomUUID
  , requestID: Option[String] = None
  , payload: scala.xml.Elem = null
  , receivedAt: org.joda.time.DateTime = org.joda.time.DateTime.now
  ) = {
    new Task(
      _locator = None
    , _URI = java.util.UUID.randomUUID.toString
    , _ID = ID
    , _requestID = requestID
    , _payload = payload
    , _receivedAt = receivedAt)
  }

  private[Model] def buildInternal(_locator: IServiceLocator
    , URI: String
    , ID: java.util.UUID
    , requestID: Option[String]
    , payload: scala.xml.Elem
    , receivedAt: org.joda.time.DateTime) =
    new Task(
      _locator = Some(_locator)
    , _URI = URI
    , _ID = ID
    , _requestID = requestID
    , _payload = payload
    , _receivedAt = receivedAt)
}
