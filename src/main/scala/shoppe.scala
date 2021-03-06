/**
  *
  * Cart API:
  *
  * {{{
  * scala> api.cart("Apple", "Apple", "Orange", "Apple")
  * res1: Int = 205
  * }}}
  *
  * Cart with promotions API:
  *
  * {{{
  * scala> api.cartPromos("Apple", "Apple")
  * res1: Int = 60
  *
  * scala> api.cartPromos("Orange", "Orange", "Orange")
  * res1: Int = 50
  *
  * scala> api.cartPromos("Apple", "Apple", "Orange", "Apple")
  * res1: Int = 145
  *
  * scala> api.cartPromos("Orange", "Japanese Quince")
  * res1: Int = 25
  * }}}
  *
  * @author Juan M Uys
  */
object api {
  val catalogue = Map[String, Int]("Apple" -> 60, "Orange" -> 25) withDefaultValue 0

  def cart(items: String*): Int = items map catalogue sum

// apples: pay for every second item
  // oranges: pay for every 1 and a half-eth item
  // everything else: pay for every item
  var promos = Map[String, Double] ("Apple" -> 2.0, "Orange" -> 1.5) withDefaultValue 1.0

  def cartPromos(items: String*): Int = items groupBy(i => i) map {
    case (item, items) => {
      math.ceil(items.length / promos(item)) * catalogue(item) toInt
    }
  } sum
}

object shop extends App {
  def usage() = println("""
           Usage: sbt run [cart|cartPromos] [list of product]
           Example: sbt "run cart Apple Apple Orange Apple"
                      """)

  if (args.length <= 1) {
    usage()
  } else {
    args(0) match {
      case "cart" =>
        val result = api.cart(args.slice(1, args.length):_*).toDouble / 100
        println(f"£${result}%2.2f")
      case "cartPromos" =>
        val result = api.cartPromos(args.slice(1, args.length):_*).toDouble / 100
        println(f"£${result}%2.2f")
      case _ => usage()
    }
  }
}
