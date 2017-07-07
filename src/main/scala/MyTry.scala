/**
  * Created by yangshuxuan on 17-7-4.
  */
package com.adups
abstract class MyTry[+T] {

}
case class MySuccess[T](v:T) extends MyTry[T]
case class MyFailure(msg:String) extends  MyTry[Nothing]
object MyTry{
  def deal(result:MyTry[Int]): Unit ={
    result match {
      case MySuccess(x) => println(x)
      case MyFailure(msg) => println(msg)
    }
  }

  def main(args: Array[String]): Unit = {
    deal(MySuccess(5))
    deal(MyFailure("failed"))
  }
}
