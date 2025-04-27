package taller

import scala.annotation.tailrec

class definirManiobras {
  type Vagon = Any
  type Tren = List[Vagon]
  type Estado = (Tren, Tren, Tren)
  type Maniobra = List[Movimiento]

  sealed trait Movimiento
  case class Uno(n: Int) extends Movimiento
  case class Dos(n: Int) extends Movimiento

  def aplicarMovimiento(e: Estado, m: Movimiento): Estado = {
    val (principal, uno, dos) = e
    m match {
      case Uno(n) if n > 0 =>
        val (movidos, restantes) = principal.splitAt(n min principal.length)
        (restantes, uno ++ movidos, dos)
      case Uno(n) if n < 0 =>
        val (movidos, restantes) = uno.splitAt((-n) min uno.length)
        (principal ++ movidos, restantes, dos)
      case Dos(n) if n > 0 =>
        val (movidos, restantes) = principal.splitAt(n min principal.length)
        (restantes, uno, dos ++ movidos)
      case Dos(n) if n < 0 =>
        val (movidos, restantes) = dos.splitAt((-n) min dos.length)
        (principal ++ movidos, uno, restantes)
      case _ => e
    }
  }

  def definirManiobra(t1: Tren, t2: Tren): Maniobra = {
    @tailrec
    def loop(estado: Estado, movimientos: Maniobra): Maniobra = {
      val (principal, uno, dos) = estado

      if (principal == t2) movimientos
      else {
        val opciones = for {
          mov <- List(
            if (principal.nonEmpty) Some(Uno(1)) else None,
            if (principal.isEmpty && uno.nonEmpty) Some(Dos(1)) else None,
            if (principal.isEmpty && uno.isEmpty && dos.nonEmpty) Some(Dos(-1)) else None
          )
          movimiento <- mov
        } yield movimiento

        opciones.headOption match {
          case Some(movimiento) =>
            loop(aplicarMovimiento(estado, movimiento), movimientos :+ movimiento)
          case None => movimientos
        }
      }
    }

    loop((t1, Nil, Nil), Nil)
  }
}
