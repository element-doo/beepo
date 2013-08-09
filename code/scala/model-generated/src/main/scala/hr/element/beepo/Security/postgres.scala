package hr.element.beepo
package Security

import hr.ngs.patterns._

package object postgres {
  def initialize(container: IContainer) {
    val locator = container[IServiceLocator]

    Security.postgres.UserConverter.initializeProperties
    container.register[Security.postgres.UserRepository, Security.IUserRepository]
    container.register[Security.postgres.UserRepository, IRepository[Security.User]]
    container.register[Security.postgres.UserRepository, IPersistableRepository[Security.User]]
  }
}
