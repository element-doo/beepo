package hr.element.beepo
package Security

import hr.ngs.patterns._

trait IRoleRepository
    extends IRepository[Security.Role] with IPersistableRepository[Security.Role]
