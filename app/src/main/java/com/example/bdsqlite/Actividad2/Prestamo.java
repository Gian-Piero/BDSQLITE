package com.example.bdsqlite.Actividad2;

public class Prestamo {
    private String dniPrestamo;
    private String isbnPrestamo;
    private String nombreAlumno;
    private String nombreLibro;

    public Prestamo (String dniPrestamo, String isbnPrestamo, String nombreAlumno, String nombreLibro) {
        this.dniPrestamo = dniPrestamo;
        this.isbnPrestamo = isbnPrestamo;
        this.nombreAlumno = nombreAlumno;
        this.nombreLibro = nombreLibro;
    }

    public String getDniPrestamo() {
        return dniPrestamo;
    }

    public String getIsbnPrestamo() {
        return isbnPrestamo;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public String getNombreLibro() {
        return nombreLibro;
    }
}
