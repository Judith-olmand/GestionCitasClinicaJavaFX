package Clases;

public class Especialidad {
    private int idE;
    private String nombre;

    public Especialidad(int idE, String nombre) {
        this.idE = idE;
        this.nombre = nombre;
    }

    public int getIdE() {
        return idE;
    }

    public void setIdE(int idE) {
        this.idE = idE;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }


}
