package com.example.gestionreparacionesapp.ui.productos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionreparacionesapp.ui.reparaciones.NuevaReparacionActivity;
import com.example.gestionreparacionesapp.ui.ventas.NuevaVentaActivity;
import com.example.gestionreparacionesapp.R;
import com.example.gestionreparacionesapp.data.db.AppDatabase;
import com.example.gestionreparacionesapp.data.db.entity.Producto;
import com.example.gestionreparacionesapp.ui.clientes.ClientesActivity;

import java.util.List;

public class ListaProductosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProductos;
    private TextView tvSinProductos;
    private AppDatabase db;
    private ProductosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos_real);

        db = AppDatabase.getInstance(this);

        initViews();
        cargarProductos();
        setupBottomNavigation();
    }

    private void initViews() {
        recyclerViewProductos = findViewById(R.id.recyclerViewProductos);
        tvSinProductos = findViewById(R.id.tvSinProductos);

        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));
    }

    private void cargarProductos() {
        List<Producto> listaProductos = db.productoDao().getAll();

        if (listaProductos.isEmpty()) {
            recyclerViewProductos.setVisibility(View.GONE);
            tvSinProductos.setVisibility(View.VISIBLE);
        } else {
            recyclerViewProductos.setVisibility(View.VISIBLE);
            tvSinProductos.setVisibility(View.GONE);

            adapter = new ProductosAdapter(listaProductos);
            recyclerViewProductos.setAdapter(adapter);
        }
    }

    private void setupBottomNavigation() {
        findViewById(R.id.nav_inicio).setOnClickListener(v -> finish());

        findViewById(R.id.nav_ventas).setOnClickListener(v -> {
            Intent intent = new Intent(ListaProductosActivity.this, NuevaVentaActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_reparaciones).setOnClickListener(v -> {
            Intent intent = new Intent(ListaProductosActivity.this, NuevaReparacionActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_clientes).setOnClickListener(v -> {
            Intent intent = new Intent(ListaProductosActivity.this, ClientesActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_productos).setOnClickListener(v -> {
            Intent intent = new Intent(ListaProductosActivity.this, ProductosActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarProductos(); // Recargar para mostrar stock actualizado
    }
}