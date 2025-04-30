package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AplicarMovimientosTest extends AnyFunSuite {
  val obj = new AplicarMovimientos()
  import obj._

  private def generarVagones(n: Int): Tren =
    List.tabulate(n)(i => ('a' + (i % 26)).toChar)

  /* ***********************
   * PRUEBAS DE JUGUETE (5)
   * ***********************/
  test("Secuencia simple: Uno(2) y Dos(3)") {
    val vagones = generarVagones(10)
    val e0: Estado = (vagones, Nil, Nil)
    val movimientos = List(Uno(2), Dos(3))
    val estados = aplicarMovimientos(e0, movimientos)

    assert(estados.size == 3)
    assert(estados(1)._2 == vagones.takeRight(2))
    assert(estados(2)._3 == vagones.slice(5, 8))
  }

  test("Secuencia con movimientos negativos") {
    val e0: Estado = (Nil, List('a', 'b', 'c'), List('x', 'y', 'z'))
    val movimientos = List(Uno(-2), Dos(-1))
    val estados = aplicarMovimientos(e0, movimientos)

    assert(estados(1)._1 == List('a', 'b'))
    assert(estados(1)._2 == List('c'))
    assert(estados(2)._1 == List('x', 'a', 'b'))
    assert(estados(2)._3 == List('y', 'z'))
  }

  test("Secuencia vacía") {
    val e0: Estado = (generarVagones(5), Nil, Nil)
    val movimientos = List()
    val estados = aplicarMovimientos(e0, movimientos)

    assert(estados.size == 1)
    assert(estados.head == e0)
  }

  test("Movimientos cero intercalados") {
    val e0: Estado = (List('a', 'b'), List('c'), List('d'))
    val movimientos = List(Uno(0), Dos(0), Uno(1))
    val estados = aplicarMovimientos(e0, movimientos)

    assert(estados(0) == e0)
    assert(estados(1) == e0)
    assert(estados(2) == e0)
    assert(estados(3)._2 == List('c', 'b'))
  }

  test("Conservación total tras secuencia simple") {
    val e0: Estado = (generarVagones(10), Nil, Nil)
    val movimientos = List(Uno(3), Dos(4), Uno(-1), Dos(-2))
    val estados = aplicarMovimientos(e0, movimientos)

    val total = estados.last._1.size + estados.last._2.size + estados.last._3.size
    assert(total == 10)
  }

  /* ***********************
   * PRUEBAS PEQUEÑAS (3)
   * ***********************/
  test("Mover toda la vía principal en varias etapas") {
    val vagones = generarVagones(100)
    val e0: Estado = (vagones, Nil, Nil)
    val movimientos = List.fill(10)(Uno(10))
    val estados = aplicarMovimientos(e0, movimientos)

    assert(estados.last._1.isEmpty)
    assert(estados.last._2.size == 100)
    assert(estados.last._3.isEmpty)
  }

  test("Secuencia alterna regular Uno y Dos") {
    val e0: Estado = (generarVagones(100), Nil, Nil)
    val movimientos = List.tabulate(20)(i => if (i % 2 == 0) Uno(5) else Dos(5))
    val estados = aplicarMovimientos(e0, movimientos)

    val total = estados.last._1.size + estados.last._2.size + estados.last._3.size
    assert(total == 100)
  }

  test("Movimiento negativo mayor al tamaño de vía") {
    val e0: Estado = (Nil, generarVagones(30), generarVagones(20))
    val movimientos = List(Uno(-100), Dos(-100))
    val estados = aplicarMovimientos(e0, movimientos)

    assert(estados.last._1.size == 50)
    assert(estados.last._2.isEmpty)
    assert(estados.last._3.isEmpty)
  }

  /* ***********************
   * PRUEBAS MEDIANAS (2)
   * ***********************/
  test("Secuencia larga combinada aleatoria") {
    val e0: Estado = (generarVagones(500), Nil, Nil)
    val movimientos = List.fill(50)(if (math.random() > 0.5) Uno(10) else Dos(10))
    val estados = aplicarMovimientos(e0, movimientos)

    val total = estados.last._1.size + estados.last._2.size + estados.last._3.size
    assert(total == 500)
  }

  test("Secuencia de extracción y reensamblado") {
    val vagones = generarVagones(500)
    val e0: Estado = (vagones, Nil, Nil)

    // Secuencia corregida (misma cantidad que se extrae)
    val movimientos = List(Uno(200), Dos(200), Uno(-200), Dos(-200))

    val estados = aplicarMovimientos(e0, movimientos)

    assert(estados.last._1.size == 500)  // Ahora sí coincidirá
    assert(estados.last._2.isEmpty)
    assert(estados.last._3.isEmpty)
  }

  /* ***********************
   * PRUEBAS GRANDES (2)
   * ***********************/
  test("Secuencia masiva alternada") {
    val e0: Estado = (generarVagones(1000), Nil, Nil)
    val movimientos = List.tabulate(100)(i => if (i % 3 == 0) Uno(7) else Dos(11))
    val estados = aplicarMovimientos(e0, movimientos)

    val total = estados.last._1.size + estados.last._2.size + estados.last._3.size
    assert(total == 1000)
  }

  test("Secuencia compleja estructurada") {
    val vagones = generarVagones(1000)
    val e0: Estado = (vagones, Nil, Nil)
    val movimientos = List(Uno(300), Dos(400), Uno(-100), Dos(-200), Uno(500))
    val estados = aplicarMovimientos(e0, movimientos)

    val total = estados.last._1.size + estados.last._2.size + estados.last._3.size
    assert(total == 1000)
  }
}
