package hr.element.beepo
package Model

import hr.ngs.patterns._

case class RequestStatus(
    queuedAt: org.joda.time.DateTime = org.joda.time.DateTime.now
  , sentAt: Option[org.joda.time.DateTime] = None
  , status: String = ""
  , serverResponse: Option[String] = None
  )
