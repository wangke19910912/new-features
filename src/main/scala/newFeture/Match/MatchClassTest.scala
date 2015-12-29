package newFeture.Match


/**
 * 模式匹配的深度使用
 */
object MatchClassTest {


  def main(args: Array[String]) {

    //首先Array调用Array的伴生对象apply方法,构造一个数组
    //然后将数据传递给Array(a,b),这时会调用unapply方法将a,b赋值给a,b
    //不能在匹配中加入大写开头的变量,编译器默认为常量

    val Array(a,b)= Array("a","b","c")

    val teacher = new Teacher("wangke1")
    val student = new Student("wangke2")
    val student2 = new Student2("wangke3")

    //会调用Array的apply方法构造Array,如果
    val people = Array(student, teacher, student2)


    for (p <- people) {
      p match {
        case Student() => println("got a student")
        case Teacher(name) => println("got a teacher"+name)
        case Student2(name) => println("got a student2:" + name)
      }
    }

  }

  class Persion


  class Student(val name: String) extends Persion

  //不使用case class 实现unapply也可实现自动提取功能,自动提取使用的是这个函数
  object Student {
    //在进行匹配时,会将match的参数persion传给unapply方法进行提取
    //如果case后的参数没有任何返回值,那么只需要返回false或者true即可
    def unapply(person: Persion) = {
      println("math the persion name")
      true
    }

  }


  class Student2(val name: String) extends Persion

  object Student2 {

    //在进行匹配时返回结果会带给case后的函数作为参数进行使用
    //可以返回Option[*],在运行时要么得到的是Some[*],要么得到的是NULL
    def unapply(persion: Persion): Option[String] = {

      //这个返回值充分展示了传入是wangke3,返回是wangke4的结果
      Option("wnagke4")
    }

  }

  case class Teacher(name: String) extends Persion

}
