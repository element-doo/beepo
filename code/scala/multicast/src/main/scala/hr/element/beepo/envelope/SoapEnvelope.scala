package hr.element.beepo
package envelope

  object SendSMS2{
    def apply(receiver: String, messageText: String, sid: String, pass: String) = {
      <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
        <soap12:Body>
        <SendSms2 xmlns="http://tempuri.org/">
          <messageText>{messageText}</messageText>
          <userPhone>{receiver}</userPhone>
          <partnerServiceID>{sid}</partnerServiceID>
          <password>{pass}</password>
        </SendSms2>
      </soap12:Body>
    </soap12:Envelope> toString
      }
  }
/*
 * Response example:
 *
 * <?xml version="1.0" encoding="utf-8"?>
 * <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
 * <soap:Body>
 * <SendSms2Response xmlns="http://tempuri.org/">
 * <SendSms2Result>56129344</SendSms3Result>
 * </SendSms2Response>
 * </soap:Body>
 * </soap:Envelope>
 */

  object UserSendSMS {
    def apply(receiver: String, messageText: String, sid: String, pass: String) = {
      <?xml version="1.0" encoding="utf-8"?>
      <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
        <soap12:Body>
        <UserSendSMS xmlns="http://tempuri.org/">
          <ServiceID>{sid}</ServiceID>
          <Password>{pass}</Password>
          <MSISDN>{receiver}</MSISDN>
          <MessageText>{messageText}</MessageText>
        </UserSendSMS>
        </soap12:Body>
     </soap12:Envelope> toString
    }
  }

  object CheckMessage{
    def apply(messageId: Long, sid: String, pass: String)  = {
    <?xml version="1.0" encoding="utf-8"?>
    <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
      <soap12:Body>
        <CheckMessage xmlns="http://tempuri.org/">
          <MessageLogID>{messageId}</MessageLogID>
          <intPartnerID>{sid}</intPartnerID>
          <Pass>{pass}</Pass>
          </CheckMessage>
      </soap12:Body>
    </soap12:Envelope> toString
    }
/*
 * Response example:
 *
 * <?xml version="1.0" encoding="utf-8"?><soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope"
 * xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
 * <soap:Body>
 * <CheckMessageResponse xmlns="http://tempuri.org/">
 * <CheckMessageResult>1</CheckMessageResult>
 * </CheckMessageResponse></soap:Body></soap:Envelope>
 */

  }
