package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AplicarMovimientoTest extends AnyFunSuite {

  val obj = new AplicarMovimiento()
  import obj._

  // ==== PRUEBAS DE JUGUETE (tamaño 10) ====
  test("Juguete 1: Uno(2) mueve de principal a uno") {
    val estado = (List('a', 'b', 'c', 'd'), Nil, Nil)
    val esperado = (List('a', 'b'), List('c', 'd'), Nil)
    assert(aplicarMovimiento(estado, Uno(2)) === esperado)
  }

  test("Juguete 2: Dos(2) mueve de principal a dos") {
    val estado = (List('a', 'b', 'c', 'd'), Nil, Nil)
    val esperado = (List('a', 'b'), Nil, List('c', 'd'))
    assert(aplicarMovimiento(estado, Dos(2)) === esperado)
  }

  test("Juguete 3: Uno(-2) mueve de uno a principal") {
    val estado = (List('a'), List('c', 'd'), List('b'))
    val esperado = (List('c', 'd', 'a'), Nil, List('b'))
    assert(aplicarMovimiento(estado, Uno(-2)) === esperado)
  }

  test("Juguete 4: Dos(-1) mueve de dos a principal") {
    val estado = (List(), List('c', 'd'), List('a', 'b'))
    val esperado = (List('a'), List('c', 'd'), List('b'))
    assert(aplicarMovimiento(estado, Dos(-1)) === esperado)
  }

  test("Juguete 5: Uno(0) no cambia el estado") {
    val estado = (List('x', 'y'), List('z'), List())
    assert(aplicarMovimiento(estado, Uno(0)) === estado)
  }

  // ==== PRUEBAS PEQUEÑAS (tamaño 100) ====
  test("Pequeña 1: mover 10 a uno") {
    val principal = (1 to 100).toList
    val esperado = ((1 to 90).toList, (91 to 100).toList, Nil)
    assert(aplicarMovimiento((principal, Nil, Nil), Uno(10)) === esperado)
  }

  test("Pequeña 2: mover 20 a dos") {
    val principal = (1 to 100).toList
    val esperado = ((1 to 80).toList, Nil, (81 to 100).toList)
    assert(aplicarMovimiento((principal, Nil, Nil), Dos(20)) === esperado)
  }

  test("Pequeña 3: retornar 10 de uno") {
    val uno = (91 to 100).toList
    val esperado = ((91 to 100).toList ++ (1 to 90).toList, Nil, Nil)
    assert(aplicarMovimiento(((1 to 90).toList, uno, Nil), Uno(-10)) === esperado)
  }

  test("Pequeña 4: retornar 20 de dos") {
    val dos = (81 to 100).toList
    val esperado = ((81 to 100).toList ++ (1 to 80).toList, Nil, Nil)
    assert(aplicarMovimiento(((1 to 80).toList, Nil, dos), Dos(-20)) === esperado)
  }

  test("Pequeña 5: movimiento Dos(0) no cambia el estado") {
    val estado = ((1 to 100).toList, (1 to 50).toList, (51 to 100).toList)
    assert(aplicarMovimiento(estado, Dos(0)) === estado)
  }

  // ==== PRUEBAS MEDIANAS (tamaño 500) ====
  test("Mediana 1: mover 100 a uno") {
    val principal = (1 to 500).toList
    val esperado = ((1 to 400).toList, (401 to 500).toList, Nil)
    assert(aplicarMovimiento((principal, Nil, Nil), Uno(100)) === esperado)
  }

  test("Mediana 2: mover 250 a dos") {
    val principal = (1 to 500).toList
    val esperado = ((1 to 250).toList, Nil, (251 to 500).toList)
    assert(aplicarMovimiento((principal, Nil, Nil), Dos(250)) === esperado)
  }

  test("Mediana 3: retornar 100 de uno") {
    val uno = (401 to 500).toList
    val esperado = ((401 to 500).toList ++ (1 to 400).toList, Nil, Nil)
    assert(aplicarMovimiento(((1 to 400).toList, uno, Nil), Uno(-100)) === esperado)
  }

  test("Mediana 4: retornar 250 de dos") {
    val dos = (251 to 500).toList
    val esperado = ((251 to 500).toList ++ (1 to 250).toList, Nil, Nil)
    assert(aplicarMovimiento(((1 to 250).toList, Nil, dos), Dos(-250)) === esperado)
  }

  test("Mediana 5: movimiento Uno(0) no cambia el estado") {
    val estado = ((1 to 500).toList, (1 to 250).toList, (251 to 500).toList)
    assert(aplicarMovimiento(estado, Uno(0)) === estado)
  }

  // ==== PRUEBAS GRANDES (tamaño 1000) ====
  test("Grande 1: mover 300 a uno") {
    val principal = (1 to 1000).toList
    val esperado = ((1 to 700).toList, (701 to 1000).toList, Nil)
    assert(aplicarMovimiento((principal, Nil, Nil), Uno(300)) === esperado)
  }

  test("Grande 2: mover 500 a dos") {
    val principal = (1 to 1000).toList
    val esperado = ((1 to 500).toList, Nil, (501 to 1000).toList)
    assert(aplicarMovimiento((principal, Nil, Nil), Dos(500)) === esperado)
  }

  test("Grande 3: retornar 300 de uno") {
    val uno = (701 to 1000).toList
    val esperado = ((701 to 1000).toList ++ (1 to 700).toList, Nil, Nil)
    assert(aplicarMovimiento(((1 to 700).toList, uno, Nil), Uno(-300)) === esperado)
  }

  test("Grande 4: retornar 500 de dos") {
    val dos = (501 to 1000).toList
    val esperado = ((501 to 1000).toList ++ (1 to 500).toList, Nil, Nil)
    assert(aplicarMovimiento(((1 to 500).toList, Nil, dos), Dos(-500)) === esperado)
  }

  test("Grande 5: movimiento Dos(0) no cambia el estado") {
    val estado = ((1 to 1000).toList, (1 to 500).toList, (501 to 1000).toList)
    assert(aplicarMovimiento(estado, Dos(0)) === estado)
  }
}
