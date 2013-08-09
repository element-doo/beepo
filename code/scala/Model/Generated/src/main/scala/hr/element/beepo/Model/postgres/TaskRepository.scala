package hr.element.beepo
package Model.postgres

import hr.ngs.patterns._

class TaskRepository(
    private val sessionFactory: org.pgscala.PGSessionFactory
  , private val locator: IServiceLocator
  ) extends Model.ITaskRepository {

  import org.pgscala._

  val createFromResultSet = (rS: PGScalaResultSet) =>
    Model.postgres.TaskConverter.fromPGString(rS.one[String], locator)

  def find(uris: Traversable[String]): IndexedSeq[Model.Task] = {
    val pks = if(uris eq null) Array.empty[String] else uris.filter(_ ne null).toArray
    if (pks.isEmpty) {
      IndexedSeq.empty
    }
    else {
      val formattedUris = postgres.Utils.buildSimpleUriList(pks)
      sessionFactory.using( _.arr("""SELECT r
FROM "Model"."Task_entity" r
WHERE r."ID" IN (%s)""".format(formattedUris)) (createFromResultSet)
      )
    }
  }

  private val typeConverter = Model.postgres.TaskConverter
  private val rootTypeConverter = typeConverter.toPGString _

  def persist(insert: Traversable[Model.Task], update: Traversable[(Model.Task, Model.Task)], delete: Traversable[Model.Task]): IndexedSeq[String] = {

    sessionFactory.using{ dbSession =>
      val insertValues = insert.toArray
      val updateValues = update.toArray
      val deleteValues = delete.toArray

      insertValues foreach { item => item.URI = Model.postgres.TaskConverter.buildURI(item.ID) }
      updateValues foreach { case(_, item) => item.URI = Model.postgres.TaskConverter.buildURI(item.ID) }

      val sqlCom = new StringBuilder("""/*NO LOAD BALANCE*/
        SELECT "Model"."persist_Task"(
          %s::"Model"."Task_entity"[],
          %s::"Model"."Task_entity"[],
          %s::"Model"."Task_entity"[],
          %s::"Model"."Task_entity"[]""".format(
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
