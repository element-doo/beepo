package hr.element.beepo

import Model.postgres._
import Security.postgres._
import hr.element.beepo.misc.IIptCredentials
import hr.element.beepo.misc.IEmailCredentials


object Locator extends hr.ngs.patterns.ILocator {
  val configPath =
    sys.props("user.home") + "/.config/beepo/db.config"

  SystemConfiguration.initialize(container)
  container.register(classOf[EmailSmtpRequestRepositoryPlus])
  container.register(classOf[SmsIptRequestRepositoryPlus])
  container.register(classOf[TaskRepositoryPlus])
  container.register(classOf[InheritedRoleRepositoryPlus])
  container.register(classOf[RoleRepositoryPlus])
  container.register(classOf[UserRepositoryPlus])
  container.register(classOf[IptCredentials])
  container.register(classOf[Uris])
  container.register(classOf[EmailCredentials])
  container.register(transporter.Transporter)

  def register(clazz: Any) =
    container.synchronized
    {
      container.register(clazz)
    }

  class IptCredentials extends IIptCredentials{
    lazy val serviceId = "12"+ config("sms-serviceid") //Fix for real
    lazy val password  = config("sms-password")
    lazy val sender    = config("sms-sender")
  }

  class EmailCredentials extends IEmailCredentials{
    lazy val smtpHost = config("email-smtpHost")
    lazy val smtpPort = config("email-smtpPort")
    lazy val username = config("email-username")
    lazy val password = config("email-password")
  }
}

object Repositories {
  lazy val userRepository  = Locator[Security.IUserRepositoryPlus]
  lazy val taskRepository  = Locator[Model.ITaskRepositoryPlus]
  lazy val emailRepository = Locator[Model.IEmailSmtpRequestRepositoryPlus]
  lazy val smsRepository   = Locator[Model.ISmsIptRequestRepositoryPlus]
  def transport             = Locator[misc.ITransporter]
  def uris                  = Locator[misc.IUris]
  def iptCred               = Locator[misc.IIptCredentials]
  def emailCreds            = Locator[misc.IEmailCredentials]
}

class Uris extends misc.IUris {
  lazy val iptHost = "smsgw.ipt.hr"
  lazy val iptPort = 8080
}

