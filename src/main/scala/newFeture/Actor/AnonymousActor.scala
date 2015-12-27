package newFeture.Actor

import scala.actors.Actor._
import scala.actors.TIMEOUT

/**
 * 演示匿名Actor的使用(Actor的伴生对象的使用)
 */
object AnonymousActor {

  def main(args: Array[String]) {

    //匿名actor,创建就开始运行
    val anonymousActor = actor {
      while (true) {
        //使用receive 进行消息相应
        receive {
          case "hello" => println("world")
        }
      }
    }

    val anonymousActor2 = actor {
      while (true) {

        receiveWithinWhile()

      }

    }
    //发送消息
    anonymousActor ! "hello"

    var i = 0;
    while (i < 10) {
      anonymousActor2 ! "hello"
      Thread.sleep(800)
      i = i + 1
    }

    while (i < 20) {
      anonymousActor2 ! "hello"
      Thread.sleep(1100)
      i = i + 1;
    }
  }

  def receiveWithinWhile(): Unit = {
    while (true) {
      //接收消息时间超过1000ms会自动响应timeout消息
      receiveWithin(1000) {
        case "hello" => println("world")
        case TIMEOUT => println("thread is time out ! ")
      }
    }

  }


}
