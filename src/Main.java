import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ListaEnlazada listaPorNombre = new ListaEnlazada();
    private static ListaEnlazada listaPorCarnet = new ListaEnlazada();
    private static ListaEnlazada listaPorNota = new ListaEnlazada();

    private static String nombreArchivoGuardado = "";
    private static boolean datosCargados = false;

    public static void main(String[] args) {
        Scanner sn = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n========= MENU PROYECTO 3 =========");
            System.out.println("1. Establecer y visualizar archivo original");
            System.out.println("2. Cargar datos a las listas (con simulación)");
            System.out.println("3. Visualizar ordenado por Nombre (Asc agrupado)");
            System.out.println("4. Visualizar ordenado por Carnet (Asc agrupado)");
            System.out.println("5. Visualizar ordenado por Nota (Desc sin importar sección)");
            System.out.println("6. Visualizar Notas más Alta y más Baja por sección");
            System.out.println("7. Visualizar Promedio por sección");
            System.out.println("8. Salir");
            System.out.print("Elija una opción: ");

            while (!sn.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido.");
                sn.next();
            }
            opcion = sn.nextInt();
            sn.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1: opcion1(sn); break;
                case 2: opcion2(); break;
                case 3: opcion3(); break;
                case 4: opcion4(); break;
                case 5: opcion5(); break;
                case 6: opcion6(sn); break;
                case 7: opcion7(sn); break;
                case 8: System.out.println("Saliendo... ¡Muchos éxitos en la entrega!"); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 8);
    }

    private static void opcion1(Scanner sn) {
        if (!nombreArchivoGuardado.isEmpty()) {
            System.out.println("El archivo ya fue establecido previamente: " + nombreArchivoGuardado);
        } else {
            System.out.print("Ingrese el nombre del archivo (ej: alumnos.txt): ");
            nombreArchivoGuardado = sn.nextLine().trim();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivoGuardado))) {
            String linea;
            System.out.println("\n--- CONTENIDO ORIGINAL DEL ARCHIVO ---");
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("¡Error al leer el archivo! Verifique el nombre.");
            nombreArchivoGuardado = "";
        }
    }

    private static void opcion2() {
        if (nombreArchivoGuardado.isEmpty()) {
            System.out.println("Primero establezca el archivo en la opción 1.");
            return;
        }
        if (datosCargados) {
            System.out.println("Los datos ya fueron cargados previamente.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivoGuardado))) {
            String linea;
            System.out.println("\n--- SIMULANDO CARGA REGISTRO POR REGISTRO ---");
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] campos = linea.split(",");
                int carnet = Integer.parseInt(campos[0].trim());
                String nombre = campos[1].trim();
                String curso = campos[2].trim();
                String seccion = campos[3].trim();
                double nota = Double.parseDouble(campos[4].trim());

                Alumno alumno = new Alumno(carnet, nombre, curso, seccion, nota);

                listaPorNombre.insertarOrdenadoPorNombre(alumno);
                listaPorCarnet.insertarOrdenadoPorCarnet(alumno);
                listaPorNota.insertarOrdenadoPorNota(alumno);

                System.out.println("\n-> Insertando dinámicamente: " + nombre);
                listaPorNombre.simularLista();
            }
            datosCargados = true;
            System.out.println("\n¡Carga masiva completada exitosamente!");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error procesando el archivo: " + e.getMessage());
        }
    }

    private static void opcion3() {
        if (!datosCargados) { System.out.println("Cargue los datos en la opción 2 primero."); return; }
        System.out.println("\n--- REPORTE ORDENADO POR NOMBRE (CURSO -> SECCIÓN -> NOMBRE) ---");

        Nodo temp = listaPorNombre.getCabeza();
        String cursoActual = "";
        String seccionActual = "";

        while (temp != null) {
            if (!temp.alumno.getCodigoCurso().equals(cursoActual) || !temp.alumno.getSeccion().equals(seccionActual)) {
                cursoActual = temp.alumno.getCodigoCurso();
                seccionActual = temp.alumno.getSeccion();
                System.out.println("\n>> CURSO: " + cursoActual + " | SECCIÓN: " + seccionActual + " <<");
            }
            System.out.println("   - " + temp.alumno.getNombre() + " (Carnet: " + temp.alumno.getCarnet() + ", Nota: " + temp.alumno.getNota() + ")");
            temp = temp.siguiente;
        }
    }

    private static void opcion4() {
        if (!datosCargados) { System.out.println("Cargue los datos en la opción 2 primero."); return; }
        System.out.println("\n--- REPORTE ORDENADO POR CARNET (CURSO -> SECCIÓN -> CARNET) ---");

        Nodo temp = listaPorCarnet.getCabeza();
        String cursoActual = "";
        String seccionActual = "";

        while (temp != null) {
            if (!temp.alumno.getCodigoCurso().equals(cursoActual) || !temp.alumno.getSeccion().equals(seccionActual)) {
                cursoActual = temp.alumno.getCodigoCurso();
                seccionActual = temp.alumno.getSeccion();
                System.out.println("\n>> CURSO: " + cursoActual + " | SECCIÓN: " + seccionActual + " <<");
            }
            System.out.println("   - Carnet: " + temp.alumno.getCarnet() + " | Nombre: " + temp.alumno.getNombre() + " | Nota: " + temp.alumno.getNota());
            temp = temp.siguiente;
        }
    }

    private static void opcion5() {
        if (!datosCargados) { System.out.println("Cargue los datos en la opción 2 primero."); return; }
        System.out.println("\n--- REPORTE GLOBAL: NOTAS DE MAYOR A MENOR ---");

        Nodo temp = listaPorNota.getCabeza();
        while (temp != null) {
            System.out.println("Nota: " + temp.alumno.getNota() + " -> Carnet: " + temp.alumno.getCarnet() + " | " + temp.alumno.getNombre() + " [Curso: " + temp.alumno.getCodigoCurso() + " Secc: " + temp.alumno.getSeccion() + "]");
            temp = temp.siguiente;
        }
        System.out.println("\n--- SIMULACIÓN VISUAL DE LA LISTA DE NOTAS ---");
        listaPorNota.simularLista();
    }

    private static void opcion6(Scanner sn) {
        if (!datosCargados) { System.out.println("Cargue los datos en la opción 2 primero."); return; }
        System.out.print("Ingrese Código del Curso: "); String curso = sn.nextLine().trim();
        System.out.print("Ingrese la Sección: "); String seccion = sn.nextLine().trim();

        System.out.println("\n--- TOP 5 NOTAS MÁS ALTAS ---");
        Nodo temp = listaPorNota.getCabeza();
        int altas = 0;
        while (temp != null && altas < 5) {
            if (temp.alumno.getCodigoCurso().equalsIgnoreCase(curso) && temp.alumno.getSeccion().equalsIgnoreCase(seccion)) {
                System.out.println((altas+1) + ". Nota: " + temp.alumno.getNota() + " | " + temp.alumno.getNombre());
                altas++;
            }
            temp = temp.siguiente;
        }
        if (altas == 0) { System.out.println("No hay datos para esta sección."); return; }

        System.out.println("\n--- TOP 5 NOTAS MÁS BAJAS ---");
        ArrayList<Alumno> filtrados = new ArrayList<>();
        temp = listaPorNota.getCabeza();
        while (temp != null) {
            if (temp.alumno.getCodigoCurso().equalsIgnoreCase(curso) && temp.alumno.getSeccion().equalsIgnoreCase(seccion)) {
                filtrados.add(temp.alumno);
            }
            temp = temp.siguiente;
        }
        int bajas = 0;
        for (int i = filtrados.size() - 1; i >= 0 && bajas < 5; i--) {
            Alumno al = filtrados.get(i);
            System.out.println((bajas+1) + ". Nota: " + al.getNota() + " | " + al.getNombre());
            bajas++;
        }
    }

    private static void opcion7(Scanner sn) {
        if (!datosCargados) { System.out.println("Cargue los datos en la opción 2 primero."); return; }
        System.out.print("Ingrese Código del Curso: "); String curso = sn.nextLine().trim();
        System.out.print("Ingrese la Sección: "); String seccion = sn.nextLine().trim();

        Nodo temp = listaPorNota.getCabeza();
        double suma = 0; int cont = 0;
        while (temp != null) {
            if (temp.alumno.getCodigoCurso().equalsIgnoreCase(curso) && temp.alumno.getSeccion().equalsIgnoreCase(seccion)) {
                suma += temp.alumno.getNota(); cont++;
            }
            temp = temp.siguiente;
        }
        if (cont > 0) {
            System.out.printf("\nCurso %s | Sección %s -> Promedio: %.2f (Alumnos: %d)\n", curso, seccion, (suma/cont), cont);
        } else {
            System.out.println("No se encontraron registros.");
        }
    }
}