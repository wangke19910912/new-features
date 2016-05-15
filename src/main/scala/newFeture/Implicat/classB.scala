package newFeture.Implicat

/**
 * 代表ClassA的增强类,可以继承自ClassB
 */

object classB {
  implicit def A2B(a: classA) = new classB
}

class classB {

  def funcB = {
    println("call function B")

  }
}
