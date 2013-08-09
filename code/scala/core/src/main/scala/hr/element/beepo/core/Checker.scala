//package hr.element.beepo
//package core
//
//import net.liftweb.actor.LiftActor
//import send.sms.SMSSender
//import send.sms.Received
//import org.joda.time.DateTime
//
//
//sealed trait CheckerMsg
//case class Check(sms: Model.SmsIptRequest) extends CheckerMsg
//case class ReSend(id: Int) extends CheckerMsg
//object Checker extends LiftActor{
//
//  def messageHandler = {
//    case Check(sms) =>
//      val time = DateTime.now()
//      for (id <- sms.messageLogID) {
//          SMSSender.getSMSReturnCode(id) match {
//            case Received =>
//              sms.status = sms.status match {
//                case Some(st) =>
//                  Some(st.copy(sentAt = Some(time), status = "Success"))
//                case None =>
//                  Some( Model.RequestStatus(
//                      time
//                    , Some(time)
//                    , "Success"
//                    , None
//                    ))
//                    }
//            case _ =>
//              sms.status = sms.status match {
//                case Some(st) =>
//                  Some(st.copy(sentAt = Some(time), status = "Success"))
//                case None =>
//                  Some( Model.RequestStatus(
//                      time
//                    , Some(time)
//                    , "Success"
//                    , None
//                    ))
//              }
//              }
//          }
//      Repositories.smsRepository.update(sms)
//      }
//}