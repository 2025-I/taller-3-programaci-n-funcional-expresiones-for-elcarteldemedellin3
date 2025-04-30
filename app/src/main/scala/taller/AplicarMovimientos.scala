package taller

class AplicarMovimientos {
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
          i <- (principal.length - n) until principal.length if i >= 0
        } yield principal(i)
        val resto = for {
          i <- 0 until (principal.length - n) if i >= 0
        } yield principal(i)
        (resto.toList, uno ++ movidos.toList, dos)

      case Uno(n) if n < 0 =>
        val movidos = for {
          i <- 0 until -n if i < uno.length
        } yield uno(i)
        val resto = for {
          i <- -n until uno.length if i >= 0
        } yield uno(i)
        (movidos.toList ++ principal, resto.toList, dos)

      case Uno(0) =>
        e

      case Dos(n) if n > 0 =>
        val movidos = for {
          i <- (principal.length - n) until principal.length if i >= 0
        } yield principal(i)
        val resto = for {
          i <- 0 until (principal.length - n) if i >= 0
        } yield principal(i)
        (resto.toList, uno, dos ++ movidos.toList)

      case Dos(n) if n < 0 =>
        val movidos = for {
          i <- 0 until -n if i < dos.length
        } yield dos(i)
        val resto = for {
          i <- -n until dos.length if i >= 0
        } yield dos(i)
        (movidos.toList ++ principal, uno, resto.toList)

      case Dos(0) =>
        e
    }
  }

  def aplicarMovimientos(e: Estado, movs: List[Movimiento]): List[Estado] = {
    movs.scanLeft(e)((estado, movimiento) => aplicarMovimiento(estado, movimiento))
  }
}