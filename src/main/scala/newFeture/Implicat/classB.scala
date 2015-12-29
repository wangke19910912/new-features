package newFeture.Implicat

/**
 * Created by wangke on 15-12-29.
 */
object classB {
  implicit def A2B(a: classA) = new classB
}

class classB {

  def funcB = {
    println("call function B")

  }
}
