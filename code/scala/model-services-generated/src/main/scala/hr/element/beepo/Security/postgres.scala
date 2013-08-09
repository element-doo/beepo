package hr.element.beepo
package Security

import hr.ngs.patterns._

package object postgres {
  def initialize(container: IContainer) {
    val locator = container[IServiceLocator]

    Security.postgres.RoleConverter.initializeProperties
    container.register[Security.postgres.RoleRepository, Security.IRoleRepository]
    container.register[Security.postgres.RoleRepository, IRepository[Security.Role]]
    Security.postgres.InheritedRoleConverter.initializeProperties
    container.register[Security.postgres.InheritedRoleRepository, Security.IInheritedRoleRepository]
    container.register[Security.postgres.InheritedRoleRepository, IRepository[Security.InheritedRole]]
    Security.postgres.UserConverter.initializeProperties
    container.register[Security.postgres.UserRepository, Security.IUserRepository]
    container.register[Security.postgres.UserRepository, IRepository[Security.User]]
    container.register[Security.postgres.RoleRepository, IPersistableRepository[Security.Role]]
    container.register[Security.postgres.InheritedRoleRepository, IPersistableRepository[Security.InheritedRole]]
    container.register[Security.postgres.UserRepository, IPersistableRepository[Security.User]]
  }
}
