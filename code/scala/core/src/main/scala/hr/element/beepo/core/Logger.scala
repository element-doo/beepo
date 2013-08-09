package hr.element.beepo.core

trait Logger {
  lazy val logger = org.slf4j.LoggerFactory getLogger getClass
}