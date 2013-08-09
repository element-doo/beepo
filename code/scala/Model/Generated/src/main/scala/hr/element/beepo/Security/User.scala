package hr.element.beepo
package Security

import hr.ngs.patterns._
import com.fasterxml.jackson.annotation._

class User private(
    @transient private val _locator: Option[IServiceLocator]
  , private var _URI: String
  , private var _name: String
  , private var _password: String
  ) extends IIdentifiable
    with Serializable {

  @JsonGetter("URI")
  def URI = {

    _URI
  }

  private[Security]def URI_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _URI = value

  }

  override def hashCode = URI.hashCode
  override def equals(o: Any) = o match {
    case c: User => c.URI == URI
    case _ => false
  }

  override def toString = "User("+ URI +")"

  @JsonGetter("name")
  def name = {

    _name
  }

  def name_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _name = value

  }

  @JsonGetter("password")
  def password = {

    _password
  }

  def password_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _password = value

  }

  @JsonCreator private def this(
    @JacksonInject("locator") _locator: IServiceLocator
  , @JsonProperty("URI") URI: String
  , @JsonProperty("name") name: String
  , @JsonProperty("password") password: String
  ) =
    this(_locator = Some(_locator), _URI = URI, _name = name, _password = password)

}

object User {

  def apply(
    name: String = ""
  , password: String = ""
  ) = {
    new User(
      _locator = None
    , _URI = java.util.UUID.randomUUID.toString
    , _name = name
    , _password = password)
  }

  private[Security] def buildInternal(_locator: IServiceLocator
    , URI: String
    , name: String
    , password: String) =
    new User(
      _locator = Some(_locator)
    , _URI = URI
    , _name = name
    , _password = password)
}
