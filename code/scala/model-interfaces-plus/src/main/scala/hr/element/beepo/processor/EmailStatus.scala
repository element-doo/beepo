package hr.element.beepo.processor

sealed trait EmailStatus

case object Success extends EmailStatus

case class EmailError(e: Throwable) extends EmailStatus