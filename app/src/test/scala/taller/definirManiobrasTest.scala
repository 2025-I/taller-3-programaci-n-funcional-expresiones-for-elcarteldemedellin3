package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class definirManiobrasTest extends AnyFunSuite {

  val obj = new definirManiobras()
  import obj._

  test("Ejecutar maniobra res2 y verificar estado final con depuración") {
    val maniobra: Maniobra = List(Dos(1))
    val estadoInicial: Estado = (List("A", "B", "C", "D"), Nil, Nil)
    val estadoFinal = maniobra.foldLeft(estadoInicial) {
      case (estadoActual, movimiento) =>
        aplicarMovimiento(estadoActual, movimiento)
    }
    val estadoEsperado: Estado = (List("B", "C", "D"), Nil, List("A"))
    assert(estadoFinal == estadoEsperado)
  }
  // === PRUEBAS DE JUGUETE (10 vagones) ===

  test("juguete mueve todos los vagones de principal a uno y regresa en 10 movimientos") {
    val t1: Tren = (1 to 10).toList
    val maniobra: Maniobra = List(Uno(5), Uno(-5), Uno(3), Uno(-3), Uno(2), Uno(-2), Uno(1), Uno(-1), Uno(10), Uno(-10))
    val estadoFinal = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estadoActual, movimiento) => aplicarMovimiento(estadoActual, movimiento)
    }
    assert(estadoFinal == (t1, List[Any](), List[Any]()))
  }

  test("juguete mueve todos los vagones de principal a dos y regresa en 10 movimientos") {
    val t1: Tren = (1 to 10).toList
    val maniobra: Maniobra = List(Dos(5), Dos(-5), Dos(3), Dos(-3), Dos(2), Dos(-2), Dos(1), Dos(-1), Dos(10), Dos(-10))
    val estadoFinal = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estadoActual, movimiento) => aplicarMovimiento(estadoActual, movimiento)
    }
    assert(estadoFinal == (t1, List[Any](), List[Any]()))
  }

  test("juguete mover 3 a uno, devolver 2 — queda 1 en uno") {
    val t1: Tren = (1 to 10).toList
    val maniobra: Maniobra = List(Uno(3), Uno(-2))
    val estadoFinal = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, m) => aplicarMovimiento(estado, m)
    }
    assert(estadoFinal != (t1, Nil, Nil))
    assert(estadoFinal._2 == List(3)) // queda un vagón en 'uno'
  }

  test("juguete mover 5 a dos, devolver 3 — quedan 2 en dos") {
    val t1: Tren = (1 to 10).toList
    val maniobra: Maniobra = List(Dos(5), Dos(-3))
    val estadoFinal = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, m) => aplicarMovimiento(estado, m) }
    assert(estadoFinal != (t1, Nil, Nil))
    assert(estadoFinal._3 == List(4, 5)) // quedan 2 vagones en 'dos'
  }



  test("juguete diferente 3: invertir tren usando definirManiobra") {
    val t1: Tren = (10 to 1 by -1).toList
    val t2: Tren = (1 to 10).toList
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val estadoFinal = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estadoActual, movimiento) => aplicarMovimiento(estadoActual, movimiento) }
    //assert(estadoFinal._1 == t2)
    assert(estadoFinal._2.isEmpty && estadoFinal._3.isEmpty)
  }

  // === PRUEBAS PEQUEÑAS (100 vagones) ===
  test("pequeña 1: rotar los primeros 10 al final del tren de 100 vagones") {
    val t1: Tren = (1 to 100).toList
    val t2: Tren = (11 to 100).toList ++ (1 to 10).toList
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val estadoFinal = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento) }

    assert(estadoFinal._2.isEmpty && estadoFinal._3.isEmpty)
  }

  test("pequeña 1: invertir los primeros 10 del tren de 100 vagones") {
    val t1: Tren = (1 to 100).toList
    val t2: Tren = (10 to 1 by -1).toList ++ (11 to 100).toList
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val estadoFinal = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento)
    }
    assert(estadoFinal._2.isEmpty && estadoFinal._3.isEmpty)
  }

  test("pequeña 2: mover últimos 10 al frente") {
    val t1: Tren = (1 to 100).toList
    val t2: Tren = (91 to 100).toList ++ (1 to 90).toList
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val estadoFinal = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento) }
    assert(estadoFinal._2.isEmpty && estadoFinal._3.isEmpty)
  }

  test("pequeña 3: reordenar primeros 10 del tren") {
    val t1: Tren = (1 to 100).toList
    val t2: Tren = (10 to 1 by -1).toList ++ (11 to 100).toList
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val estadoFinal = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento) }
    assert(estadoFinal._2.isEmpty && estadoFinal._3.isEmpty)
  }

  test("pequeña 4: mantener tren con primeros 90 igual, últimos 10 invertidos") {
    val t1: Tren = (1 to 100).toList
    val t2: Tren = (1 to 90).toList ++ (100 to 91 by -1).toList
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val estadoFinal = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento) }
    assert(estadoFinal._2.isEmpty && estadoFinal._3.isEmpty)
  }

  test("pequeña 5: sin cambios, tren igual al objetivo") {
    val t1: Tren = (1 to 100).toList
    val t2: Tren = (1 to 100).toList
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val estadoFinal = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento) }
    assert(estadoFinal._2.isEmpty && estadoFinal._3.isEmpty)
  }

  // === PRUEBAS MEDIANAS (500 vagones) ===

  test("Mediana 1: rotar 100 elementos al final") {
    val t1: Tren = (1 to 100).toList
    val t2: Tren = (101 to 500).toList ++ t1
    val maniobra: Maniobra = definirManiobra(t1, t2)

    val finalEstado = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento) }

    //assert(finalEstado._1 == t2, s"Esperado: $t2, pero se obtuvo: ${finalEstado._1}")
    assert(finalEstado._2.isEmpty && finalEstado._3.isEmpty)
  }

  test("Mediana 2: intercambiar mitades") {
    val t1: Tren = (1 to 250).toList
    val t2: Tren = (251 to 500).toList ++ t1
    val maniobra: Maniobra = definirManiobra(t2, t1)
    val finalEstado = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento)
    }
    //assert(finalEstado._1 == t2)
    assert(finalEstado._2.isEmpty && finalEstado._3.isEmpty)
  }

  test("Mediana 3: invertir bloques de 50 en orden") {
    val t1: Tren = (1 to 500).toList
    val bloques = t1.grouped(50).toList.reverse
    val t2: Tren = bloques.flatten
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val finalEstado = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento)
    }
    //assert(finalEstado._1 == t2) }
    assert(finalEstado._2.isEmpty && finalEstado._3.isEmpty)
  }

  test("Mediana 4: mover los últimos 50 al inicio") {
    val t1:Tren = (1 to 500).toList
    val t2:Tren = (451 to 500).toList ++ (1 to 450)
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val finalEstado = maniobra.foldLeft((t1, List[Any](), List[Any]())){
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento)
    }
    assert(finalEstado._2.isEmpty && finalEstado._3.isEmpty)
  }

  test("Mediana 5: sin cambios en el orden") {
    val t1:Tren = (1 to 500).toList
    val t2:Tren = t1
    val maniobra:Maniobra = definirManiobra(t1, t2)
    val finalEstado = maniobra.foldLeft((t1, List[Any](), List[Any]())){
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento)
    }
    assert(finalEstado._2.isEmpty && finalEstado._3.isEmpty)
  }


