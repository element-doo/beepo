package hr.element.beepo
package Model.postgres

import hr.ngs.patterns._

class ImSkypeRequestRepository(
    private val sessionFactory: org.pgscala.PGSessionFactory
  , private val locator: IServiceLocator
  ) extends Model.IImSkypeRequestRepository {

  import org.pgscala._

  val createFromResultSet = (rS: PGScalaResultSet) =>
    Model.postgres.ImSkypeRequestConverter.fromPGString(rS.one[String], locator)

  def find(uris: Traversable[String]): IndexedSeq[Model.ImSkypeRequest] = {
    val pks = if(uris eq null) Array.empty[String] else uris.filter(_ ne null).toArray
    if (pks.isEmpty) {
      IndexedSeq.empty
    }
    else {
      val formattedUris = postgres.Utils.buildSimpleUriList(pks)
      sessionFactory.using( _.arr("""SELECT r
FROM "Model"."ImSkypeRequest_entity" r
WHERE r."ID" IN (%s)""".format(formattedUris)) (createFromResultSet)
      )
    }
  }

  private val typeConverter = Model.postgres.ImSkypeRequestConverter
  private val rootTypeConverter = typeConverter.toPGString _

  def persist(insert: Traversable[Model.ImSkypeRequest], update: Traversable[(Model.ImSkypeRequest, Model.ImSkypeRequest)], delete: Traversable[Model.ImSkypeRequest]): IndexedSeq[String] = {

    sessionFactory.using{ dbSession =>
      val insertValues = insert.toArray
      val updateValues = update.toArray
      val deleteValues = delete.toArray

      if(insertValues.nonEmpty) {
        val ids = dbSession.getArr[Int]("""/*NO LOAD BALANCE*/SELECT nextval('"Model"."ImSkypeRequest_ID_seq"'::regclass)::int FROM generate_series(1, @1);""", insertValues.size)

        insertValues.zip(ids) foreach { case (item, id) =>
          item.ID = id
        }
      }

      insertValues foreach { item => item.URI = Model.postgres.ImSkypeRequestConverter.buildURI(item.ID) }
      updateValues foreach { case(_, item) => item.URI = Model.postgres.ImSkypeRequestConverter.buildURI(item.ID) }

      val sqlCom = new StringBuilder("""/*NO LOAD BALANCE*/
        SELECT "Model"."persist_ImSkypeRequest"(
          %s::"Model"."ImSkypeRequest_entity"[],
          %s::"Model"."ImSkypeRequest_entity"[],
          %s::"Model"."ImSkypeRequest_entity"[],
          %s::"Model"."ImSkypeRequest_entity"[]""".format(
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
