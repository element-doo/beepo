package hr.element.beepo

import scala.xml._
import hr.ngs.patterns._
import org.pgscala.PGPool
import org.slf4j._

object Locator extends ILocator {
  val configHome = sys.props("user.home") / ".config" / "beepo"
  override val configPath = configHome + "/main.config"

  SystemConfigurationPlus initialize container
}
