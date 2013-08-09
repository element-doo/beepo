package hr.element.beepo
package Security

trait IRoleRepositoryPlus extends IRoleRepository {
  def registerRole(name: String): Role
  def assignRole(name: String, role: String): Unit
  def deleteRole(name: String): Unit
}
