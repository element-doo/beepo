package hr.element.beepo
package Security

import hr.ngs.patterns._
import com.fasterxml.jackson.annotation._

class InheritedRole private(
    @transient private val _locator: Option[IServiceLocator]
  , private var _URI: String
  , private var _Name: String
  , private var _ParentName: String
  , private var _RoleURI: String
  , @transient private var _Role: Security.Role
  , private var _ParentRoleURI: String
  , @transient private var _ParentRole: Security.Role
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
    case c: InheritedRole => c.URI == URI
    case _ => false
  }

  override def toString = "InheritedRole("+ URI +")"

  @JsonGetter("Name")
  def Name = {

    _Name
  }

  protected[beepo]def Name_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _Name = value

  }

  @JsonGetter("ParentName")
  def ParentName = {

    _ParentName
  }

  protected[beepo]def ParentName_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _ParentName = value

  }

  @JsonGetter("RoleURI")
  def RoleURI = {

    _RoleURI
  }

  private[beepo]def RoleURI_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _RoleURI = value

  }

  def Role = {

    if(_locator.isDefined) {
      if (_Role == null || _Role.URI != RoleURI)
        _Role = _locator.get[Security.IRoleRepository].find(RoleURI).getOrElse(null)
    }
    _Role
  }

  def Role_= (value: Security.Role) {
    //TODO missing primitive type distinction require(value ne null)

    _Role = value

        if(Name != value.Name)
          Name = value.Name
    _RoleURI = value.URI
  }

  @JsonGetter("ParentRoleURI")
  def ParentRoleURI = {

    _ParentRoleURI
  }

  private[beepo]def ParentRoleURI_= (value: String) {
    //TODO missing primitive type distinction require(value ne null)

    _ParentRoleURI = value

  }

  def ParentRole = {

    if(_locator.isDefined) {
      if (_ParentRole == null || _ParentRole.URI != ParentRoleURI)
        _ParentRole = _locator.get[Security.IRoleRepository].find(ParentRoleURI).getOrElse(null)
    }
    _ParentRole
  }

  def ParentRole_= (value: Security.Role) {
    //TODO missing primitive type distinction require(value ne null)

    _ParentRole = value

        if(ParentName != value.Name)
          ParentName = value.Name
    _ParentRoleURI = value.URI
  }

  @JsonCreator private def this(
    @JacksonInject("locator") _locator: IServiceLocator
  , @JsonProperty("URI") URI: String
  , @JsonProperty("Name") Name: String
  , @JsonProperty("ParentName") ParentName: String
  , @JsonProperty("RoleURI") RoleURI: String
  , @JsonProperty("ParentRoleURI") ParentRoleURI: String
  ) =
    this(_locator = Some(_locator), _URI = URI, _Name = Name, _ParentName = ParentName, _RoleURI = RoleURI, _Role = null, _ParentRoleURI = ParentRoleURI, _ParentRole = null)

}

object InheritedRole {

  def apply(
    Role: Security.Role
  , ParentRole: Security.Role
  ) = {
    if (Role == null) throw new IllegalArgumentException("""Argument "Role" cannot be null""")
    if (ParentRole == null) throw new IllegalArgumentException("""Argument "ParentRole" cannot be null""")
    new InheritedRole(
      _locator = None
    , _URI = java.util.UUID.randomUUID.toString
    , _Name = Role.Name
    , _ParentName = ParentRole.Name
    , _RoleURI = Role.URI
    , _Role = Role
    , _ParentRoleURI = ParentRole.URI
    , _ParentRole = ParentRole)
  }

  private[Security] def buildInternal(_locator: IServiceLocator
    , URI: String
    , Name: String
    , ParentName: String
    , RoleURI: String
    , Role: Security.Role
    , ParentRoleURI: String
    , ParentRole: Security.Role) =
    new InheritedRole(
      _locator = Some(_locator)
    , _URI = URI
    , _Name = Name
    , _ParentName = ParentName
    , _RoleURI = RoleURI
    , _Role = Role
    , _ParentRoleURI = ParentRoleURI
    , _ParentRole = ParentRole)
}
