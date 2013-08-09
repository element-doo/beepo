package hr.element.beepo
package Security

import hr.ngs.patterns._
import com.fasterxml.jackson.annotation._

class Role private(
    @transient private val _locator: Option[IServiceLocator]
  , private var _URI: String
  , private var _Name: String
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
    case c: Role => c.URI == URI
    case _ => false
  }

  override def toString = "Role("+ URI +")"

  @JsonGetter("Name")
  def Name = {

    _Name
  }

  def Name_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _Name = value

  }

  @JsonCreator private def this(
    @JacksonInject("locator") _locator: IServiceLocator
  , @JsonProperty("URI") URI: String
  , @JsonProperty("Name") Name: String
  ) =
    this(_locator = Some(_locator), _URI = URI, _Name = Name)

}

object Role {

  def apply(
    Name: String = ""
  ) = {
    new Role(
      _locator = None
    , _URI = java.util.UUID.randomUUID.toString
    , _Name = Name)
  }

  private[Security] def buildInternal(_locator: IServiceLocator
    , URI: String
    , Name: String) =
    new Role(
      _locator = Some(_locator)
    , _URI = URI
    , _Name = Name)
}
