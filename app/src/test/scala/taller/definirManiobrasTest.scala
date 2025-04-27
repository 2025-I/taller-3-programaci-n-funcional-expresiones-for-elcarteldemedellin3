package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class definirManiobrasTest extends AnyFunSuite {

  val obj = new definirManiobras()
  /*import obj._

  def aplicarMovimientos(e: Estado, maniobra: Maniobra): Estado = {
    maniobra.foldLeft(e)(aplicarMovimiento)
  }

  // === PRUEBAS DE JUGUETE (10 vagones) ===

  test("Juguete 1") {
    val t1 = (1 to 10).toList
    val t2 = (1 to 10).reverse.toList
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Juguete 2") {
    val t1 = (1 to 10).toList
    val t2 = (2 to 10).toList :+ 1
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Juguete 3") {
    val t1 = List(1,2,3,4,5,6,7,8,9,10)
    val t2 = List(5,6,7,8,9,10,1,2,3,4)
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Juguete 4") {
    val t1 = (10 to 1 by -1).toList
    val t2 = (1 to 10).toList
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Juguete 5") {
    val t1 = (1 to 10).toList
    val t2 = (1 to 10).toList
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  // === PRUEBAS PEQUEÑAS (100 vagones) ===

  test("Pequeña 1") {
    val t1 = (1 to 100).toList
    val t2 = (1 to 100).reverse.toList
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Pequeña 2") {
    val t1 = (1 to 100).toList
    val t2 = (51 to 100).toList ++ (1 to 50)
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Pequeña 3") {
    val t1 = (1 to 100).toList
    val t2 = (25 to 100).toList ++ (1 to 24)
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Pequeña 4") {
    val t1 = (1 to 100).toList
    val t2 = (100 to 1 by -1).toList
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Pequeña 5") {
    val t1 = (1 to 100).toList
    val t2 = (1 to 100).toList
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  // === PRUEBAS MEDIANAS (500 vagones) ===

  test("Mediana 1") {
    val t1 = (1 to 500).toList
    val t2 = (1 to 500).reverse.toList
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Mediana 2") {
    val t1 = (1 to 500).toList
    val t2 = (251 to 500).toList ++ (1 to 250)
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Mediana 3") {
    val t1 = (1 to 500).toList
    val t2 = (125 to 500).toList ++ (1 to 124)
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Mediana 4") {
    val t1 = (500 to 1 by -1).toList
    val t2 = (1 to 500).toList
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Mediana 5") {
    val t1 = (1 to 500).toList
    val t2 = (1 to 500).toList
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  // === PRUEBAS GRANDES (1000 vagones) ===

  test("Grande 1") {
    val t1 = (1 to 1000).toList
    val t2 = (1 to 1000).reverse.toList
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Grande 2") {
    val t1 = (1 to 1000).toList
    val t2 = (501 to 1000).toList ++ (1 to 500)
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Grande 3") {
    val t1 = (1 to 1000).toList
    val t2 = (250 to 1000).toList ++ (1 to 249)
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Grande 4") {
    val t1 = (1000 to 1 by -1).toList
    val t2 = (1 to 1000).toList
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }

  test("Grande 5") {
    val t1 = (1 to 1000).toList
    val t2 = (1 to 1000).toList
    val maniobra = definirManiobra(t1, t2)
    val finalEstado = aplicarMovimientos((t1, Nil, Nil), maniobra)
    assert(finalEstado._1 == t2)
  }*/
}
