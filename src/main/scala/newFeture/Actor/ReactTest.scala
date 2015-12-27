package newFeture.Actor

import scala.actors.{TIMEOUT, Actor}
import scala.actors.Actor._

/**
 * 演示react的使用方法
 */
object ReactTest {


  case class Student(name: String, age: Int)

  var condition = true
  def main(args: Array[String]) {


    val anonymousActor = actor {
      //使用react进行消息相应
      //主线程不能包含有消息处理receive/react等,会造成主线程阻塞
//      doReact()
//      doReactLoopWhile()
        doReactWithin()
    }

    anonymousActor ! Student("wangke", 12)
    anonymousActor ! Student("zhaomeng", 13)

    while(true){
      receive{
        case s: String => println(s);condition=false
      }
    }
  }

  def doReact(): Unit = {

    react {
      //doReact的造作一定要放在最后完成,否则之后语句无法执行,sender为发送的客户端的句柄
      case Student(name, age) => println("get a student name is " + name);  sender ! "ok";doReact();

    }
  }

  def doReactWithin():Unit={
    //
   reactWithin(2000){
     case Student(name, age) => println("get a student name is " + name);sender ! "ok";doReactWithin();
     case TIMEOUT => println("time is out !")
   }
  }
  def doReactLoop()={

    //与递归+react作用相同
    loop{
      react{
        case Student(name, age) => println("get a student name is " + name);  sender ! "ok";
      }
    }
  }

  def doReactLoopWhile()={

    //添加了条件的reactloop
    loopWhile(condition){
      react{
        case Student(name, age) => println("get a student name is " + name);  sender ! "ok";
      }
    }

  }
}

