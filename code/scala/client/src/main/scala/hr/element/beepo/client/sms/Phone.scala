package hr.element.beepo.client
package sms

object Phone {
  private val CountryCode = """(?:\+|00)(.*)"""r
  private val NonDigits = """[^\d]+"""r

  private def parse(number: String, trim: Boolean = true) =
    number match {
      case CountryCode(body) =>
        (if (trim) {
          NonDigits.replaceAllIn(body, "")
        }
        else {
          body
        })
      case _ =>
        sys.error("Invalid phone number, must not start with 00 or +")
  }
}

case class Phone(number: String) {
  val normalized = Phone.parse(number)
  override val toString = normalized
}
