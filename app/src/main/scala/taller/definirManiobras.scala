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

  def aplicarMovimiento(e: Estado, m: Movimiento): Estado = m match {
    case Uno(n) if n > 0 =>
      val (principal, uno, dos) = e
      val (movidos, restantes) = principal.splitAt(n.min(principal.length))
      (restantes, uno ++ movidos, dos)
    case Uno(n) if n < 0 =>
      val (principal, uno, dos) = e
      val (movidos, restantes) = uno.splitAt((-n).min(uno.length))
      (principal ++ movidos, restantes, dos)
    case Dos(n) if n > 0 =>
      val (principal, uno, dos) = e
      val (movidos, restantes) = principal.splitAt(n.min(principal.length))
      (restantes, uno, dos ++ movidos)
    case Dos(n) if n < 0 =>
      val (principal, uno, dos) = e
      val (movidos, restantes) = dos.splitAt((-n).min(dos.length))
      (principal ++ movidos, uno, restantes)
    case _ => e
  }

  def definirManiobra(t1: Tren, t2: Tren): Maniobra = {
    @tailrec
    def definirManiobraAux(estado: Estado, movimientos: Maniobra): Maniobra = {
      val (principal, uno, dos) = estado

      if (principal == t2) movimientos
      else if (principal.nonEmpty) {
        definirManiobraAux(aplicarMovimiento(estado, Uno(1)), movimientos :+ Uno(1))
      } else if (uno.nonEmpty) {
        definirManiobraAux(aplicarMovimiento(estado, Dos(1)), movimientos :+ Dos(1))
      } else if (dos.nonEmpty) {
        definirManiobraAux(aplicarMovimiento(estado, Dos(-1)), movimientos :+ Dos(-1))
      } else movimientos
    }
    definirManiobraAux((t1, Nil, Nil), Nil)
  }
}