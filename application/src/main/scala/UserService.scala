import monix.eval.Task

trait UserService {
  def getAll: Task[Seq[User]]
}
class UserServiceImpl(userRepository: UserRepository) extends UserService {
  def getAll: Task[Seq[User]] = userRepository.resolveAll
}
