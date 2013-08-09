package hr.element.beepo
package Security.postgres

import hr.ngs.patterns._

import org.pgscala.converters._
import org.pgscala.util._
import hr.ngs.patterns._

object RoleConverter {

  private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  def fromPGString(record: String, locator: IServiceLocator): Security.Role = {
    val items = PGRecord.unpack(record)
    Security.Role.buildInternal(
      _locator = locator
    , URI = items(URIPos)
    , Name = items(NamePos)
    )
  }

  def fromPGStringExtended(record: String, locator: IServiceLocator): Security.Role = {
    val items = PGRecord.unpack(record)
    Security.Role.buildInternal(
      _locator = locator
    , URI = items(URIPosExtended)
    , Name = items(NamePosExtended)
    )
  }

  def toPGString(item: Security.Role): String = {
    val items = new Array[String](columnCount)
    items(URIPos) = item.URI
    items(NamePos) = item.Name
    PGRecord.pack(items)
  }

  def toPGStringExtended(item: Security.Role): String = {
    val items = new Array[String](extendedColumnCount)
    items(URIPosExtended) = item.URI
    items(NamePosExtended) = item.Name
    PGRecord.pack(items)
  }

  private var columnCount = -1
  private var extendedColumnCount = -1

  def initializeProperties() {

    postgresUtils.getIndexes("Security", "Role_entity").get("URI") match {
      case Some(index) => URIPos = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Security.Role_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "-ngs_Role_type-").get("URI") match {
      case Some(index) => URIPosExtended = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Security.Role. Check if database is out of sync with code!""")
    }
    columnCount = postgresUtils.getColumnCount("Security", "Role_entity")
    extendedColumnCount = postgresUtils.getColumnCount("Security", "-ngs_Role_type-")
    postgresUtils.getIndexes("Security", "Role_entity").get("Name") match {
      case Some(index) => NamePos = index - 1
      case None => logger.error("""Couldn't find column "Name" in type Security.Role_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "-ngs_Role_type-").get("Name") match {
      case Some(index) => NamePosExtended = index - 1
      case None => logger.error("""Couldn't find column "Name" in type Security.Role. Check if database is out of sync with code!""")
    }
  }

  private var URIPos = -1
  private var URIPosExtended = -1

  def buildURI(Name: String) : String = {
    val _uriParts = new Array[String](1)
    _uriParts(0) = Name
    postgres.Utils.buildURI(_uriParts)
  }
  private var NamePos = -1
  private var NamePosExtended = -1
}
