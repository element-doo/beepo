package hr.element.beepo
package Security.postgres

import hr.ngs.patterns._

import org.pgscala.converters._
import org.pgscala.util._
import hr.ngs.patterns._

object InheritedRoleConverter {

  private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  def fromPGString(record: String, locator: IServiceLocator): Security.InheritedRole = {
    val items = PGRecord.unpack(record)
    Security.InheritedRole.buildInternal(
      _locator = locator
    , URI = items(URIPos)
    , Name = items(NamePos)
    , ParentName = items(ParentNamePos)
    , RoleURI = items(RoleURIPos)
    , Role = null
    , ParentRoleURI = items(ParentRoleURIPos)
    , ParentRole = null
    )
  }

  def fromPGStringExtended(record: String, locator: IServiceLocator): Security.InheritedRole = {
    val items = PGRecord.unpack(record)
    Security.InheritedRole.buildInternal(
      _locator = locator
    , URI = items(URIPosExtended)
    , Name = items(NamePosExtended)
    , ParentName = items(ParentNamePosExtended)
    , RoleURI = items(RoleURIPosExtended)
    , Role = null
    , ParentRoleURI = items(ParentRoleURIPosExtended)
    , ParentRole = null
    )
  }

  def toPGString(item: Security.InheritedRole): String = {
    val items = new Array[String](columnCount)
    items(URIPos) = item.URI
    items(NamePos) = item.Name
    items(ParentNamePos) = item.ParentName
    items(RoleURIPos) = item.RoleURI
    items(ParentRoleURIPos) = item.ParentRoleURI
    PGRecord.pack(items)
  }

  def toPGStringExtended(item: Security.InheritedRole): String = {
    val items = new Array[String](extendedColumnCount)
    items(URIPosExtended) = item.URI
    items(NamePosExtended) = item.Name
    items(ParentNamePosExtended) = item.ParentName
    items(RoleURIPosExtended) = item.RoleURI
    items(ParentRoleURIPosExtended) = item.ParentRoleURI
    PGRecord.pack(items)
  }

  private var columnCount = -1
  private var extendedColumnCount = -1

  def initializeProperties() {

    postgresUtils.getIndexes("Security", "InheritedRole_entity").get("URI") match {
      case Some(index) => URIPos = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Security.InheritedRole_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "-ngs_InheritedRole_type-").get("URI") match {
      case Some(index) => URIPosExtended = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Security.InheritedRole. Check if database is out of sync with code!""")
    }
    columnCount = postgresUtils.getColumnCount("Security", "InheritedRole_entity")
    extendedColumnCount = postgresUtils.getColumnCount("Security", "-ngs_InheritedRole_type-")
    postgresUtils.getIndexes("Security", "InheritedRole_entity").get("Name") match {
      case Some(index) => NamePos = index - 1
      case None => logger.error("""Couldn't find column "Name" in type Security.InheritedRole_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "-ngs_InheritedRole_type-").get("Name") match {
      case Some(index) => NamePosExtended = index - 1
      case None => logger.error("""Couldn't find column "Name" in type Security.InheritedRole. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "InheritedRole_entity").get("ParentName") match {
      case Some(index) => ParentNamePos = index - 1
      case None => logger.error("""Couldn't find column "ParentName" in type Security.InheritedRole_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "-ngs_InheritedRole_type-").get("ParentName") match {
      case Some(index) => ParentNamePosExtended = index - 1
      case None => logger.error("""Couldn't find column "ParentName" in type Security.InheritedRole. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "InheritedRole_entity").get("RoleURI") match {
      case Some(index) => RoleURIPos = index - 1
      case None => logger.error("""Couldn't find column "RoleURI" in type Security.InheritedRole_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "-ngs_InheritedRole_type-").get("RoleURI") match {
      case Some(index) => RoleURIPosExtended = index - 1
      case None => logger.error("""Couldn't find column "RoleURI" in type Security.InheritedRole. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "InheritedRole_entity").get("ParentRoleURI") match {
      case Some(index) => ParentRoleURIPos = index - 1
      case None => logger.error("""Couldn't find column "ParentRoleURI" in type Security.InheritedRole_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "-ngs_InheritedRole_type-").get("ParentRoleURI") match {
      case Some(index) => ParentRoleURIPosExtended = index - 1
      case None => logger.error("""Couldn't find column "ParentRoleURI" in type Security.InheritedRole. Check if database is out of sync with code!""")
    }
  }

  private var URIPos = -1
  private var URIPosExtended = -1

  def buildURI(Name: String, ParentName: String) : String = {
    val _uriParts = new Array[String](2)
    _uriParts(0) = Name
    _uriParts(1) = ParentName
    postgres.Utils.buildURI(_uriParts)
  }
  private var NamePos = -1
  private var NamePosExtended = -1
  private var ParentNamePos = -1
  private var ParentNamePosExtended = -1
  private var RoleURIPos = -1
  private var RoleURIPosExtended = -1
  private var ParentRoleURIPos = -1
  private var ParentRoleURIPosExtended = -1
}
