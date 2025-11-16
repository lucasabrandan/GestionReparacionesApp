package com.example.gestionreparacionesapp.ui.clientes;

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
import com.example.gestionreparacionesapp.data.db.entity.Cliente;
import com.example.gestionreparacionesapp.ui.productos.ProductosActivity;

import java.util.List;

public class ListaClientesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewClientes;
    private TextView tvSinClientes;
    private AppDatabase db;
    private ClientesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes_real);

        db = AppDatabase.getInstance(this);

        initViews();
        cargarClientes();
        setupBottomNavigation();
    }

    private void initViews() {
        recyclerViewClientes = findViewById(R.id.recyclerViewClientes);
        tvSinClientes = findViewById(R.id.tvSinClientes);

        recyclerViewClientes.setLayoutManager(new LinearLayoutManager(this));
    }

    private void cargarClientes() {
        List<Cliente> listaClientes = db.clienteDao().getAll();

        if (listaClientes.isEmpty()) {
            recyclerViewClientes.setVisibility(View.GONE);
            tvSinClientes.setVisibility(View.VISIBLE);
        } else {
            recyclerViewClientes.setVisibility(View.VISIBLE);
            tvSinClientes.setVisibility(View.GONE);

            adapter = new ClientesAdapter(listaClientes);
            recyclerViewClientes.setAdapter(adapter);
        }
    }

    private void setupBottomNavigation() {
        findViewById(R.id.nav_inicio).setOnClickListener(v -> finish());

        findViewById(R.id.nav_ventas).setOnClickListener(v -> {
            Intent intent = new Intent(ListaClientesActivity.this, NuevaVentaActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_reparaciones).setOnClickListener(v -> {
            Intent intent = new Intent(ListaClientesActivity.this, NuevaReparacionActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_clientes).setOnClickListener(v -> {
            Intent intent = new Intent(ListaClientesActivity.this, ClientesActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.nav_productos).setOnClickListener(v -> {
            Intent intent = new Intent(ListaClientesActivity.this, ProductosActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarClientes();
    }
}