package hr.element.beepo
package Security
package postgres

import org.pgscala._
import hr.ngs.patterns.IServiceLocator

class InheritedRoleRepositoryPlus(
      sessionFactory: PGSessionFactory
    , locator: IServiceLocator
    ) extends InheritedRoleRepository(sessionFactory, locator)
    with IInheritedRoleRepositoryPlus {

  def findRoles(uri: String): IndexedSeq[InheritedRole] =
    sessionFactory.using( _.arr("""
        SELECT r
        FROM "Security"."InheritedRole" r
        WHERE r."Name" = @1;
""", uri) (createFromResultSet)
    )
  }
