public class ListaEnlazada {
    private Nodo cabeza;

    public Nodo getCabeza() {
        return cabeza;
    }

    // --- INSERCIÓN ORDENADA MULTINIVEL: CURSO -> SECCIÓN -> NOMBRE (A-Z) ---
    public void insertarOrdenadoPorNombre(Alumno nuevoAlumno) {
        Nodo nuevoNodo = new Nodo(nuevoAlumno);

        if (cabeza == null || debaIrAntesPorNombre(nuevoAlumno, cabeza.alumno)) {
            nuevoNodo.siguiente = cabeza;
            cabeza = nuevoNodo;
            return;
        }

        Nodo actual = cabeza;
        while (actual.siguiente != null && !debaIrAntesPorNombre(nuevoAlumno, actual.siguiente.alumno)) {
            actual = actual.siguiente;
        }
        nuevoNodo.siguiente = actual.siguiente;
        actual.siguiente = nuevoNodo;
    }

    private boolean debaIrAntesPorNombre(Alumno nuevo, Alumno existente) {
        int compCurso = nuevo.getCodigoCurso().compareToIgnoreCase(existente.getCodigoCurso());
        if (compCurso != 0) return compCurso < 0;

        int compSeccion = nuevo.getSeccion().compareToIgnoreCase(existente.getSeccion());
        if (compSeccion != 0) return compSeccion < 0;

        return nuevo.getNombre().compareToIgnoreCase(existente.getNombre()) < 0;
    }

    // --- INSERCIÓN ORDENADA MULTINIVEL: CURSO -> SECCIÓN -> CARNET (ASC) ---
    public void insertarOrdenadoPorCarnet(Alumno nuevoAlumno) {
        Nodo nuevoNodo = new Nodo(nuevoAlumno);

        if (cabeza == null || debaIrAntesPorCarnet(nuevoAlumno, cabeza.alumno)) {
            nuevoNodo.siguiente = cabeza;
            cabeza = nuevoNodo;
            return;
        }

        Nodo actual = cabeza;
        while (actual.siguiente != null && !debaIrAntesPorCarnet(nuevoAlumno, actual.siguiente.alumno)) {
            actual = actual.siguiente;
        }
        nuevoNodo.siguiente = actual.siguiente;
        actual.siguiente = nuevoNodo;
    }

    private boolean debaIrAntesPorCarnet(Alumno nuevo, Alumno existente) {
        int compCurso = nuevo.getCodigoCurso().compareToIgnoreCase(existente.getCodigoCurso());
        if (compCurso != 0) return compCurso < 0;

        int compSeccion = nuevo.getSeccion().compareToIgnoreCase(existente.getSeccion());
        if (compSeccion != 0) return compSeccion < 0;

        return nuevo.getCarnet() < existente.getCarnet();
    }

    // --- INSERCIÓN ORDENADA POR NOTA (DESCENDENTE GLOBAL) ---
    public void insertarOrdenadoPorNota(Alumno nuevoAlumno) {
        Nodo nuevoNodo = new Nodo(nuevoAlumno);

        if (cabeza == null || cabeza.alumno.getNota() < nuevoAlumno.getNota()) {
            nuevoNodo.siguiente = cabeza;
            cabeza = nuevoNodo;
            return;
        }

        Nodo actual = cabeza;
        while (actual.siguiente != null && actual.siguiente.alumno.getNota() > nuevoAlumno.getNota()) {
            actual = actual.siguiente;
        }
        nuevoNodo.siguiente = actual.siguiente;
        actual.siguiente = nuevoNodo;
    }

    // --- SIMULACIÓN GRÁFICA EN TEXTO ---
    public void simularLista() {
        if (cabeza == null) {
            System.out.println("INICIO -> NULL (Lista Vacía)");
            return;
        }
        Nodo temp = cabeza;
        System.out.print("INICIO -> ");
        while (temp != null) {
            System.out.print("[" + temp.alumno.getNombre() + " | Secc: " + temp.alumno.getSeccion() + " | " + temp.alumno.getNota() + "] -> ");
            temp = temp.siguiente;
        }
        System.out.println("NULL");
    }
}