# Informe de Proceso: Implementacion de definirManiobras

## 1. Introduccion 

La función **definirManiobra** genera una lista de movimientos para transformar un tren inicial (**t1**) en un tren objetivo (**t2**). Este informe detalla el proceso de ejecución y los casos de prueba utilizados para validar su comportamiento.

## 2. Implementacion de DefinirManiobra

1. Se inicializa el estado con **t1** en la vía principal y las otras vías vacías.
2. Para cada elemento en **t2:**
   - Se busca su posicion en la vía principal. 
   - Se generan movimientos para colocarlo en la posición correcta.
   - Se actualiza el estado aplicando los movimientos generados.
3. La lista acumulada de movimientos se invierte al final para mantener el orden correcto.

### Ejemplo de ejecución

Entrada

```scala
t1: List("A", "B", "C", "D")
t2: List("D", "C", "B", "A")
```

Ejecución

1. Se busca "D" en t1 y se generan movimientos para colocarlo en la posición correcta.
2. Se actualiza el estado y se continúa con "C", "B" y "A".
3. La lista de movimientos acumulada se invierte al final.

Salida

```scala
Maniobra: List(Uno(3), Uno(-3), Dos(1), Dos(-1), ...)
```

## 3. Casos de prueba

### Organización general 
- Se utilizaron **ScalaTest** y **JUnit** 
- Se probaron trenes de diferentes tamaños y configuraciones (10, 100, 500, 1000 vagones).

### Categorías de pruebas
1. **Pruebas de juguete:** Transformaciones simples con pocos vagones.
2. **Pruebas pequeñas:** Reordenamientos parciales en trenes de 100 vagones.
3. **Pruebas medianas:** Transformaciones complejas en trenes de 500 vagones.
4. **Pruebas grandes:** Transformaciones masivas en trenes de 1000 vagones.

### Tipos de transformaciones probados
- Invertir el tren completo.
- Rotar bloques de vagones.
- Reordenar bloques específicos.
- Mantener el tren sin cambios.

## 4. Conclusiones

La función **definirManiobra** ha sido correctamente implementada y probada exhaustivamente. Su diseño funcional garantiza claridad, corrección y cumplimiento de los requisitos del taller.
