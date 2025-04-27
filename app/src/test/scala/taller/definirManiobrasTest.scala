package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import taller.definirManiobras

@RunWith(classOf[JUnitRunner])
class definirManiobrasTest extends AnyFunSuite {

  val objdefinirManiobras = new definirManiobras()

  test("Revertir tren de 10 vagones devuelve la maniobra esperada") {
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
  }
  //pruebas pequeñas
  test("Prueba P1: Revertir tren de 100 vagones") {
    // Arrange
    val t1 = (1 to 100).toList
    val t2 = t1.reverse
    val expected = (1 to 100).toList.flatMap { i =>
      List(objdefinirManiobras.Uno(i), objdefinirManiobras.Dos(-i))
    }
    // Act
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    // Assert
    assert(result == expected)
  }

  test("Prueba P2: Mover un vagón a la estación uno") {
    // Arrange
    val t1 = (1 to 100).toList
    val t2 = (2 to 100).toList :+ 1 // Mover el primer vagón al final
    val expected = List(objdefinirManiobras.Uno(1), objdefinirManiobras.Uno(-1))
    // Act
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    // Assert
    assert(result == expected)
  }

  test("Prueba P3: Mover dos vagones a la estación uno") {
    // Arrange
    val t1 = (1 to 100).toList
    val t2 = (3 to 100).toList :+ 1 :+ 2 // Mover los dos primeros vagones al final
    val expected = List(objdefinirManiobras.Uno(2), objdefinirManiobras.Uno(-2))
    // Act
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    // Assert
    assert(result == expected)
  }

  test("Prueba P4: Mover vagones intermedios") {
    // Arrange
    val t1 = (1 to 100).toList
    val t2 = (4 to 100).toList ++ List(1, 2, 3) // Mover los tres primeros vagones al final
    val expected = List(objdefinirManiobras.Uno(3), objdefinirManiobras.Uno(-3))
    // Act
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    // Assert
    assert(result == expected)
  }

  test("Prueba P5: Sin movimiento necesario") {
    // Arrange
    val t1 = (1 to 100).toList
    val t2 = (1 to 100).toList // No se requiere movimiento
    val expected = List() // No se requiere movimiento
    // Act
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    // Assert
    assert(result == expected)
  }

  //Pruebas Medianas
  test("Prueba M1: Mover los primeros 100 vagones al final") {
    val t1 = (1 to 500).toList
    val t2 = (101 to 500).toList ++ (1 to 100) // Mover los primeros 100 vagones al final
    val expected = (1 to 100).flatMap(i => List(objdefinirManiobras.Uno(i), objdefinirManiobras.Uno(-i)))
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }

  test("Prueba M2: Mover vagones del medio a la estación uno") {
    val t1 = (1 to 500).toList
    val t2 = (1 to 250).toList ++ (251 to 500).toList // Mover los vagones del medio al final
    val expected = (251 to 500).flatMap(i => List(objdefinirManiobras.Uno(i), objdefinirManiobras.Uno(-i)))
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }

  test("Prueba M3: Mover los últimos 50 vagones al principio") {
    val t1 = (1 to 500).toList
    val t2 = (451 to 500).toList ++ (1 to 450) // Mover los últimos 50 vagones al principio
    val expected = (451 to 500).flatMap(i => List(objdefinirManiobras.Uno(i), objdefinirManiobras.Uno(-i)))
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }

  test("Prueba M4: Mover vagones en un patrón alterno") {
    val t1 = (1 to 500).toList
    val t2 = (1 to 500).toList.reverse // Invertir el tren
    val expected = (1 to 500).flatMap(i => List(objdefinirManiobras.Uno(i), objdefinirManiobras.Dos(-i)))
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }

  test("Prueba M5: Mover vagones de forma aleatoria") {
    val t1 = (1 to 500).toList
    val t2 = List(100, 200, 300, 400, 500) ++ (1 to 99) ++ (101 to 199) ++ (201 to 299) ++ (301 to 399) ++ (401 to 499) // Mover vagones en un orden específico
    val expected = List(
      objdefinirManiobras.Uno(1), objdefinirManiobras.Uno(2), objdefinirManiobras.Uno(3), objdefinirManiobras.Uno(4), objdefinirManiobras.Uno(5),
      // Agregar el resto de los movimientos esperados según la lógica de tu función
    )
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }
  //Pruebas Grandes
  test("Prueba G1: Revertir tren de 1000 vagones") {
    val t1 = (1 to 1000).toList
    val t2 = t1.reverse
    val expected = (1 to 1000).toList.flatMap { i =>
      List(objdefinirManiobras.Uno(i), objdefinirManiobras.Dos(-i))
    }
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }

  test("Prueba G2: Mover los primeros 250 vagones al final") {
    val t1 = (1 to 1000).toList
    val t2 = (251 to 1000).toList ++ (1 to 250) // Mover los primeros 250 vagones al final
    val expected = (1 to 250).flatMap(i => List(objdefinirManiobras.Uno(i), objdefinirManiobras.Uno(-i)))
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }

  test("Prueba G3: Mover los vagones en un patrón alterno") {
    val t1 = (1 to 1000).toList
    val t2 = (1 to 1000).toList.reverse // Invertir el tren
    val expected = (1 to 1000).flatMap(i => List(objdefinirManiobras.Uno(i), objdefinirManiobras.Dos(-i)))
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }

  test("Prueba G4: Mover los últimos 100 vagones al principio") {
    val t1 = (1 to 1000).toList
    val t2 = (901 to 1000).toList ++ (1 to 900) // Mover los últimos 100 vagones al principio
    val expected = (901 to 1000).flatMap(i => List(objdefinirManiobras.Uno(i), objdefinirManiobras.Uno(-i)))
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }

  test("Prueba G5: Mover vagones en un orden específico") {
    val t1 = (1 to 1000).toList
    val t2 = List(500, 600, 700, 800, 900) ++ (1 to 499) ++ (501 to 599) ++ (601 to 699) ++ (701 to 799) ++ (801 to 899) ++ (901 to 1000) // Mover vagones en un orden específico
    val expected = List(
      objdefinirManiobras.Uno(1), objdefinirManiobras.Uno(2), objdefinirManiobras.Uno(3), objdefinirManiobras.Uno(4), objdefinirManiobras.Uno(5),
      // Agregar el resto de los movimientos esperados según la lógica de tu función
    )
    val result = objdefinirManiobras.definirManiobra(t1, t2)
    assert(result == expected)
  }
}
