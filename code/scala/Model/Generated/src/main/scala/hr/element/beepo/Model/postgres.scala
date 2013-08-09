package hr.element.beepo
package Model

import hr.ngs.patterns._

package object postgres {
  def initialize(container: IContainer) {
    val locator = container[IServiceLocator]

    Model.postgres.TaskConverter.initializeProperties
    container.register[Model.postgres.TaskRepository, Model.ITaskRepository]
    container.register[Model.postgres.TaskRepository, IRepository[Model.Task]]
    container.register[Model.postgres.TaskRepository, IPersistableRepository[Model.Task]]
  }
}
