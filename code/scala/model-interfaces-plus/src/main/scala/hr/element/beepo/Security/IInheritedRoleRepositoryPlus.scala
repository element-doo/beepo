package hr.element.beepo
package Security

trait IInheritedRoleRepositoryPlus extends IInheritedRoleRepository {
  def findRoles(uri: String): IndexedSeq[InheritedRole]  
}
