package hr.element.beepo
package Model

import hr.ngs.patterns._

trait IEmailSmtpRequestRepository
    extends IRepository[Model.EmailSmtpRequest] with IPersistableRepository[Model.EmailSmtpRequest] {

  def findByTaskID(taskID: java.util.UUID): IndexedSeq[Model.EmailSmtpRequest]
}
