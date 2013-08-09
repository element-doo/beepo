package hr.element.beepo
package Security
package postgres

import org.pgscala._
import hr.ngs.patterns.IServiceLocator

class RoleRepositoryPlus(
    sessionFactory: PGSessionFactory
  , inheritedRoleRepository: IInheritedRoleRepositoryPlus
  , locator: IServiceLocator
  ) extends RoleRepository(sessionFactory, locator)
  with IRoleRepositoryPlus {


  def registerRole(name: String): Role = {
    val role = Role(name)
    insert(role)
    role
  }

  def assignRole(name: String, role: String) {
    (find(name), find(role)) match {
      case (Some(nameRole), Some(parentRole)) =>
        inheritedRoleRepository.insert(
          InheritedRole(nameRole, parentRole)
        )

      case _ =>
        sys.error("Role could not be assigned, source and/or target role could not be found!")
    }
  }

  def deleteRole(name: String) {
    find(name) match {
      case Some(role) =>
        val inheritedRoles = inheritedRoleRepository.find(role.Name)
        inheritedRoleRepository.delete(inheritedRoles)

        delete(role)

      case _ =>
        sys.error("Role could not be deleted, because it could not be found!")
    }
  }
}
