import monix.eval.Task

trait UserRepository {
  def resolveAll: Task[Seq[User]]
}
