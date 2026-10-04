import java.util.ArrayList;
import java.util.Arrays;

public class Asignacion {

    private int[][] costes; // Matriz que representa los costos de asignación
    private boolean trazas; // Activa/desactiva las trazas de ejecución

    // Constructor que inicializa la matriz de costos y la opción de trazas
    public Asignacion(int[][] tabla, boolean trazas) {
        this.costes = tabla;
        this.trazas = trazas;
    }

    public int[] asignaTareas() {
        int resultado = 0;
        int[] nodoSolucion = generaSolucionInicial(); // Generar solución inicial (por defecto)
        int[] solucionFinal = new int[costes.length]; // Array para almacenar la solución final
        ArrayList<Integer> agentesDisponibles = inicializaAgentes(); // Lista de agentes no asignados
        int tareaActual = 0; // Índice para las tareas

        // Mientras queden agentes disponibles
        while (!agentesDisponibles.isEmpty()) {
            if (trazas) {
                System.out.println("Paso " + (tareaActual + 1) + ":");
                System.out.println("Agentes disponibles: " + ajustaIndices(agentesDisponibles));
            }

            // Crear un array para almacenar las cotas de cada agente disponible
            int[] cotas = new int[agentesDisponibles.size()];
            for (int i = 0; i < cotas.length; i++) {
                nodoSolucion[tareaActual] = costes[agentesDisponibles.get(i)][tareaActual]; // Simulación de asignación
                cotas[i] = calculaCota(nodoSolucion); // Calcular la cota asociada

                if (trazas) {
                    System.out.println("Cota calculada para agente " + (agentesDisponibles.get(i) + 1) + ": " + cotas[i]);
                    resultado = cotas[i];
                }
            }

            // Seleccionar el agente con la cota más baja
            int mejorAgenteIndex = getIndiceMejorAgente(cotas);
            int mejorAgente = agentesDisponibles.get(mejorAgenteIndex);

            nodoSolucion[tareaActual] = costes[mejorAgente][tareaActual]; // Actualizar nodo solución
            solucionFinal[tareaActual] = mejorAgente; // Asignar el agente a la tarea actual

            if (trazas) {
                System.out.println("Agente seleccionado: " + (mejorAgente + 1) + " para tarea " + (tareaActual + 1));
                System.out.println("Nodo actual: " + Arrays.toString(nodoSolucion));
            }

            agentesDisponibles.remove(mejorAgenteIndex); // Eliminar agente asignado de la lista
            tareaActual++; // Avanzar a la siguiente tarea
        }
        if (trazas) {
            System.out.println("Resultado final: " + resultado);
        }
        return solucionFinal; // Devolver la solución final
    }

    // Genera una solución inicial simple basada en la diagonal principal
    private int[] generaSolucionInicial() {
        int[] solucion = new int[costes.length];
        for (int i = 0; i < solucion.length; i++) {
            solucion[i] = costes[i][i];
        }
        return solucion;
    }

    // Inicializa la lista de agentes disponibles
    private ArrayList<Integer> inicializaAgentes() {
        ArrayList<Integer> solucion = new ArrayList<>();
        for (int i = 0; i < costes.length; i++) {
            solucion.add(i);
        }
        return solucion;
    }

    // Calcula la cota asociada a un nodo (suma de costos actuales)
    private int calculaCota(int[] nodo) {
        int suma = 0;
        for (int valor : nodo) {
            suma += valor;
        }
        return suma;
    }

    // Determina la posición del agente con la mejor cota (menor valor)
    private int getIndiceMejorAgente(int[] cotas) {
        int posicion = 0;
        int mejorValor = cotas[0];
        for (int i = 1; i < cotas.length; i++) {
            if (cotas[i] < mejorValor) {
                mejorValor = cotas[i];
                posicion = i;
            }
        }

        if (trazas) {
            System.out.println("Vector de cotas: " + Arrays.toString(cotas));
            System.out.println("Mejor posición: " + (posicion + 1));
        }
        return posicion;
    }

    // Cambia el indice de los agentes y tareas para que empiece en 1 en lugar de 0 cuando trazas = true
    private ArrayList<Integer> ajustaIndices(ArrayList<Integer> indices) {
        ArrayList<Integer> ajustados = new ArrayList<>();
        for (int indice : indices) {
            ajustados.add(indice + 1);
        }
        return ajustados;
    }
}
