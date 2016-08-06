/**
  *
  * Cart API:
  *
  * {{{
  * scala> api.cart("Apple", "Apple", "Orange", "Apple")
  * res1: Int = 205
  * }}}
  *
  * @author Juan M Uys
  */
object api {
  val catalogue = Map[String, Int]("Apple" -> 60, "Orange" -> 25) withDefaultValue 0

  def cart(items: String*): Int = items map catalogue sum
}
