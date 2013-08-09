package hr.element.beepo
package Model

import hr.ngs.patterns._

trait ITaskRepository
    extends IRepository[Model.Task] with IPersistableRepository[Model.Task]
