package Clases;

import java.util.Date;

public class Cita {
    private int idp;
    private int numeroCita;
    private Date fecha;
    private String especialidad;
    private int idEspecial;

    public Cita(int idp, int numeroCita, Date fecha, String especialidad, int idEspecial) {
        this.idp = idp;
        this.numeroCita = numeroCita;
        this.fecha = fecha;
        this.especialidad = especialidad;
        this.idEspecial = idEspecial;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public int getNumeroCita() {
        return numeroCita;
    }

    public void setNumeroCita(int numeroCita) {
        this.numeroCita = numeroCita;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getIdEspecial() {
        return idEspecial;
    }

    public void setIdEspecial(int idEspecial) {
        this.idEspecial = idEspecial;
    }
}
