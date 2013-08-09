package hr.element.beepo
package Security

import hr.ngs.patterns._

trait IInheritedRoleRepository
    extends IRepository[Security.InheritedRole] with IPersistableRepository[Security.InheritedRole]
