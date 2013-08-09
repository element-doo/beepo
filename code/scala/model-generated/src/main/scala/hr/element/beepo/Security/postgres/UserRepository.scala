package hr.element.beepo
package Security.postgres

import hr.ngs.patterns._

class UserRepository(
    private val sessionFactory: org.pgscala.PGSessionFactory
  , private val locator: IServiceLocator
  ) extends Security.IUserRepository {

  import org.pgscala._

  val createFromResultSet = (rS: PGScalaResultSet) =>
    Security.postgres.UserConverter.fromPGString(rS.one[String], locator)

  def find(uris: Traversable[String]): IndexedSeq[Security.User] = {
    val pks = if(uris eq null) Array.empty[String] else uris.filter(_ ne null).toArray
    if (pks.isEmpty) {
      IndexedSeq.empty
    }
    else {
      val formattedUris = postgres.Utils.buildSimpleUriList(pks)
      sessionFactory.using( _.arr("""SELECT r
FROM "Security"."User_entity" r
WHERE r."name" IN (%s)""".format(formattedUris)) (createFromResultSet)
      )
    }
  }

  private val typeConverter = Security.postgres.UserConverter
  private val rootTypeConverter = typeConverter.toPGString _

  def persist(insert: Traversable[Security.User], update: Traversable[(Security.User, Security.User)], delete: Traversable[Security.User]): IndexedSeq[String] = {

    sessionFactory.using{ dbSession =>
      val insertValues = insert.toArray
      val updateValues = update.toArray
      val deleteValues = delete.toArray

      insertValues foreach { item => item.URI = Security.postgres.UserConverter.buildURI(item.name) }
      updateValues foreach { case(_, item) => item.URI = Security.postgres.UserConverter.buildURI(item.name) }

      val sqlCom = new StringBuilder("""/*NO LOAD BALANCE*/
        SELECT "Security"."persist_User"(
          %s::"Security"."User_entity"[],
          %s::"Security"."User_entity"[],
          %s::"Security"."User_entity"[],
          %s::"Security"."User_entity"[]""".format(
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
