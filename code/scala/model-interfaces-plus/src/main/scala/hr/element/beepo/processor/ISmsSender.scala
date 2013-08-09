package hr.element.beepo
package processor


trait ISmsSender {

  def send(sms: Model.SmsIptRequest): SmsStatus

  def checkStatus(messageId: String): SmsStatus

}