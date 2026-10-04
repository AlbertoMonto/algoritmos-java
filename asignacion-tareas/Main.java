import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {


    public static void main(String[] args) {
        int contador = 0;
        boolean trazas = false;
        // Procesa los argumentos
        for (String arg : args) {
            if (arg.equals("-t")) {
                contador++;
                trazas = true;
            }
            if (arg.equals("-h")) {
                mostrarAyuda();
                contador++;
            }
        }

        // Verifica que hay suficientes argumentos
        if (args.length < contador + 2) {
            System.out.println("Error: No se proporcionaron suficientes argumentos.");
            return;
        }

        String ficheroEntrada = args[contador];
        String ficheroSalida = args[contador + 1];

        try {
            procesarArchivo(ficheroEntrada, ficheroSalida, trazas);
        } catch (IOException e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
        }
    }



    public static void mostrarAyuda() {
        System.out.println("SINTAXIS: tareas [-t][-h] [fichero entrada] [fichero salida]");
        System.out.println("-t    Traza el algoritmo");
        System.out.println("-h    Muestra esta ayuda");
        System.out.println("[fichero entrada] Nombre del fichero de entrada");
        System.out.println("[fichero salida]  Nombre del fichero de salida");
    }

    public static void procesarArchivo(String ficheroEntrada, String ficheroSalida, boolean trazas) throws IOException {
        int[][] tabla;
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroEntrada))) {
            String linea = br.readLine();
            if (linea == null) {
                System.out.println("El archivo está vacío.");
                return;
            }

            // Procesa la primera línea
            int nAgentes = Integer.parseInt(linea.substring(0, 1));
            int nTareas = Integer.parseInt(linea.substring(2, 3));

            tabla = new int[nAgentes][nTareas];

            // Procesa el resto del archivo
            int j = 0;
            while ((linea = br.readLine()) != null) {
                String[] numeros = linea.split(" ");
                for (int i = 0; i < numeros.length; i++) {
                    tabla[j][i] = Integer.parseInt(numeros[i]);
                }
                j++;
            }
        }

        Asignacion a = new Asignacion(tabla, trazas);
        int[] resultado = a.asignaTareas();

        // Escribe el resultado en el fichero de salida
        try (FileWriter writer = new FileWriter(ficheroSalida)) {
            for (int i = 0; i < resultado.length; i++) {
                writer.write("Agente " +(i + 1) + " asignado a tarea " + (resultado[i] + 1) + "\n");
            }

        }
    }
}
