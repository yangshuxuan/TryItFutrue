import scala.concurrent.Future
import scala.util.Try
import scala.util.Success
import scala.util.Failure
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by yangshuxuan on 17-7-1.
  */
class TryIt {

}
object TryIt{
  def main(args: Array[String]): Unit = {
    println("hi")
    val p:Future[Int] = Future[Int] {
      Thread.sleep(10 * 1000)
      val f = 100
      f * f
    }
    p onComplete {
      case Success(x) => println(x)
      case Failure(msg) => println(msg)
    }
    Thread.sleep(20 * 1000)
  }
}
