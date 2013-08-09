package hr.element.beepo
package Model.postgres

import hr.ngs.patterns._

class EmailSmtpRequestRepository(
    private val sessionFactory: org.pgscala.PGSessionFactory
  , private val locator: IServiceLocator
  ) extends Model.IEmailSmtpRequestRepository {

  import org.pgscala._

  val createFromResultSet = (rS: PGScalaResultSet) =>
    Model.postgres.EmailSmtpRequestConverter.fromPGString(rS.one[String], locator)

  def find(uris: Traversable[String]): IndexedSeq[Model.EmailSmtpRequest] = {
    val pks = if(uris eq null) Array.empty[String] else uris.filter(_ ne null).toArray
    if (pks.isEmpty) {
      IndexedSeq.empty
    }
    else {
      val formattedUris = postgres.Utils.buildSimpleUriList(pks)
      sessionFactory.using( _.arr("""SELECT r
FROM "Model"."EmailSmtpRequest_entity" r
WHERE r."ID" IN (%s)""".format(formattedUris)) (createFromResultSet)
      )
    }
  }
  def findByTaskID(taskID: java.util.UUID): IndexedSeq[Model.EmailSmtpRequest] = {

    throw new UnsupportedOperationException("Please implement findByTaskID and register implementation in the container")
  }

  private val typeConverter = Model.postgres.EmailSmtpRequestConverter
  private val rootTypeConverter = typeConverter.toPGString _

  def persist(insert: Traversable[Model.EmailSmtpRequest], update: Traversable[(Model.EmailSmtpRequest, Model.EmailSmtpRequest)], delete: Traversable[Model.EmailSmtpRequest]): IndexedSeq[String] = {

    sessionFactory.using{ dbSession =>
      val insertValues = insert.toArray
      val updateValues = update.toArray
      val deleteValues = delete.toArray

      if(insertValues.nonEmpty) {
        val ids = dbSession.getArr[Int]("""/*NO LOAD BALANCE*/SELECT nextval('"Model"."EmailSmtpRequest_ID_seq"'::regclass)::int FROM generate_series(1, @1);""", insertValues.size)

        insertValues.zip(ids) foreach { case (item, id) =>
          item.ID = id
        }
      }

      insertValues foreach { item => item.URI = Model.postgres.EmailSmtpRequestConverter.buildURI(item.ID) }
      updateValues foreach { case(_, item) => item.URI = Model.postgres.EmailSmtpRequestConverter.buildURI(item.ID) }

      val sqlCom = new StringBuilder("""/*NO LOAD BALANCE*/
        SELECT "Model"."persist_EmailSmtpRequest"(
          %s::"Model"."EmailSmtpRequest_entity"[],
          %s::"Model"."EmailSmtpRequest_entity"[],
          %s::"Model"."EmailSmtpRequest_entity"[],
          %s::"Model"."EmailSmtpRequest_entity"[]""".format(
        postgres.Utils.createArrayLiteral(insertValues, rootTypeConverter),
        postgres.Utils.createArrayLiteral(updateValues map(_._1), rootTypeConverter),
        postgres.Utils.createArrayLiteral(updateValues map(_._2), rootTypeConverter),
        postgres.Utils.createArrayLiteral(deleteValues, rootTypeConverter)))

      sqlCom.append(")")

      dbSession.exec(sqlCom.toString)

      insertValues.map(_.URI)
    } // using
  }

}
