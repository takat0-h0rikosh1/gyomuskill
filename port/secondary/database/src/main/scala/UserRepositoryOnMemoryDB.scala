import UserRecordInitializeObject.Users
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

class UserRepositoryOnMemoryDB extends UserRepository {
  lazy val users = TableQuery[Users]
  lazy val db = Database.forConfig("h2mem1")
  override def resolveAll: Future[Seq[User]] = {
    val names = users.map(_.mapTo[User]).result
    db.run(names)
  }
}
