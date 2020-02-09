package com.example.bdsqlite.Actividad1;

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
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.bdsqlite.R;

import java.util.ArrayList;

public class Actividad1 extends AppCompatActivity {

    //Variables para guardar contacto
    private EditText editTextNombre;
    private EditText editTextNumero;

    //Pestañas
    private Resources res;
    private TabHost tabs;

    // Base de datos
    private SQLiteDatabase db;
    private ContactosSQLiteHelper contactosSQLiteHelper;

    //Lista de contactos
    private RecyclerView recyclerView;
    public AdaptadorContactos adaptadorContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad1);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextNumero = findViewById(R.id.editTextNumero);

        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        contactosSQLiteHelper = new ContactosSQLiteHelper(this, "DBcontactos", null, 1);
        db = contactosSQLiteHelper.getWritableDatabase();

        res = getResources();
        tabs = findViewById(android.R.id.tabhost);
        tabs.setup();

        //Vinculo la tab1 con la pestaña
        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Guardar Contactos", res.getDrawable((android.R.drawable.ic_menu_camera)));
        tabs.addTab(spec);

        //Vinculo la tab2 con la pestaña
        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Ver Contactos", res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        // Por defecto mostrara la pestaña de guardar contactos
        tabs.setCurrentTab(0);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                if (tabId.equals("mitab2")) {
                    leerContactos();
                }

            }
        });

    }

    private void leerContactos() {
        final ArrayList<Contacto> contactos = new ArrayList<Contacto>();
        Cursor c = db.rawQuery("SELECT nombre, numero FROM Contactos", null);

        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                String nombreContacto = c.getString(0);
                String numeroContacto = c.getString(1);
                contactos.add(new Contacto(nombreContacto, numeroContacto));
            }while (c.moveToNext());
        }

        recyclerView = findViewById(R.id.rv_lista_webs);
        adaptadorContactos = new AdaptadorContactos(this, contactos);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adaptadorContactos);


        adaptadorContactos.setOnItemClickListener(new AdaptadorContactos.OnItemClickListener() {
            @Override
            public void onbtnPulsado(int position) {
                actualizarBase(position);
            }
        });

    }

    private void actualizarBase(int position)
    {
        Contacto cuento = adaptadorContactos.mdata.get(position);
        String[] args = new String[]{cuento.getNombre()};
        db.execSQL("DELETE FROM Contactos WHERE nombre=?", args);
        adaptadorContactos.mdata.remove(position);
        adaptadorContactos.notifyItemRemoved(position);
    }

    public void guardarContacto(View view) {
        String nombreContacto = editTextNombre.getText().toString();
        String numeroContacto = editTextNumero.getText().toString();

        ContentValues nuevoCuento = new ContentValues();
        nuevoCuento.put("nombre", nombreContacto);
        nuevoCuento.put("numero",  numeroContacto);

        db.insert("Contactos", null, nuevoCuento);
        limpiar();
        Toast.makeText(getApplicationContext(), "Contacto guardado", Toast.LENGTH_SHORT).show();
    }

    private void limpiar() {
        editTextNumero.setText("");
        editTextNombre.setText("");
    }
}
