module Model
{
  root Task(ID)
  {
    GUID    ID;
    String? requestID { unique; }
    XML payload;
    Timestamp createdAt;
    // Status?
/*
    specification findByRequestIDorURI 'it => it.requestID == id || it.URI == id'
    {
      String id;
    }*/
  }

  value RequestStatus
  {
    Timestamp queuedAt;
    Timestamp? sentAt;
    String  status;
    String? serverResponse;
  //  Int     retryCount;
  // Int     maxRetries;   // e mail = 5, rest = 1
  //  String[] errors;
  }

  root EmailSmtpRequest
  {
    Task *task;
    String from;
    String[] to;
    String[] replyTo;
    String[] cc;
    String[] bcc;
    String subject;
    String? textBody;
    String? htmlBody;
    Model.Attachment[] attachments;
    Model.RequestStatus? status;

    specification findByTaskID 'it => it.taskID == taskID'
    {
      UUID taskID;
    }
  }

  value Attachment{
    String fileName;
    String mimeType;
    Binary bytes;
  }

  root SmsIptRequest
  {
    Task *task;
    String phone;
    String messageText;
    Model.RequestStatus? status;
    Long? messageLogID; //unique sms identifier of ipt system that is received when message is sent
  }

  root ImSkypeRequest
  {
    Task *task;
    Model.RequestStatus? status;
  }
}
