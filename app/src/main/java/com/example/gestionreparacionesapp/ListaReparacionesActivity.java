package com.example.gestionreparacionesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionreparacionesapp.data.db.AppDatabase;
import com.example.gestionreparacionesapp.data.db.entity.Cliente;
import com.example.gestionreparacionesapp.data.db.entity.Reparacion;

import java.util.List;

public class ListaReparacionesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewReparaciones;
    private TextView tvSinReparaciones;
    private AppDatabase db;
    private ReparacionesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reparaciones);

        db = AppDatabase.getInstance(this);

        initViews();
        cargarReparaciones();
        setupBottomNavigation();
    }

    private void initViews() {
        recyclerViewReparaciones = findViewById(R.id.recyclerViewReparaciones);
        tvSinReparaciones = findViewById(R.id.tvSinReparaciones);

        recyclerViewReparaciones.setLayoutManager(new LinearLayoutManager(this));
    }

    private void cargarReparaciones() {
        List<Reparacion> listaReparaciones = db.reparacionDao().getAll();
        List<Cliente> listaClientes = db.clienteDao().getAll();

        if (listaReparaciones.isEmpty()) {
            recyclerViewReparaciones.setVisibility(View.GONE);
            tvSinReparaciones.setVisibility(View.VISIBLE);
        } else {
            recyclerViewReparaciones.setVisibility(View.VISIBLE);
            tvSinReparaciones.setVisibility(View.GONE);

            adapter = new ReparacionesAdapter(listaReparaciones, listaClientes);
            recyclerViewReparaciones.setAdapter(adapter);
        }
    }

    private void setupBottomNavigation() {
        findViewById(R.id.nav_inicio).setOnClickListener(v -> finish());

        findViewById(R.id.nav_ventas).setOnClickListener(v -> {
            Intent intent = new Intent(ListaReparacionesActivity.this, NuevaVentaActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_reparaciones).setOnClickListener(v -> {
            Intent intent = new Intent(ListaReparacionesActivity.this, NuevaReparacionActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.nav_clientes).setOnClickListener(v -> {
            Intent intent = new Intent(ListaReparacionesActivity.this, ClientesActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_productos).setOnClickListener(v -> {
            Intent intent = new Intent(ListaReparacionesActivity.this, ProductosActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarReparaciones();
    }
}