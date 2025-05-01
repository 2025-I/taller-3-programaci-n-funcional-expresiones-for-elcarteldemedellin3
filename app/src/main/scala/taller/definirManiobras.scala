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
        (movidos ++ principal, restantes, dos)
      case Dos(n) if n > 0 =>
        val (movidos, restantes) = principal.splitAt(n min principal.length)
        (restantes, uno, dos ++ movidos)
      case Dos(n) if n < 0 =>
        val (movidos, restantes) = dos.splitAt((-n) min dos.length)
        (movidos ++ principal, uno, restantes)
      case _ => e
    }
  }

  def definirManiobra(t1: Tren, t2: Tren): Maniobra = {
    @tailrec
    def loop(estado: Estado, objetivo: Tren, acc: Maniobra): Maniobra = {
      objetivo match {
        case Nil => acc.reverse
        case cabeza :: resto =>
          val (principal, uno, dos) = estado

          val indiceOpt = (for {
            (v, idx) <- principal.zipWithIndex
            if v == cabeza
          } yield idx).headOption

          indiceOpt match {
            case Some(idx) =>
              val movs =
                (if (idx > 0) List(Uno(idx), Uno(-idx)) else Nil) ::: List(Dos(1), Dos(-1))
              val nuevoEstado = movs.foldLeft(estado)(aplicarMovimiento)
              loop(nuevoEstado, resto, movs.reverse ::: acc)
            case None =>
              acc.reverse
          }
      }
    }
    loop((t1, Nil, Nil), t2, Nil)
  }
}
