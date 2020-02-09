package com.example.bdsqlite.Actividad2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.bdsqlite.R;

import java.util.ArrayList;

public class Actividad2 extends AppCompatActivity {

    //Variables para guardar un libro
    private EditText editTextIsbn;
    private EditText editTextNombreLibro;

    //Variables para guardar un alumno
    private EditText editTextDniUsuario;
    private EditText editTextNombreUsuario;

    //Pestañas
    private Resources res;
    private TabHost tabs;

    // Base de datos
    private SQLiteDatabase db;
    private LibreriaSQLiteHelper libreriaSQLiteHelper;

    //Lista de libros
    public AdaptadorLibros adaptadorLibros;

    //Lista de alumnos
    public AdapatadorAlumnos adapatadorAlumnos;

    //Lista de prestamos
    public AdaptadorPrestamos adaptadorPrestamos;

    private Spinner spinnerAlumnos;
    private Spinner spinnerLibros;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad2);

        editTextIsbn = findViewById(R.id.editTextIsbn);
        editTextNombreLibro = findViewById(R.id.editTextNombreLibro);


        editTextDniUsuario = findViewById(R.id.editTextDniUsuario);
        editTextNombreUsuario = findViewById(R.id.editTextNombreUsuario);

        spinnerAlumnos = findViewById(R.id.spinnerAlumnos);
        spinnerLibros = findViewById(R.id.spinnerLibros);


        //Abrimos la base de datos "DBlibreria" en modo de escritura
        libreriaSQLiteHelper = new LibreriaSQLiteHelper(this, "DBlibreria", null, 1);
        db = libreriaSQLiteHelper.getWritableDatabase();

        res = getResources();
        tabs = findViewById(android.R.id.tabhost);
        tabs.setup();

        //Vinculo la tab1 con la pestaña
        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Gestion Libros", res.getDrawable((android.R.drawable.ic_menu_camera)));
        tabs.addTab(spec);

        //Vinculo la tab2 con la pestaña
        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Gestion Alumnos", res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        //Vinculo la tab3 con la pestaña
        spec = tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Gestion Prestamo", res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        // Por defecto mostrara la pestaña de guardar contactos
        tabs.setCurrentTab(0);
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                if (tabId.equals("mitab3")) {
                   cargarSpinner();
                }

            }
        });


        cargarSpinner();
        cargarLibros();
        cargarAlumnos();
        cargarPrestamos();
    }

    private void cargarLibros()
    {
        final ArrayList<Libro> libros = new ArrayList<Libro>();
        Cursor c = db.rawQuery("SELECT isbn, nombre_libro FROM Libros", null);

        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                String isbn = c.getString(0);
                String nombre = c.getString(1);
                libros.add(new Libro(isbn, nombre));
            }while (c.moveToNext());
        }

        RecyclerView recyclerView = findViewById(R.id.rv_lista_libros);
        adaptadorLibros = new AdaptadorLibros(this, libros);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adaptadorLibros);

        adaptadorLibros.setOnItemClickListener(new AdaptadorLibros.OnItemClickListener() {
            @Override
            public void onbtnPulsado(int position) {
                Libro libro = adaptadorLibros.mdata.get(position);
                String[] args = new String[]{libro.getIsbn()};
                db.execSQL("DELETE FROM Libros WHERE isbn=?", args);
                adaptadorLibros.mdata.remove(position);
                adaptadorLibros.notifyItemRemoved(position);
            }
        });

    }

    private void cargarAlumnos()
    {
        final ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
        Cursor c = db.rawQuery("SELECT dni, nombre_alumno FROM Alumnos", null);

        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                String dni = c.getString(0);
                String nombre = c.getString(1);
                alumnos.add(new Alumno(dni, nombre));
            }while (c.moveToNext());
        }

        RecyclerView recyclerView = findViewById(R.id.rv_lista_alumnos);
        adapatadorAlumnos = new AdapatadorAlumnos(this, alumnos);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapatadorAlumnos);

        adapatadorAlumnos.setOnItemClickListener(new AdapatadorAlumnos.OnItemClickListener() {
            @Override
            public void onbtnPulsado(int position) {
                Alumno alumno = adapatadorAlumnos.mdata.get(position);
                String[] args = new String[]{alumno.getDniAlumno()};
                db.execSQL("DELETE FROM Alumnos WHERE dni=?", args);
                adapatadorAlumnos.mdata.remove(position);
                adapatadorAlumnos.notifyItemRemoved(position);

            }
        });
    }

    private void cargarPrestamos()
    {
        final ArrayList<Prestamo> prestamos = new ArrayList<Prestamo>();
        Cursor c = db.rawQuery("SELECT isbn, dni, nombre_libro, nombre_alumno FROM Prestamos", null);
        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                String isbn = c.getString(0);
                String dni = c.getString(1);
                String nombre_libro = c.getString(2);
                String nombre_alumno = c.getString(3);
                prestamos.add(new Prestamo(isbn, dni, nombre_alumno, nombre_libro));
            }while (c.moveToNext());
        }

        RecyclerView recyclerView = findViewById(R.id.rv_lista_prestamos);
        adaptadorPrestamos = new AdaptadorPrestamos(this, prestamos);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adaptadorPrestamos);

    }

    private void cargarSpinner()
    {
        //Cargo el spinnerAlumnos con los datos
        final ArrayList<String> alumnos = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT nombre_alumno FROM Alumnos", null);

        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                String nombre = c.getString(0);
                alumnos.add(nombre);
            }while (c.moveToNext());
        }

        ArrayAdapter<String> adapterAlumnos = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, alumnos);
        spinnerAlumnos.setAdapter(adapterAlumnos);

        // Cargo el spinnerLibros con los datos
        final ArrayList<String> libros = new ArrayList<String>();
        Cursor d = db.rawQuery("SELECT nombre_libro FROM Libros", null);

        if (d.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                String nombre = d.getString(0);
                libros.add(nombre);
            }while (d.moveToNext());
        }

        ArrayAdapter<String> adapterLibros = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, libros);
        spinnerLibros.setAdapter(adapterLibros);
    }

    public void guardarLibro(View view) {
        String isbn = editTextIsbn.getText().toString();
        String nombre = editTextNombreLibro.getText().toString();

        ContentValues nuevoLibro = new ContentValues();
        nuevoLibro.put("isbn", isbn);
        nuevoLibro.put("nombre_libro",  nombre);

        db.insert("Libros", null, nuevoLibro);
        editTextIsbn.setText("");
        editTextNombreLibro.setText("");
        Toast.makeText(getApplicationContext(), "Libro Guardado", Toast.LENGTH_SHORT).show();
        cargarLibros();
    }



    public void guardarAlumno(View view) {
        String dni = editTextDniUsuario.getText().toString();
        String nombre = editTextNombreUsuario.getText().toString();

        ContentValues nuevoAlumno = new ContentValues();
        nuevoAlumno.put("dni", dni);
        nuevoAlumno.put("nombre_alumno",  nombre);

        db.insert("Alumnos", null, nuevoAlumno);
        editTextDniUsuario.setText("");
        editTextNombreUsuario.setText("");
        Toast.makeText(getApplicationContext(), "Alumno Guardado", Toast.LENGTH_SHORT).show();
        cargarAlumnos();
    }

    public void guardarPrestamos(View view) {
        String nombreAlumno = spinnerAlumnos.getSelectedItem().toString();
        String nombreLibro = spinnerLibros.getSelectedItem().toString();

        //Obtengo el isbn del libro seleccionado
        final ArrayList<String> isbnLibro = new ArrayList<String>();
        String[] arg= new String[]{nombreLibro};
        Cursor c = db.rawQuery("SELECT isbn FROM Libros where nombre_libro=?", arg);

        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                String nombre = c.getString(0);
                isbnLibro.add(nombre);
            }while (c.moveToNext());
        }
        System.out.println(isbnLibro + " ------");


        ContentValues nuevoPrestamo = new ContentValues();
        nuevoPrestamo.put("isbn", isbnLibro.get(0));
        nuevoPrestamo.put("nombre_libro",  nombreLibro);
        nuevoPrestamo.put("nombre_alumno",  nombreAlumno);

        db.insert("Prestamos", null, nuevoPrestamo);
        Toast.makeText(getApplicationContext(), "Prestamo Realizado", Toast.LENGTH_SHORT).show();

        cargarPrestamos();
    }
}
