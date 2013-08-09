package hr.element.beepo
package Model
package postgres

import org.pgscala.PGSessionFactory
import hr.ngs.patterns.IServiceLocator

class SmsIptRequestRepositoryPlus(
    sessionFactory: PGSessionFactory
  , locator : IServiceLocator
  ) extends SmsIptRequestRepository(sessionFactory, locator)
  with ISmsIptRequestRepositoryPlus {

  def getSmssByTaskID(taskID: String): IndexedSeq[SmsIptRequest] =
    sessionFactory.using( _.arr("""
      SELECT s
      FROM "Model"."SmsIptRequest_entity" s
      WHERE s."taskID" :: text = $1;
      """, taskID ) (createFromResultSet)
      )
}