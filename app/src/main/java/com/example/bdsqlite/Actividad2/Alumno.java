package com.example.bdsqlite.Actividad2;

public class Alumno {

    private String dniAlumno;
    private String nombreAlumno;

    public Alumno(String dniAlumno, String nombreAlumno) {
        this.dniAlumno = dniAlumno;
        this.nombreAlumno = nombreAlumno;
    }

    public String getDniAlumno() {
        return dniAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }
}
