import Module._

import slick.jdbc.H2Profile.api._
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {
  val hoge = design.newSession.build[SampleTraitInject]
  val db = Database.forConfig("h2mem1")
  Await.result(db.run(UserRecordInitializeObject.initializedF), Duration.Inf)

  import monix.execution.Scheduler.Implicits.global
  println(Await.result(hoge.userService.getAll.runToFuture, Duration.Inf))

  //  design.withSession.build[SampleTraitInject] { _ =>
  //    QuickstartServer.main(Array.empty)
  //  }

}
