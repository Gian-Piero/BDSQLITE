package com.example.bdsqlite.Actividad2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LibreriaSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Contactos
    String sqlCreateLibro = "CREATE TABLE Libros (isbn TEXT PRIMARY KEY, nombre_libro TEXT)";
    String sqlCreateAlumnos = "CREATE TABLE Alumnos (dni TEXT PRIMARY KEY, nombre_alumno TEXT)";
    String sqlCreatePrestamo = "CREATE TABLE Prestamos (isbn TEXT PRIMARY KEY, nombre_libro TEXT, nombre_alumno TEXT)";
    public LibreriaSQLiteHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateLibro);
        db.execSQL(sqlCreateAlumnos);
        db.execSQL(sqlCreatePrestamo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Contactos");
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreateLibro);
        db.execSQL(sqlCreateAlumnos);
        db.execSQL(sqlCreatePrestamo);
    }
}
