package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import taller.definirManiobras

@RunWith(classOf[JUnitRunner])
class definirManiobrasTest extends AnyFunSuite {

  val objdefinirManiobras = new definirManiobras()

  /*test("Revertir tren de 10 vagones devuelve la maniobra esperada") {
    // Arrange
    val t1 = List('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j')
    val t2 = List('j', 'i', 'h', 'g', 'f', 'e', 'd', 'c', 'b', 'a')
    val expected = List(
      objdefinirManiobras.Uno(10), objdefinirManiobras.Uno(-9), objdefinirManiobras.Dos(9),
      objdefinirManiobras.Uno(-8), objdefinirManiobras.Dos(8), objdefinirManiobras.Uno(-7),
      objdefinirManiobras.Dos(7), objdefinirManiobras.Uno(-6), objdefinirManiobras.Dos(6),
      objdefinirManiobras.Uno(-5), objdefinirManiobras.Dos(5), objdefinirManiobras.Uno(-4),
      objdefinirManiobras.Dos(4), objdefinirManiobras.Uno(-3), objdefinirManiobras.Dos(3),
      objdefinirManiobras.Uno(-2), objdefinirManiobras.Dos(2), objdefinirManiobras.Uno(-1),
      objdefinirManiobras.Dos(1)
    )
    // Act
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    // Assert
    assert(result == expected)
  }

  test("Prueba 2: Mover un vagón a la estación uno") {
    val t1 = List('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j')
    val t2 = List('b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'a')
    val expected = List(objdefinirManiobras.Uno(1), objdefinirManiobras.Uno(-1))
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }

  test("Prueba 3: Mover dos vagones a la estación uno") {
    val t1 = List('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j')
    val t2 = List('c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'a', 'b')
    val expected = List(objdefinirManiobras.Uno(2), objdefinirManiobras.Uno(-2))
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }

  test("Prueba 4: Mover vagones intermedios") {
    val t1 = List('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j')
    val t2 = List('d', 'e', 'f', 'g', 'h', 'i', 'j', 'a', 'b', 'c')
    val expected = List(objdefinirManiobras.Uno(3), objdefinirManiobras.Uno(-3))
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }

  test("Prueba 5: Sin movimiento necesario") {
    val t1 = List('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j')
    val t2 = List('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j')
    val expected = List() // No se requiere movimiento
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }*/
}
