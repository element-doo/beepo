package hr.element.beepo
package processor


import Model.{EmailSmtpRequest, SmsIptRequest, Task}

trait ITaskProcessor {

  def send(
      taks: Task
    , emails: IndexedSeq[EmailSmtpRequest]
    , smss: IndexedSeq[SmsIptRequest]
    ): String

  def send(uris: IndexedSeq[String]): String

  def enqueue(
      taks: Task
    , emails: IndexedSeq[EmailSmtpRequest]
    , smss: IndexedSeq[SmsIptRequest]
    ): String
}