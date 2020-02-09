package com.example.bdsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bdsqlite.Actividad1.Actividad1;
import com.example.bdsqlite.Actividad2.Actividad2;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void irActividad1(View view) {
        Intent intent = new Intent(this, Actividad1.class);
        startActivity(intent);
    }

    public void irActividad2(View view) {
        Intent intent = new Intent(this, Actividad2.class);
        startActivity(intent);
    }
}
