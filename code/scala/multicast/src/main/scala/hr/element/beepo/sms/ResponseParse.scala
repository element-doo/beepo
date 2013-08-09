package hr.element.beepo
package sms

import scala.xml._
import hr.element.beepo.processor._

object ResponseParse {

  def sendResponseParse(x: Either[Exception, Elem]): SendStatus =
    x match {
    case Left(x) =>
      SmsError(0, x.toString)
    case Right(elem) =>
      elem \ "Body" \ "SendSms2Response" \ "SendSms2Result" text match {
        case AsLong(x) if x > 0  =>
          Sent(x)
        case AsInt(x) if sendMisc.isDefinedAt(x) =>
          SmsError(x, sendMisc(x))
        case _ => SmsError(0, "Unknown error: %s" format elem toString)
      }
  }

  def checkResponseParse(x: Either[Exception, Elem]): CheckStatus =
    x match {
      case Left(x) => NotReceived(4, x.toString)
      case Right(xml) =>
        xml \ "CheckMessageResponse" \ "CheckMessageResult" text match {
          case AsLong(x) =>
            x match {
              case 0 => Received
              case AsInt(x) if checkMisc.isDefinedAt(x) =>
                NotReceived(x, checkMisc(x))
              case _ => NotReceived(4, "Unknown error: %s" format xml toString)
            }
          case _ => NotReceived(4, "Unknown error: %s" format xml toString)
        }
  }

  private def sendMisc: PartialFunction[Int, String] =
  {
    case -1 => "Nepoznata greška"
    case -2 => "MessageText ili userPhone parametar nedostaje"
    case -3 => "Pogrešna zaporka partnera"
    case -4 => "userPhone parametar je prekratak"
    case -5 => "userPhone parametar je predug"
    case -6 => "userPhone parametar nije ispravnog formata"
    case -7 => "Previše poslanih SMS poruka u prethodnom periodu. Period o" +
        " dozvoljeni broj SMS poruka se postavlja za svaki servis partnera " +
        "individualno"
    case -8 => "Pogrešan partnerServiceID"
    case -9 => "SMS poruka nije pohranjena zbog interne greške"
    case -10 => "Neispravan partnerServiceID ili nije aktivan"
    case -11 => "Specifična interna greška"
    case -12 => "Specifična interna greška"
    case -14 => "Specifična interna greška (Pohrana SMS poruke nije moguća)"
    case -15 => "SMS poruka je predugačka"
    case -16 => "Pogrešan partnerServiceID"
    case -999 => "Echo / Ping"
  }

  private def checkMisc: PartialFunction[Int, String] =
  {
    case -4 => "Status poruke nije poznat"
    case -3 => "Pogrešan intPartnerID ili je pogrešna zaporka"
    case -2 => "Poruka ne postoji u IPT sustavu"
    case  1 => "Poruka je poslana i dostavljena"
    case  2 => "Poruka je poslana ali je istekla (nijedostavljena)"
    case  3 => "Poruka još nije obrađena ili nije još dostavljena"
  }
}


object AsInt{
  def unapply(s: Any) =
    s match {
    case i: Long => Some(i.toInt)
    case str: String => Some(str.toInt)
    case _ => None
  }
}

object AsLong{

  def unapply(s: String) =
    try{
      Some(s.toLong)
    } catch {
      case _ => None
    }
}

