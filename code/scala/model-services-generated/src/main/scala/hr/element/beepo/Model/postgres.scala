package hr.element.beepo
package Model

import hr.ngs.patterns._

package object postgres {
  def initialize(container: IContainer) {
    val locator = container[IServiceLocator]

    Model.postgres.TaskConverter.initializeProperties
    container.register[Model.postgres.TaskRepository, Model.ITaskRepository]
    container.register[Model.postgres.TaskRepository, IRepository[Model.Task]]
    Model.postgres.RequestStatusConverter.initializeProperties
    Model.postgres.EmailSmtpRequestConverter.initializeProperties
    container.register[Model.postgres.EmailSmtpRequestRepository, Model.IEmailSmtpRequestRepository]
    container.register[Model.postgres.EmailSmtpRequestRepository, IRepository[Model.EmailSmtpRequest]]
    Model.postgres.AttachmentConverter.initializeProperties
    Model.postgres.SmsIptRequestConverter.initializeProperties
    container.register[Model.postgres.SmsIptRequestRepository, Model.ISmsIptRequestRepository]
    container.register[Model.postgres.SmsIptRequestRepository, IRepository[Model.SmsIptRequest]]
    Model.postgres.ImSkypeRequestConverter.initializeProperties
    container.register[Model.postgres.ImSkypeRequestRepository, Model.IImSkypeRequestRepository]
    container.register[Model.postgres.ImSkypeRequestRepository, IRepository[Model.ImSkypeRequest]]
    container.register[Model.postgres.TaskRepository, IPersistableRepository[Model.Task]]
    container.register[Model.postgres.EmailSmtpRequestRepository, IPersistableRepository[Model.EmailSmtpRequest]]
    container.register[Model.postgres.SmsIptRequestRepository, IPersistableRepository[Model.SmsIptRequest]]
    container.register[Model.postgres.ImSkypeRequestRepository, IPersistableRepository[Model.ImSkypeRequest]]
  }
}
