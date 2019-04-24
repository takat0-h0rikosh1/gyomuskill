import java.time.LocalDateTime
import java.util.concurrent.{ExecutorService, Executors, ForkJoinPool}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, ExecutionContextExecutorService, Future}
import scala.concurrent.ExecutionContext.Implicits.global

object StudyExecutionContext {

// Create ExecutionContext from java.util.concurrent.Executor
val myExecutors: ExecutorService = Executors.newFixedThreadPool(3)
val myExecutionContext1: ExecutionContextExecutor = ExecutionContext.fromExecutor(myExecutors)

// Create ExecutionContext from java.util.concurrent.ExecutorService
val myExecuteService: ForkJoinPool = new ForkJoinPool(3)
val myExecutionContext2: ExecutionContextExecutorService = ExecutionContext.fromExecutorService(myExecuteService)

 def now(): LocalDateTime = LocalDateTime.now()

  def doFuture(): Unit =
    (1 to 40).foreach(_ => Future{Thread.sleep(2000);println(now())})

}
