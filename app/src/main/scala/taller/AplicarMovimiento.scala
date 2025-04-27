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
          i <- (principal.length - n) until principal.length
        } yield principal(i)
        val resto = for {
          i <- 0 until (principal.length - n)
        } yield principal(i)
        (resto.toList, uno ++ movidos.toList, dos)

      case Uno(n) if n < 0 =>
        val movidos = for {
          i <- 0 until -n
        } yield uno(i)
        val resto = for {
          i <- (-n) until uno.length
        } yield uno(i)
        (movidos.toList ++ principal, resto.toList, dos)

      case Uno(0) =>
        e

      case Dos(n) if n > 0 =>
        val movidos = for {
          i <- (principal.length - n) until principal.length
        } yield principal(i)
        val resto = for {
          i <- 0 until (principal.length - n)
        } yield principal(i)
        (resto.toList, uno, dos ++ movidos.toList)

      case Dos(n) if n < 0 =>
        val movidos = for {
          i <- 0 until -n
        } yield dos(i)
        val resto = for {
          i <- (-n) until dos.length
        } yield dos(i)
        (movidos.toList ++ principal, uno, resto.toList)

      case Dos(0) =>
        e
    }
  }
}
