package hr.element.beepo.misc

trait IEmailCredentials {
  val smtpHost: String
  val smtpPort: String
  val username: String
  val password: String
}