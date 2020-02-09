package com.example.bdsqlite.Actividad2;

public class Libro {
    private String isbn;
    private String nombre;

    public Libro(String isbn, String nombre) {
        this.isbn = isbn;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIsbn() {
        return isbn;
    }
}
