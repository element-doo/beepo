package hr.element.beepo

import org.slf4j.LoggerFactory

trait Logger {
  lazy val logger = LoggerFactory getLogger getClass
}