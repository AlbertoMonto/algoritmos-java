# Algoritmos en Java

Dos programas de línea de comandos con modo traza (`-t`) y ayuda (`-h`) que leen la entrada de un fichero y escriben el resultado en otro.

## asignacion-tareas/
Asigna n agentes a n tareas minimizando el coste total, a partir de una matriz de costes, con un enfoque de ramificación y poda basado en cotas.

    cd asignacion-tareas
    javac *.java
    java Main -t entrada.txt salida.txt

## torres-hanoi/
Resuelve las Torres de Hanoi de forma recursiva entre dos postes cualesquiera y escribe la secuencia de movimientos. La entrada es `origen destino n`.

    cd torres-hanoi
    javac *.java
    java Main -t entrada.txt salida.txt

## Tecnologías
Java · E/S de ficheros · análisis de coste temporal y espacial
