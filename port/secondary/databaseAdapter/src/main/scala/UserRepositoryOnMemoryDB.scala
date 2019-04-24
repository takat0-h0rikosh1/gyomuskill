import UserRecordInitializeObject.Users
import cats.effect.IO
import monix.catnap.FutureLift
import monix.eval.Task
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._

import scala.concurrent.{ ExecutionContext, Future }

class UserRepositoryOnMemoryDB extends UserRepository {
  lazy val users = TableQuery[Users]
  lazy val db = Database.forConfig("h2mem1")

  override def resolveAll: Task[Seq[User]] =
    Task.deferFutureAction { implicit s =>
      db.run(users.result)
    }

}

