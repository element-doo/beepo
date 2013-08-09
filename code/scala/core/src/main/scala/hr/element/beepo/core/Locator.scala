package hr.element.beepo
package core

import processor.{ TaskProcessor, ITaskProcessor}


object Locator extends hr.ngs.patterns.ILocator {
    val configPath =
      sys.props("user.home") + "/.config/beepo/db.config"

    SystemConfiguration.initialize(container)
    container.register(classOf[TaskProcessor])


  def register(clazz: Any) =
    container.synchronized
    {
      container.register(clazz)
    }
}

object Repositories
{
  lazy val taskProc = Locator[ITaskProcessor]

}
