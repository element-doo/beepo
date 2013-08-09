package hr.element.beepo
package Model

trait IEmailSmtpRequestRepositoryPlus extends IEmailSmtpRequestRepository {
  def getEmailsByTaskId(taskID: String): IndexedSeq[EmailSmtpRequest]
}
