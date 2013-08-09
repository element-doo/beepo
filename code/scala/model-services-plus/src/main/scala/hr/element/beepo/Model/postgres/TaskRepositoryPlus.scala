package hr.element.beepo
package Model
package postgres

import org.pgscala._
import org.pgscala.util.PGArray
import java.util.UUID
import hr.ngs.patterns.IServiceLocator

class TaskRepositoryPlus(
      sessionFactory: PGSessionFactory
    , locator: IServiceLocator
    ) extends TaskRepository(sessionFactory, locator)
    with ITaskRepositoryPlus {

    def findByRequestIDorURI(id: String): Option[Task] =
      sessionFactory.using( _.row("""
        SELECT t
        FROM "Model"."Task_entity" t
        WHERE t."requestID" = $1 OR t."URI" = $1;
""", id ) (createFromResultSet)
      )

    def findByRequestIDorURI(
        uris: IndexedSeq[String]
      ): IndexedSeq[Task] =
    if (uris == null || uris.isEmpty) {
      IndexedSeq.empty
    } else {
      sessionFactory.using( _.arr("""
                SELECT r
        FROM "Model"."Task_entity" r
        WHERE r."URI" = ANY(@1) OR r."requestID" = ANY(@1);
        """, uris){createFromResultSet})
    }
}
