package hr.element.beepo

import hr.ngs.patterns._

object SystemConfiguration {
  def initialize(container: IContainer) {
    postgresUtils.loadIndexes(container[org.pgscala.PGSessionFactory])
    Model.postgres.initialize(container)
    Security.postgres.initialize(container)
  }
}
