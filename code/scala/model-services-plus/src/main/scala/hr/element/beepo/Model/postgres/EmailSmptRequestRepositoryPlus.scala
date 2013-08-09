package hr.element.beepo
package Model
package postgres


import org.pgscala._
import hr.ngs.patterns.IServiceLocator

class EmailSmtpRequestRepositoryPlus(
    sessionFactory: PGSessionFactory
  , locator: IServiceLocator
  ) extends EmailSmtpRequestRepository(sessionFactory, locator)
  with IEmailSmtpRequestRepositoryPlus {

  def getEmailsByTaskId(taskID: String): IndexedSeq[EmailSmtpRequest] =
    sessionFactory.using( _.arr("""
      SELECT e
      FROM "Model"."EmailSmtpRequest_entity" e
      WHERE e."taskID" :: text = $1;
      """, taskID ) (createFromResultSet)
      )
}
