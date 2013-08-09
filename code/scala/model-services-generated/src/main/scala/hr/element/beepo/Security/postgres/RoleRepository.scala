package hr.element.beepo
package Security.postgres

import hr.ngs.patterns._

class RoleRepository(
    private val sessionFactory: org.pgscala.PGSessionFactory
  , private val locator: IServiceLocator
  ) extends Security.IRoleRepository {

  import org.pgscala._

  val createFromResultSet = (rS: PGScalaResultSet) =>
    Security.postgres.RoleConverter.fromPGString(rS.one[String], locator)

  def find(uris: Traversable[String]): IndexedSeq[Security.Role] = {
    val pks = if(uris eq null) Array.empty[String] else uris.filter(_ ne null).toArray
    if (pks.isEmpty) {
      IndexedSeq.empty
    }
    else {
      val formattedUris = postgres.Utils.buildSimpleUriList(pks)
      sessionFactory.using( _.arr("""SELECT r
FROM "Security"."Role_entity" r
WHERE r."Name" IN (%s)""".format(formattedUris)) (createFromResultSet)
      )
    }
  }

  private val typeConverter = Security.postgres.RoleConverter
  private val rootTypeConverter = typeConverter.toPGString _

  def persist(insert: Traversable[Security.Role], update: Traversable[(Security.Role, Security.Role)], delete: Traversable[Security.Role]): IndexedSeq[String] = {

    sessionFactory.using{ dbSession =>
      val insertValues = insert.toArray
      val updateValues = update.toArray
      val deleteValues = delete.toArray

      insertValues foreach { item => item.URI = Security.postgres.RoleConverter.buildURI(item.Name) }
      updateValues foreach { case(_, item) => item.URI = Security.postgres.RoleConverter.buildURI(item.Name) }

      val sqlCom = new StringBuilder("""/*NO LOAD BALANCE*/
        SELECT "Security"."persist_Role"(
          %s::"Security"."Role_entity"[],
          %s::"Security"."Role_entity"[],
          %s::"Security"."Role_entity"[],
          %s::"Security"."Role_entity"[]""".format(
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
