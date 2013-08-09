package hr.element.beepo
package sms

import processor._

object SendSms extends ISmsSender with Logger {

  def apply(sms: Model.SmsIptRequest) = send(sms)

  def send(sms: Model.SmsIptRequest) =
    ResponseParse.sendResponseParse(IPTSender.sendSMS2(sms.phone, sms.messageText))

  def checkStatus(id: String) =
    ResponseParse.checkResponseParse(IPTSender.getReturnCode(id.toLong))
}
