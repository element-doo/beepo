package hr.element.beepo
package Model

import hr.ngs.patterns._

trait ISmsIptRequestRepository
    extends IRepository[Model.SmsIptRequest] with IPersistableRepository[Model.SmsIptRequest]
