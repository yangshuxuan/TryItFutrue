def f(n:Int):Stream[Int]={
  n #:: f(n + 1)
}
val p = f(0)
p(1)
p(2)
def seive(h:Stream[Int]):Stream[Int]={
  h.head #:: seive(h.tail.filter(_ % h.head != 0))
}
val u = seive(f(2))
u.take(1000).toList


360 / 12
val thread =new Thread(new Runnable[Int]{ def run(){print("hi")}})
thread.start()
