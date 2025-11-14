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
import com.example.gestionreparacionesapp.data.db.entity.Venta;

import java.util.List;

public class ListaVentasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewVentas;
    private TextView tvSinVentas;
    private AppDatabase db;
    private VentasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ventas);

        db = AppDatabase.getInstance(this);

        initViews();
        cargarVentas();
        setupBottomNavigation();
    }

    private void initViews() {
        recyclerViewVentas = findViewById(R.id.recyclerViewVentas);
        tvSinVentas = findViewById(R.id.tvSinVentas);

        recyclerViewVentas.setLayoutManager(new LinearLayoutManager(this));
    }

    private void cargarVentas() {
        List<Venta> listaVentas = db.ventaDao().getAll();
        List<Cliente> listaClientes = db.clienteDao().getAll();

        if (listaVentas.isEmpty()) {
            recyclerViewVentas.setVisibility(View.GONE);
            tvSinVentas.setVisibility(View.VISIBLE);
        } else {
            recyclerViewVentas.setVisibility(View.VISIBLE);
            tvSinVentas.setVisibility(View.GONE);

            adapter = new VentasAdapter(listaVentas, listaClientes);
            recyclerViewVentas.setAdapter(adapter);
        }
    }

    private void setupBottomNavigation() {
        findViewById(R.id.nav_inicio).setOnClickListener(v -> finish());

        findViewById(R.id.nav_ventas).setOnClickListener(v -> {
            Intent intent = new Intent(ListaVentasActivity.this, NuevaVentaActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.nav_reparaciones).setOnClickListener(v -> {
            Intent intent = new Intent(ListaVentasActivity.this, NuevaReparacionActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_clientes).setOnClickListener(v -> {
            Intent intent = new Intent(ListaVentasActivity.this, ClientesActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_productos).setOnClickListener(v -> {
            Intent intent = new Intent(ListaVentasActivity.this, ProductosActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarVentas(); // Recargar al volver
    }
}