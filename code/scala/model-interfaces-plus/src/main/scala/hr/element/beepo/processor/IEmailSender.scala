package hr.element.beepo
package processor


trait IEmailSender {

  def send(email: Model.EmailSmtpRequest): EmailStatus

  def send(email: IndexedSeq[Model.EmailSmtpRequest]): IndexedSeq[EmailStatus]

}