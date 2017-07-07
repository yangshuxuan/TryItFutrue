/**
  * Created by yangshuxuan on 17-7-2.
  */
package com.adups
trait MyStream[+T] extends IndexedSeq[T] {
  //def head:T
  //val tail:MyStream[T]
  //def q[U >: T](v:U):MyStream[U]
  def #:: [U >: T](v:U):MyStream[U]= new #::[U](v,this)

}
 class #::[T](_head:T,  _tail: => MyStream[T]) extends MyStream[T]{
   override  val head = _head
   override lazy val tail = _tail

   override def apply(idx: Int): T = if(idx == 0){
     head
   } else {
     tail(idx - 1)
   }

   override def length: Int = ???
 }
object #::{
  def apply[T](_head: T, _tail: => MyStream[T])= new #::(_head, _tail)
}
object T {
  def fib(a:Int,b:Int):MyStream[Int]={
    //new #::(a,fib(b,a+b))
    a #:: fib(b,a+b)
  }

  def main(args: Array[String]): Unit = {
    val k = fib(1,2)
    println(k.head)
    println(k.tail.head)
    println(k.tail.tail.head)
    println(k(10))
  }
}
