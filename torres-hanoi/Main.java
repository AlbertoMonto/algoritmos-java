import java.io.*;
import java.util.ArrayList;

public class Main {
    static boolean traza = false;

    public static void main(String[] args) {
        int contador = 0;

        // Procesa los argumentos
        for (String arg : args) {
            if (arg.equals("-t")) {
                contador++;
                traza = true;
            }
            if (arg.equals("-h")) {
                mostrarAyuda();
                contador++;
            }
        }

        // Verifica que hay suficientes argumentos
        String ficheroEntrada = (args.length > contador) ? args[contador] : null;
        String ficheroSalida = (args.length > contador + 1) ? args[contador + 1] : null;

        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            // Configuración de entrada
            if (ficheroEntrada != null) {
                File file = new File(ficheroEntrada);
                if (file.exists()) {
                    br = new BufferedReader(new FileReader(file));
                } else {
                    System.out.println("El fichero de entrada no existe. Introduzca los datos:");
                    br = new BufferedReader(new InputStreamReader(System.in));
                }
            } else {
                System.out.println("No se proporcionó un fichero de entrada. Introduzca los datos:");
                br = new BufferedReader(new InputStreamReader(System.in));
            }

            // Configuración de salida
            if (ficheroSalida != null) {
                bw = new BufferedWriter(new FileWriter(ficheroSalida));
            } else {
                System.out.println("No se proporcionó un fichero de salida. Los resultados se mostrarán en pantalla:");
                bw = new BufferedWriter(new OutputStreamWriter(System.out));
            }

            procesarArchivo(br, bw);
        } catch (IOException e) {
            System.out.println("Error al procesar los datos: " + e.getMessage());
        } finally {
            try {
                if (br != null) br.close();
                if (bw != null) bw.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }
    }

    public static void mostrarAyuda() {
        System.out.println("SINTAXIS: tareas [-t][-h] [fichero entrada] [fichero salida]");
        System.out.println("-t    Traza el algoritmo");
        System.out.println("-h    Muestra esta ayuda");
        System.out.println("[fichero entrada] Nombre del fichero de entrada");
        System.out.println("[fichero salida]  Nombre del fichero de salida");
    }

    public static void procesarArchivo(BufferedReader br, BufferedWriter bw) throws IOException {
        String linea;
        ArrayList<Integer> lista = new ArrayList<>();
        // Leer línea por línea
        while ((linea = br.readLine()) != null && !linea.isEmpty()) {
            String[] datos = linea.split(" ");
            for (String dato : datos) {
                try {
                    int numero = Integer.parseInt(dato);
                    lista.add(numero);
                } catch (NumberFormatException e) {
                    System.err.println("Dato no válido ignorado: " + dato);
                }
            }
        }
        if (lista.size() < 3) {
            throw new IllegalArgumentException("El archivo de entrada debe contener al menos tres números.");
        }
        int origen = lista.get(0);
        int destino = lista.get(1);
        int n = lista.get(2);

        hanoi(origen, destino, n, bw);
    }

    public static void hanoi(int origen, int destino, int n, BufferedWriter bw) throws IOException {
        if (n == 1) {
            String movimiento = origen + "  " + destino;
            String informacion = "Mover disco de poste " + origen + " a poste " + destino;
            bw.write(movimiento);
            bw.newLine();
            if (traza) System.out.println(informacion);
        } else {
            hanoi(origen, 6 - destino - origen, n - 1, bw);
            hanoi(origen, destino, 1, bw);
            hanoi(6 - origen - destino, destino, n - 1, bw);
        }
    }
}
