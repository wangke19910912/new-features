package newFeture.Implicat

/**
 * 展示如何使用隐式转换
 */

object ImplicatTest {


  def main(args: Array[String]) {

    //在适当的作用域中引入隐式转换
    //方式1:引入所在包中的隐式转换函数
    import newFeture.Implicat.classB._

    //方式2:在当前类中添加隐式转换方法
    //    implicit def A2B(a: classA) = new classB
    val a = new classA
    //当调用a的funcB方法时候,因为没有找到,所以会寻找当前作用域是否存在隐式转换,从伴生对象开始寻找
    a.funcA
    a.funcB
  }

}

class classA {

  def funcA = {

    println("call function A")
  }

}



