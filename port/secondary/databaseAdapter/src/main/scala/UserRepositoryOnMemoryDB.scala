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

object FutureToTask {
  def sum(list: List[Int])(implicit ec: ExecutionContext): Future[Long] =
    Future(list.foldLeft(0L)(_ + _))

  def sumTask1(list: List[Int])(implicit ec: ExecutionContext): Task[Long] =
    Task.fromFuture(sum(list))

  // Future.apply の実行を遅延する (defer の alias)
  def sumTask2(list: List[Int])(implicit ec: ExecutionContext): Task[Long] =
    Task.suspend {
      Task.fromFuture(sum(list))
    }

  // Future.apply の実行を遅延するスマートな記述
  // ベストプラクティスには反している
  def sumTask3(list: List[Int])(implicit ec: ExecutionContext): Task[Long] =
    Task.deferFuture(sum(list))

  // コールバックにインジェクトされた scheduler を暗黙知として受け取るパターン
  // メソッドのシグネチャから ExecutionContext の暗黙引数が消えるは良さそう
  def sumTask4(list: List[Int]): Task[Long] =
    Task.deferFutureAction { implicit scheduler =>
      sum(list)
    }

  import monix.execution.Scheduler.Implicits.global

  val _ = sumTask4(List(1, 2, 3)).runToFuture

  def sumTask5(list: List[Int])(implicit ec: ExecutionContext): Task[Long] =
    FutureLift.from(Task(sum(list)))

  def sumTask6(list: List[Int]): Task[Long] =
    Task.deferAction { implicit s =>
      FutureLift.from(Task(sum(list)(s)))
    }

  def sumTask7(list: List[Int])(implicit ec: ExecutionContext): IO[Long] =
    FutureLift.from(IO(sum(list)))

}
