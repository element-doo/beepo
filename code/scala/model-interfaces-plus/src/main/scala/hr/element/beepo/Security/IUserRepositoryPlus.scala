package hr.element.beepo
package Security

trait IUserRepositoryPlus extends IUserRepository {
  def getSalt(username: String): String
  def getUserCount(): Int
  def registerUser(username: String, password: String): User
  def deleteUser(username: String): Unit
  def alterPassword(username: String, password: String): Unit
  def checkLogin(username: String, password: String): Boolean
}
