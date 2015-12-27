package newFeture.Actor

import scala.actors.Actor
import scala.actors.Actor._

/**
 * 使用多线程计算1-1000的和
 */
object TotalCalculate {


  def main(args: Array[String]) {

    //input 1000
    var total = 0

    val calculateThread = actor {
      while (true) {
        receive {
          case num: Int => total = total + num
          case "FinshTasks" => println("task finsh !!!!total is " + total);
        }
      }
    }
    split(calculateThread, 1000, 100)

    Thread.sleep(20000)

  }

  //分解任务多线程计算
  def split(thread: Actor, total: Int, split: Int) = {

    var s = 0
    do {
      s = s + split
      actor {
        println("start to calculate %i to %i", s, s + split)
        thread ! calculate(s, s + split)
      }
    } while (s < total)

    Thread.sleep(2000)
    thread! "FinshTasks"
  }

  def calculate(m: Int, n: Int) = {

    var result = 0
    for (k <- m to n) {
      result = result + k
    }

    println("finsh to calculate %d to %d", m, n)
    result
  }

}
