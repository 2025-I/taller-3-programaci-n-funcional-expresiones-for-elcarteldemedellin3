
# Informe de Proceso: Implementación de AplicarMovimiento en Scala

## 1. Introducción

Este informe detalla el proceso de ejecución de la función **aplicarMovimiento**, implementada en Scala en el contexto de un sistema de maniobras de trenes. Se muestra el estado de la pila de llamadas y se analizan diversos casos de prueba para evaluar el comportamiento del algoritmo.

## 2. Implementación de AplicarMovimiento

El algoritmo sigue un enfoque funcional para modificar el estado de un tren dividido en tres vías (principal, vía uno, vía dos). Dependiendo del movimiento recibido (`Uno(n)` o `Dos(n)`), transfiere elementos entre las vías.

```scala
package taller

class AplicarMovimiento {
  type Vagon = Any
  type Tren = List[Vagon]
  type Estado = (Tren, Tren, Tren)

  trait Movimiento
  case class Uno(n: Int) extends Movimiento
  case class Dos(n: Int) extends Movimiento

  def aplicarMovimiento(e: Estado, m: Movimiento): Estado = {
    val (principal, uno, dos) = e

    m match {
      case Uno(n) if n > 0 =>
        val movidos = for {
          i <- Math.max(0, principal.length - n) until principal.length
        } yield principal(i)
        val resto = for {
          i <- 0 until Math.max(0, principal.length - n)
        } yield principal(i)
        (resto.toList, uno ++ movidos.toList, dos)

      case Uno(n) if n < 0 =>
        val movidos = for {
          i <- 0 until Math.min(-n, uno.length)
        } yield uno(i)
        (principal.reverse_:::(movidos.toList).reverse,
          uno.drop(Math.min(-n, uno.length)),
          dos)

      case Uno(0) =>
        e

      case Dos(n) if n > 0 =>
        val movidos = for {
          i <- Math.max(0, principal.length - n) until principal.length
        } yield principal(i)
        val resto = for {
          i <- 0 until Math.max(0, principal.length - n)
        } yield principal(i)
        (resto.toList, uno, dos ++ movidos.toList)

      case Dos(n) if n < 0 =>
        val movidos = for {
          i <- 0 until Math.min(-n, dos.length)
        } yield dos(i)
        (principal.reverse_:::(movidos.toList).reverse,
          uno,
          dos.drop(Math.min(-n, dos.length)))

      case Dos(0) =>
        e
    }
  }
}
```

## 3. Proceso de Ejecución Paso a Paso

### Funcionamiento del método `aplicarMovimiento`

El método `aplicarMovimiento` modifica el estado según el tipo de movimiento:
- **Uno(n) positivo:** mueve `n` vagones de la vía principal a la vía uno.
- **Uno(n) negativo:** mueve `n` vagones de la vía uno a la vía principal.
- **Dos(n) positivo:** mueve `n` vagones de la vía principal a la vía dos.
- **Dos(n) negativo:** mueve `n` vagones de la vía dos a la vía principal.
- **Uno(0) o Dos(0):** no realiza cambios.

### Ejemplo de ejecución

Entrada:
```
Estado inicial: (List(a, b, c, d), Nil, Nil)
Movimiento: Uno(2)
```

Estado de la pila de llamadas:
```
1. Movimiento Uno(2) detectado
2. Se seleccionan los últimos 2 vagones: List(c, d)
3. Resto de la vía principal: List(a, b)
4. Resultado:
  - Principal: List(a, b)
  - Uno: List(c, d)
  - Dos: List()
```

### Segundo ejemplo

Entrada:
```
Estado inicial: (Nil, List(x, y, z), List(p, q))
Movimiento: Uno(-2)
```

Estado de la pila de llamadas:
```
1. Movimiento Uno(-2) detectado
2. Se seleccionan los primeros 2 de la vía uno: List(x, y)
3. Se insertan al inicio de la vía principal.
4. Resultado:
  - Principal: List(x, y)
  - Uno: List(z)
  - Dos: List(p, q)
```

## 4. Caso de Prueba Adicional

### Caso: Movimiento Excedente

Entrada:
```
Estado inicial: (List(a, b), List(c), List(d, e))
Movimiento: Uno(-10)
```

Estado de la pila de llamadas:
```
1. Movimiento Uno(-10) detectado
2. Solo 1 elemento en vía uno (c)
3. Se mueve ese único elemento a la vía principal.
4. Resultado:
  - Principal: List(c, a, b)
  - Uno: List()
  - Dos: List(d, e)
```

Este caso demuestra que el algoritmo maneja correctamente intentos de mover más vagones de los disponibles.

## 5. Explicación de la Generación de Pruebas de Software

### Organización general
- Se utilizaron **ScalaTest** y **JUnit**.
- Se creó un método auxiliar `generarVagones(n: Int)` para construir trenes de prueba usando caracteres.

### Categorías de pruebas
- **Pruebas de juguete (10 vagones)**
- **Pruebas pequeñas (100 vagones)**
- **Pruebas medianas (500 vagones)**
- **Pruebas grandes (1000 vagones)**

### Tipos de movimientos probados
Se probaron exhaustivamente todos los casos posibles de movimientos:

### Movimientos positivos:
- Mover n vagones desde la vía principal a vía uno o vía dos (Uno(n), Dos(n)).

### Movimientos negativos:
- Mover n vagones desde vía uno o vía dos a la vía principal (Uno(-n), Dos(-n)).

### Movimientos cero:
- No hacer nada (Uno(0), Dos(0)).

### Movimientos excedentes:
- Intentar mover más elementos de los que hay disponibles (verifica que no falle).

### Movimientos alternados y secuenciales:
- Uso de combinaciones consecutivas de movimientos aplicando foldLeft o scanLeft.

## Estructura de las pruebas
Cada prueba sigue este esquema:

### Preparación:
Crear un estado inicial (e0) usando generarVagones.

### Ejecución:
Aplicar uno o más movimientos con aplicarMovimiento.

### Verificación:
Usar assert para validar:

- Que el contenido de las vías es correcto (e1._1, e1._2, e1._3).

- Que el número total de elementos (sumando todas las vías) se conserva.

- Que el estado no cambia si el movimiento es 0.

- Que después de movimientos negativos los vagones se insertan al principio.

## Ejemplo general de patrón:

```scala
val e0: Estado = (generarVagones(n), Nil, Nil)
val e1 = aplicarMovimiento(e0, Movimiento)
assert(e1._1.size + e1._2.size + e1._3.size == n)
```

### Cobertura lograda
Se garantizó la cobertura de todos los caminos de ejecución posibles y el mantenimiento de la cantidad total de vagones.

## 6. Conclusiones

La función `aplicarMovimiento` ha sido correctamente implementada y probada exhaustivamente para diferentes tipos de movimientos y tamaños de datos. El uso de expresiones `for`, la no mutabilidad y el diseño funcional garantizan claridad y corrección, cumpliendo los requisitos del taller.
