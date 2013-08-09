package hr.element.beepo
package core

import net.liftweb._
import http._
import sitemap._

import org.slf4j.LoggerFactory

class Boot extends Bootable with Logger{

  def boot {
    LiftRules.addToPackages("hr.element.beepo.core")

    LiftRules.statelessDispatchTable.append(Api)
    LiftRules.statelessDispatchTable.append(MockRest)
    LiftRules.early.append(_ setCharacterEncoding "UTF-8")

  }
}
