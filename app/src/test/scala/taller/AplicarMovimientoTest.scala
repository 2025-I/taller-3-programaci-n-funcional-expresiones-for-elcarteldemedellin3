package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AplicarMovimientoTest extends AnyFunSuite {
  val obj = new AplicarMovimiento()
  import obj._

  // Generador de vagones (usando Char para simplificar las verificaciones)
  private def generarVagones(n: Int): Tren =
    List.tabulate(n)(i => ('a' + (i % 26)).toChar).asInstanceOf[Tren]

  /* ***********************
   * PRUEBAS DE JUGUETE (10)
   * ***********************/
  test("Prueba juguete 1: Movimiento Uno(2) mueve 2 últimos elementos a vía uno") {
    val vagones = generarVagones(10)
    val e0: Estado = (vagones, Nil, Nil)
    val e1 = aplicarMovimiento(e0, Uno(2))
    // Verificamos que se movieron los últimos 2 elementos a vía uno
    assert(e1._1 == vagones.dropRight(2))
    assert(e1._2 == vagones.takeRight(2))
    assert(e1._3.isEmpty)
  }

  test("Prueba juguete 2: Movimiento Dos(3) mueve 3 últimos elementos a vía dos") {
    val vagones = generarVagones(10)
    val e0: Estado = (vagones, Nil, Nil)
    val e1 = aplicarMovimiento(e0, Dos(3))
    // Verificamos que se movieron los últimos 3 elementos a vía dos
    assert(e1._1 == vagones.dropRight(3))
    assert(e1._2.isEmpty)
    assert(e1._3 == vagones.takeRight(3))
  }

  test("Prueba juguete 3: Movimientos negativos") {
    val e0: Estado = (Nil, List('a', 'b', 'c'), List('d', 'e', 'f'))
    val e1 = aplicarMovimiento(e0, Uno(-2))
    assert(e1._1.size == 2)  // Debería tomar 2 elementos de vía uno
    assert(e1._2.size == 1)  // Debería quedar 1 en vía uno
    assert(e1._3.size == 3)  // Vía dos intacta

    val e2 = aplicarMovimiento(e1, Dos(-1))
    assert(e2._1.size == 3)  // 2 anteriores + 1 de vía dos
    assert(e2._2.size == 1)  // Vía uno igual
    assert(e2._3.size == 2)  // Vía dos reducida en 1
  }

  test("Prueba juguete 4: Movimiento cero no cambia estado") {
    val e0: Estado = (List('a', 'b'), List('c'), List('d'))
    val e1 = aplicarMovimiento(e0, Uno(0))
    assert(e1 == e0)

    val e2 = aplicarMovimiento(e0, Dos(0))
    assert(e2 == e0)
  }

  test("Prueba juguete 5: Conservación de elementos en secuencia") {
    val vagones = generarVagones(10)
    val e0: Estado = (vagones, Nil, Nil)
    val movimientos = List(Uno(2), Dos(3), Uno(-1), Dos(-2))
    val estadoFinal = movimientos.foldLeft(e0)((e, m) => aplicarMovimiento(e, m))
    assert(estadoFinal._1.size + estadoFinal._2.size + estadoFinal._3.size == 10)
  }

  /* **********************
   * PRUEBAS PEQUEÑAS (100)
   * **********************/
  test("Prueba pequeña 1: Movimiento completo a vía uno") {
    val vagones = generarVagones(100)
    val e0: Estado = (vagones, Nil, Nil)
    val e1 = aplicarMovimiento(e0, Uno(100))
    assert(e1._1.isEmpty)
    assert(e1._2.size == 100)
    assert(e1._3.isEmpty)
  }

  test("Prueba pequeña 2: Movimiento parcial a vía dos") {
    val vagones = generarVagones(100)
    val e0: Estado = (vagones, Nil, Nil)
    val e1 = aplicarMovimiento(e0, Dos(37))
    assert(e1._1.size == 63)
    assert(e1._2.isEmpty)
    assert(e1._3.size == 37)
  }

  test("Prueba pequeña 3: Movimiento negativo desde vías") {
    val e0: Estado = (Nil, generarVagones(60), generarVagones(40))
    val e1 = aplicarMovimiento(e0, Uno(-30))
    assert(e1._1.size == 30)
    assert(e1._2.size == 30)
    assert(e1._3.size == 40)
  }

  test("Prueba pequeña 4: Secuencia alternada") {
    val e0: Estado = (generarVagones(100), Nil, Nil)
    val movimientos = List.tabulate(20)(i => if (i % 2 == 0) Uno(5) else Dos(5))
    val estados = movimientos.scanLeft(e0)((e, m) => aplicarMovimiento(e, m))
    assert(estados.last._1.size + estados.last._2.size + estados.last._3.size == 100)
  }

  test("Prueba pequeña 5: Movimiento excedente") {
    val e0: Estado = (generarVagones(50), generarVagones(30), generarVagones(20))
    val e1 = aplicarMovimiento(e0, Uno(-100))
    assert(e1._1.size == 80)
    assert(e1._2.isEmpty)
    assert(e1._3.size == 20)
  }

  /* ***********************
   * PRUEBAS MEDIANAS (500)
   * ***********************/
  test("Prueba mediana 1: Movimiento completo a vía dos") {
    val vagones = generarVagones(500)
    val e0: Estado = (vagones, Nil, Nil)
    val e1 = aplicarMovimiento(e0, Dos(500))
    assert(e1._1.isEmpty)
    assert(e1._2.isEmpty)
    assert(e1._3.size == 500)
  }

  test("Prueba mediana 2: Movimiento exacto") {
    val vagones = generarVagones(500)
    val e0: Estado = (vagones, Nil, Nil)
    val e1 = aplicarMovimiento(e0, Uno(250))
    assert(e1._1.size == 250)
    assert(e1._2.size == 250)
    assert(e1._3.isEmpty)
  }

  test("Prueba mediana 3: Movimiento negativo exacto") {
    val e0: Estado = (Nil, generarVagones(300), generarVagones(200))
    val e1 = aplicarMovimiento(e0, Dos(-200))
    assert(e1._1.size == 200)
    assert(e1._2.size == 300)
    assert(e1._3.isEmpty)
  }

  test("Prueba mediana 4: Secuencia aleatoria") {
    val e0: Estado = (generarVagones(500), Nil, Nil)
    val movimientos = List.fill(50)(if (math.random() > 0.5) Uno(10) else Dos(10))
    val estados = movimientos.scanLeft(e0)((e, m) => aplicarMovimiento(e, m))
    assert(estados.last._1.size + estados.last._2.size + estados.last._3.size == 500)
  }

  test("Prueba mediana 5: Conservación de elementos") {
    val vagones = generarVagones(500)
    val e0: Estado = (vagones, Nil, Nil)
    val e1 = aplicarMovimiento(e0, Uno(100))
    val e2 = aplicarMovimiento(e1, Dos(150))
    val e3 = aplicarMovimiento(e2, Uno(-50))
    val e4 = aplicarMovimiento(e3, Dos(-75))
    assert(e4._1.size + e4._2.size + e4._3.size == 500)
  }

  /* **********************
   * PRUEBAS GRANDES (1000)
   * **********************/
  test("Prueba grande 1: Movimiento completo") {
    val vagones = generarVagones(1000)
    val e0: Estado = (vagones, Nil, Nil)
    val e1 = aplicarMovimiento(e0, Uno(1000))
    assert(e1._1.isEmpty)
    assert(e1._2.size == 1000)
    assert(e1._3.isEmpty)
  }

  test("Prueba grande 2: Movimiento parcial") {
    val vagones = generarVagones(1000)
    val e0: Estado = (vagones, Nil, Nil)
    val e1 = aplicarMovimiento(e0, Dos(333))
    val e2 = aplicarMovimiento(e1, Uno(334))
    assert(e2._1.size == 333)
    assert(e2._2.size == 334)
    assert(e2._3.size == 333)
  }

  test("Prueba grande 3: Movimiento negativo grande") {
    val e0: Estado = (Nil, generarVagones(700), generarVagones(300))
    val e1 = aplicarMovimiento(e0, Uno(-500))
    assert(e1._1.size == 500)
    assert(e1._2.size == 200)
    assert(e1._3.size == 300)
  }

  test("Prueba grande 4: Estrés con movimientos alternos") {
    val e0: Estado = (generarVagones(1000), Nil, Nil)
    val movimientos = List.tabulate(100)(i => if (i % 3 == 0) Uno(7) else Dos(11))
    val estados = movimientos.scanLeft(e0)((e, m) => aplicarMovimiento(e, m))
    assert(estados.last._1.size + estados.last._2.size + estados.last._3.size == 1000)
  }

  test("Prueba grande 5: Verificación final") {
    val vagones = generarVagones(1000)
    val e0: Estado = (vagones, Nil, Nil)
    val movimientos = List(Uno(300), Dos(400), Uno(-100), Dos(-200), Uno(500))
    val estados = movimientos.scanLeft(e0)((e, m) => aplicarMovimiento(e, m))
    assert(estados.last._1.size + estados.last._2.size + estados.last._3.size == 1000)
  }
}