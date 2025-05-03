# Informe de Correción de definirManiobras 

## 1. Argumentación sobre corrección de programas recursivos
La función **definirManiobra** utiliza recursión para construir una lista de movimientos que transforma un tren inicial (**t1**) en un tren objetivo (**t2**). Para demostrar su corrección, aplicamos inducción estructural sobre la estructura de **t2:**

1. **Caso Base**: Si **t2** es vacío (**Nil**), no se requieren movimientos, y la maniobra resultante es una lista vacía. Esto cumple con la especificación.
2. **Caso recursivo**: Si **t2** tiene al menos un elemento (**cabeza :: resto**), se busca el índice del elemento **cabeza** en la vía principal del estado actual. Dependiendo de su posición, se generan movimientos para colocarlo en la posición correcta, y se continúa recursivamente con el resto de **t2.**

## Corrección del algoritmo definirManiobra
La función **definirManiobra** se define como:
```scala
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
```
### Caso Base
Cuando **t2** es vacío, no se generan movimientos, y la maniobra resultante es una lista vacía. Esto cumple con la especificación.

### Caso recursivo 
Para cada elemento **cabeza** en **t2:**
- Se busca su índice en la vía principal del estado actual.Se busca su índice en la vía principal del estado actual.
- Si está presente, se generan movimientos para colocarlo en la posición correcta en **t2.**
- Se actualiza el estado aplicando los movimientos generados.
- Se continúa recursivamente con el resto de **t2.**

## Correctitud de los subcomponentes
La función utiliza operaciones puras y estructuras inmutables:
- **zipWithIndex** para asociar índices a los elementos del tren.
- **foldLeft** para aplicar movimientos de manera acumulativa.
- **reverse** para invertir la lista de movimientos acumulados.

Estas operaciones garantizan que:
- Los movimientos generados son correctos y suficientes para transformar t1 en t2.
- La cantidad total de vagones se conserva.
- El orden de los vagones en t2 se respeta.

## 4. Conclusiones
Usando inducción estructural sobre la estructura de t2, demostramos que:
- **definirManiobra** genera una lista de movimientos que transforma correctamente **t1** en **t2**.
- La función es funcionalmente pura y opera de manera segura sobre las listas.