package hr.element.beepo.client
package xml

import sms._

import hr.element.etb.Pimps._

trait SmsXMLConverter extends XMLConverter { this: Sms =>
  def toXml =
<SmsIptRequest>
  <phone>{ phones.map{ p =>
    <string>{ p }</string>
  }}</phone>
  <messageText>{ this.body }</messageText>
</SmsIptRequest>
}
