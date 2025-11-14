package com.example.gestionreparacionesapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionreparacionesapp.data.db.AppDatabase;

public class HomeActivity extends AppCompatActivity {

    private TextView tvBienvenida;
    private Button btnVentas, btnReparaciones, btnClientes, btnProductos, btnCerrarSesion;
    private String usuarioNombre;
    private int usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usuarioNombre = getIntent().getStringExtra("usuario_nombre");
        usuarioId = getIntent().getIntExtra("usuario_id", 0);

        initViews();
        setupListeners();
    }

    private void initViews() {
        tvBienvenida = findViewById(R.id.tvBienvenida);
        btnVentas = findViewById(R.id.btnVentas);
        btnReparaciones = findViewById(R.id.btnReparaciones);
        btnClientes = findViewById(R.id.btnClientes);
        btnProductos = findViewById(R.id.btnProductos);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        tvBienvenida.setText(getString(R.string.bienvenido, usuarioNombre));
    }

    private void setupListeners() {
        btnVentas.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, NuevaVentaActivity.class);
            startActivity(intent);
        });

        btnReparaciones.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, NuevaReparacionActivity.class);
            startActivity(intent);
        });

        btnClientes.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ClientesActivity.class);
            startActivity(intent);
        });

        btnProductos.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProductosActivity.class);
            startActivity(intent);
        });

        btnCerrarSesion.setOnClickListener(v -> {
            // Limpiar "recordarme"
            AppDatabase db = AppDatabase.getInstance(this);
            db.usuarioDao().limpiarRecordarme();

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}