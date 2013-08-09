package hr.element.beepo
package Security.postgres

import hr.ngs.patterns._

import org.pgscala.converters._
import org.pgscala.util._
import hr.ngs.patterns._

object UserConverter {

  private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  def fromPGString(record: String, locator: IServiceLocator): Security.User = {
    val items = PGRecord.unpack(record)
    Security.User.buildInternal(
      _locator = locator
    , URI = items(URIPos)
    , name = items(namePos)
    , password = items(passwordPos)
    )
  }

  def fromPGStringExtended(record: String, locator: IServiceLocator): Security.User = {
    val items = PGRecord.unpack(record)
    Security.User.buildInternal(
      _locator = locator
    , URI = items(URIPosExtended)
    , name = items(namePosExtended)
    , password = items(passwordPosExtended)
    )
  }

  def toPGString(item: Security.User): String = {
    val items = new Array[String](columnCount)
    items(URIPos) = item.URI
    items(namePos) = item.name
    items(passwordPos) = item.password
    PGRecord.pack(items)
  }

  def toPGStringExtended(item: Security.User): String = {
    val items = new Array[String](extendedColumnCount)
    items(URIPosExtended) = item.URI
    items(namePosExtended) = item.name
    items(passwordPosExtended) = item.password
    PGRecord.pack(items)
  }

  private var columnCount = -1
  private var extendedColumnCount = -1

  def initializeProperties() {

    postgresUtils.getIndexes("Security", "User_entity").get("URI") match {
      case Some(index) => URIPos = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Security.User_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "-ngs_User_type-").get("URI") match {
      case Some(index) => URIPosExtended = index - 1
      case None => logger.error("""Couldn't find column "URI" in type Security.User. Check if database is out of sync with code!""")
    }
    columnCount = postgresUtils.getColumnCount("Security", "User_entity")
    extendedColumnCount = postgresUtils.getColumnCount("Security", "-ngs_User_type-")
    postgresUtils.getIndexes("Security", "User_entity").get("name") match {
      case Some(index) => namePos = index - 1
      case None => logger.error("""Couldn't find column "name" in type Security.User_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "-ngs_User_type-").get("name") match {
      case Some(index) => namePosExtended = index - 1
      case None => logger.error("""Couldn't find column "name" in type Security.User. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "User_entity").get("password") match {
      case Some(index) => passwordPos = index - 1
      case None => logger.error("""Couldn't find column "password" in type Security.User_entity. Check if database is out of sync with code!""")
    }
    postgresUtils.getIndexes("Security", "-ngs_User_type-").get("password") match {
      case Some(index) => passwordPosExtended = index - 1
      case None => logger.error("""Couldn't find column "password" in type Security.User. Check if database is out of sync with code!""")
    }
  }

  private var URIPos = -1
  private var URIPosExtended = -1

  def buildURI(name: String) : String = {
    val _uriParts = new Array[String](1)
    _uriParts(0) = name
    postgres.Utils.buildURI(_uriParts)
  }
  private var namePos = -1
  private var namePosExtended = -1
  private var passwordPos = -1
  private var passwordPosExtended = -1
}
