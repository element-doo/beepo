package hr.element.beepo
package Model

trait ITaskRepositoryPlus extends ITaskRepository {
  def findByRequestIDorURI(key: String): Option[Task]
  def findByRequestIDorURI(uris: IndexedSeq[String]): IndexedSeq[Task]
}
