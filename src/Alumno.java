import java.io.Serializable;

public class Alumno implements Serializable {
    private int carnet;
    private String nombre;
    private String codigoCurso;
    private String seccion;
    private double nota;

    public Alumno(int carnet, String nombre, String codigoCurso, String seccion, double nota) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.codigoCurso = codigoCurso;
        this.seccion = seccion;
        this.nota = nota;
    }

    public int getCarnet() { return carnet; }
    public String getNombre() { return nombre; }
    public String getCodigoCurso() { return codigoCurso; }
    public String getSeccion() { return seccion; }
    public double getNota() { return nota; }

    @Override
    public String toString() {
        return "Carnet: " + carnet + " | Nombre: " + nombre + " | Curso: " + codigoCurso + " | Sección: " + seccion + " | Nota: " + nota;
    }
}