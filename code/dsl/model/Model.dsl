module Model
{
  root Task(ID) {
    GUID       ID;
    String?    requestID { unique; }
    XML        payload;
    Timestamp  receivedAt;
  }
}
