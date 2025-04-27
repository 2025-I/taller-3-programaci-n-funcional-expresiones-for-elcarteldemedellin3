package taller

class AplicarMovimiento {
  type Vagon = Any
  type Tren = List[Vagon]
  type Estado = (Tren, Tren, Tren)

  trait Movimiento
  case class Uno(n: Int) extends Movimiento
  case class Dos(n: Int) extends Movimiento

  def aplicarMovimiento(e: Estado, m: Movimiento): Estado = {
    val (principal, uno, dos) = e

    m match {
      case Uno(n) if n > 0 =>
        val movidos = for {
          i <- Math.max(0, principal.length - n) until principal.length
        } yield principal(i)
        val resto = for {
          i <- 0 until Math.max(0, principal.length - n)
        } yield principal(i)
        (resto.toList, uno ++ movidos.toList, dos)

      case Uno(n) if n < 0 =>
        val movidos = for {
          i <- 0 until Math.min(-n, uno.length)
        } yield uno(i)
        (principal.reverse_:::(movidos.toList).reverse,
          uno.drop(Math.min(-n, uno.length)),
          dos)

      case Uno(0) =>
        e

      case Dos(n) if n > 0 =>
        val movidos = for {
          i <- Math.max(0, principal.length - n) until principal.length
        } yield principal(i)
        val resto = for {
          i <- 0 until Math.max(0, principal.length - n)
        } yield principal(i)
        (resto.toList, uno, dos ++ movidos.toList)

      case Dos(n) if n < 0 =>
        val movidos = for {
          i <- 0 until Math.min(-n, dos.length)
        } yield dos(i)
        (principal.reverse_:::(movidos.toList).reverse,
          uno,
          dos.drop(Math.min(-n, dos.length)))

      case Dos(0) =>
        e
    }
  }
}