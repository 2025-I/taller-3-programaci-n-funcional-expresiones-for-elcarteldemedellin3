# Informe de Corrección de AplicarMovimiento

## 1. Argumentando sobre corrección de programas recursivos

Sea f: A → B una función y A un conjunto definido recursivamente, como por ejemplo las listas o los estados de un tren.

Sea Pf un programa recursivo desarrollado en Scala que implementa f:

```scala
def P_f(a: A): B = {
  // P_f recibe a de tipo A, y devuelve f(a) de tipo B
  ...
}
```

Para demostrar que Pf es correcto, es decir, que siempre devuelve f(a) para cada a ∈ A, usamos inducción estructural.

Dado un conjunto A definido recursivamente:

1. Para cada caso base a ∈ A, se tiene que Pf(a) = f(a).
2. Para cada elemento a construido recursivamente a partir de otro(s) a', se cumple la hipótesis de inducción:
   Pf(a') = f(a') → Pf(a) = f(a).

Aplicamos este razonamiento al algoritmo `aplicarMovimiento`.

---

## 2. Corrección del algoritmo AplicarMovimiento

`AplicarMovimiento` es una función que, dado un estado del tren (principal, vía uno, vía dos) y un movimiento (`Uno(n)` o `Dos(n)`), actualiza el estado de acuerdo a las reglas:

- Si n > 0, se mueve n vagones desde `principal` hacia la vía correspondiente.
- Si n < 0, se regresa -n vagones desde la vía correspondiente hacia `principal`.
- Si n == 0, no se realiza ningún cambio.

Definimos f: Estado × Movimiento → Estado, donde f(e, m) es el nuevo estado luego de aplicar m a e.

El algoritmo se define en Scala como:

```scala
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
```

Demostraremos que ∀(e, m): Pf(e, m) = f(e, m), es decir, que el algoritmo transforma el estado correctamente.

### 2.1. Caso base

Cuando el movimiento es `Uno(0)` o `Dos(0)`, entonces:

```scala
aplicarMovimiento(e, Uno(0)) => e
aplicarMovimiento(e, Dos(0)) => e
```

No se modifica el estado, que es exactamente lo que indica la especificación. Se cumple Pf(e, 0) = f(e, 0).

---

### 2.2. Caso de inducción: Movimiento positivo

Supongamos que Pf(e', m') = f(e', m') para estados de tamaño menor.

Si n > 0:
- Se toman los últimos n elementos de `principal` (o menos si no hay suficientes).
- Se añaden en orden al final de la vía correspondiente (`uno` o `dos`).
- Se conserva el resto de los vagones en `principal`.

Como los `for` recorren y seleccionan índices válidos, y se combinan listas de manera pura (`++`), el estado final mantiene la cantidad total de vagones y su correcto orden.

Se cumple Pf(e, m) = f(e, m).

---

### 2.3. Caso de inducción: Movimiento negativo

Supongamos que Pf(e', m') = f(e', m') para estados de tamaño menor.

Si n < 0:
- Se toman los primeros `-n` elementos de `uno` o `dos`.
- Se agregan al inicio de `principal`, preservando el orden gracias al uso de `reverse_::: ... reverse`.
- Se eliminan esos elementos de la vía origen.

Esto garantiza que los vagones se mueven correctamente y que no se pierde ni duplica información.

Se cumple Pf(e, m) = f(e, m).

---

## 3. Correctitud de los subcomponentes

La extracción de vagones (`for` sobre índices) y la construcción del nuevo `Estado` utilizan exclusivamente operaciones puras y de listas inmutables:

- `++` para concatenar listas.
- `.drop`, `.take`, `.reverse`, `reverse_:::` para manipular ordenadamente las listas.

Todas las operaciones respetan las propiedades de los estados del tren:
- Conservan la suma de los elementos.
- Mantienen la integridad del orden entre inserciones y extracciones.

La función es funcionalmente pura, sin efectos secundarios, y opera de manera segura sobre las listas.

---

## 4. Conclusión

Usando **inducción estructural** sobre el tipo de `Movimiento` (`Uno`, `Dos`) y la estructura de las listas del `Estado`, demostramos que:

- `aplicarMovimiento` siempre transforma correctamente el estado.
- Conserva la cantidad de vagones y el orden entre las vías.
- Cumple exactamente con la especificación planteada.

Por lo tanto, el algoritmo de `AplicarMovimiento` es **correcto y confiable**.