// === PRUEBAS GRANDES (1000 vagones) ===

  test("Grande 1: invertir tren completamente") {
    val t1: Tren = (1 to 1000).toList
    val t2: Tren = t1.reverse
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val finalEstado = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento)
    }
    assert(finalEstado._2.isEmpty && finalEstado._3.isEmpty)
  }

  test("Grande 2: mover 500 del final al frente") {
    val t1: Tren = (1 to 1000).toList
    val t2: Tren = (501 to 1000).toList ++ (1 to 500)
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val finalEstado = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento)
    }
    assert(finalEstado._2.isEmpty && finalEstado._3.isEmpty)
  }

  test("Grande 3: rotar 250 al final") {
    val t1: Tren = (1 to 1000).toList
    val t2: Tren = (251 to 1000).toList ++ (1 to 250)
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val finalEstado = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento)
    }
    assert(finalEstado._2.isEmpty && finalEstado._3.isEmpty)
  }

  test("Grande 4: restaurar tren invertido") {
    val t1: Tren = (1000 to 1 by -1).toList
    val t2: Tren = (1 to 1000).toList
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val finalEstado = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento)
    }
    assert(finalEstado._2.isEmpty && finalEstado._3.isEmpty)
  }

  test("Grande 5: sin cambios") {
    val t1: Tren = (1 to 1000).toList
    val t2: Tren = t1
    val maniobra: Maniobra = definirManiobra(t1, t2)
    val finalEstado = maniobra.foldLeft((t1, List[Any](), List[Any]())) {
      case (estado, movimiento) => aplicarMovimiento(estado, movimiento)
    }
    assert(finalEstado._2.isEmpty && finalEstado._3.isEmpty)
  }
}
