package  com.adups
import scala.util.{Failure, Success, Try}

/**
  * Created by yangshuxuan on 17-7-6.
  */
trait MyFuture[T] { self =>
  private[this]  var r:Option[Try[T]]  = None
  def result = r
  def result_= (x:Option[Try[T]]): Unit = {
    this.synchronized {
      r = x
      run()
    }
  }
  private  var status = false
  type Action = Try[T] => Unit
  var actions = List.empty[Action]
  def dealAction(action:Action): Unit = {
    result.foreach(action(_))
  }
  def run(): Unit ={
    actions.foreach(dealAction)
  }
  def onComplete(action:Action): Unit ={
    this.synchronized {
      dealAction(action)
      actions = action :: actions
    }
  }
  def flatMap[M](f:T => MyFuture[M]):MyFuture[M]={
    new MyFuture[M] {
      override def onComplete(action: Action): Unit = {
        self.onComplete {
          case Success(x) => f(x).onComplete(action)
          case Failure(e) => action
        }
      }
    }
  }


}
object MyFuture{
  def apply[T](b: => T): MyFuture[T] = {
    var future = new MyFuture[T](){}
    val thread = new Thread{
      override def run(): Unit ={
        var temp:Option[Try[T]] = None
        try{
          temp = Some(Success(b))
        }catch {
          case throwable:Throwable => temp = Some(Failure(throwable))
        }finally {

          future.result = temp

        }
      }

    }
    thread.start()
    future
  }

  def main(args: Array[String]): Unit = {
    val future = MyFuture{
      Thread.sleep(5 * 1000)
      8 / 4

    }
    val p = future.flatMap(t => MyFuture{
      math.sqrt(t)
    })
    future.onComplete({
      case Success(x) => println(x)
      case Failure(e) => println(e.getMessage())
    })
    future.onComplete({
      case Success(x) => println(x * 6)
      case Failure(e) => println(e.getMessage())
    })
    Thread.sleep(10 * 1000)
    println("continue")
    future.onComplete({
      case Success(x) => println(x * 2)
      case Failure(e) => println(e.getMessage())
    })
    p.onComplete({
      case Success(x) => println(x * 2)
      case Failure(e) => println(e.getMessage())
    })

  }
}
