package newFeture.Implicat

import scala.math.Ordering.Implicits

/**
 * 展示如何使用隐式转换
 * 特别的在scala的源代码中可以使用隐式转换成scala的类
 * 但是java不能动态转换成scala类
 */

object ImplicatTest {


  def main(args: Array[String]) {

    //在适当的作用域中引入隐式转换
    //方式1:引入所在包中的隐式转换函数
    import newFeture.Implicat.classB._

    //方式2:在当前类中添加隐式转换方法
    //    implicit def A2B(a: classA) = new classB
    val a = new classA
    a.funcA
    // Class A 没有B 方法,但是可以通过隐式转换转换为类B,然后进行处理
    a.funcB

  }

}

class classA {

  def funcA = {

    println("call function A")
  }

  //隐式参数转换
  def bigger[T](a: T, b: T)(implicit ordered: T => Ordered[T]) = {

    if (ordered(a) > b) a else b

  }

  //上下文界定


}



