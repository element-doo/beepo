package hr.element.beepo
package Security
package postgres

import org.pgscala._
import hr.ngs.patterns.IServiceLocator

class UserRepositoryPlus(
    sessionFactory: PGSessionFactory
  , roleRepository: IRoleRepositoryPlus
  , locator: IServiceLocator
  ) extends UserRepository(sessionFactory, locator)
    with IUserRepositoryPlus {

  def getSalt(username: String) = "Beep@" + username

  def getUserCount(): Int = {
    sessionFactory.using( _.get[Int]("""
      SELECT COUNT(*)::int
      FROM "Security"."User" r;
""")
    )
  }

  private val HashFunc: (String => String) =
    org.apache.commons.codec.digest.DigestUtils.sha256Hex

  private def hashPassword(username: String, password: String) =
    HashFunc(getSalt(username) + password)

  def registerUser(username: String, password: String): User = {
    roleRepository.registerRole(username)
    val passwordHash = hashPassword(username, password)

    val user = User(username, passwordHash)
    insert(user)
    user
  }

  def deleteUser(username: String) {
    find(username) match {
      case Some(user) =>
        roleRepository.deleteRole(user.name)

        delete(user)

      case _ =>
        sys.error("User could not be deleted because it could not be found!")
    }
  }

  def alterPassword(username: String, password: String) {
    find(username) match {
      case Some(user) =>
        user.password =
          hashPassword(
              user.name
            , password
            )
        update( user )

      case _ =>
        sys.error("Password could not be altered because the username does not exist!")
    }
  }

  def checkLogin(username: String, password: String): Boolean = {
    sessionFactory.using( _.get[Boolean]("""
      SELECT EXISTS(
        SELECT 1
        FROM "Security"."User" r
        WHERE r."name" = @1 AND r."password" = @2
      );
""", username, hashPassword(username, password))
    )
  }
}
