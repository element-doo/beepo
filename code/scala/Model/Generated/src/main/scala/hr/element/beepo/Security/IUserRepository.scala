package hr.element.beepo
package Security

import hr.ngs.patterns._

trait IUserRepository
    extends IRepository[Security.User] with IPersistableRepository[Security.User]
