package hr.element.beepo
package Model

trait ISmsIptRequestRepositoryPlus extends ISmsIptRequestRepository {

  def getSmssByTaskID(taskID: String): IndexedSeq[SmsIptRequest]

}