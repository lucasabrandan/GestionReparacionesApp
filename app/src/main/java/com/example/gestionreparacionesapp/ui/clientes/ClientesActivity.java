package com.example.gestionreparacionesapp.ui.clientes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionreparacionesapp.ui.reparaciones.NuevaReparacionActivity;
import com.example.gestionreparacionesapp.ui.ventas.NuevaVentaActivity;
import com.example.gestionreparacionesapp.ui.productos.ProductosActivity;
import com.example.gestionreparacionesapp.R;
import com.example.gestionreparacionesapp.data.db.AppDatabase;
import com.example.gestionreparacionesapp.data.db.entity.Cliente;

public class ClientesActivity extends AppCompatActivity {

    private EditText etNombreCliente, etDireccion, etLocalidad, etCodigoPostal;
    private Button btnCancelar, btnGuardar, btnVerTodosClientes;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        db = AppDatabase.getInstance(this);

        initViews();
        setupListeners();
        setupBottomNavigation();
    }

    private void initViews() {
        etNombreCliente = findViewById(R.id.etNombreCliente);
        etDireccion = findViewById(R.id.etDireccion);
        etLocalidad = findViewById(R.id.etLocalidad);
        etCodigoPostal = findViewById(R.id.etCodigoPostal);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVerTodosClientes = findViewById(R.id.btnVerTodosClientes);
    }

    private void setupListeners() {
        btnGuardar.setOnClickListener(v -> guardarCliente());
        btnCancelar.setOnClickListener(v -> limpiarCampos());
        btnVerTodosClientes.setOnClickListener(v -> {
            Intent intent = new Intent(ClientesActivity.this, ListaClientesActivity.class);
            startActivity(intent);
        });
    }

    private void guardarCliente() {
        String nombre = etNombreCliente.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();
        String localidad = etLocalidad.getText().toString().trim();
        String codigoPostal = etCodigoPostal.getText().toString().trim();

        // Validar campos obligatorios
        if (nombre.isEmpty()) {
            Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }

        if (direccion.isEmpty()) {
            Toast.makeText(this, "La dirección es obligatoria", Toast.LENGTH_SHORT).show();
            return;
        }

        if (localidad.isEmpty()) {
            Toast.makeText(this, "La localidad es obligatoria", Toast.LENGTH_SHORT).show();
            return;
        }

        if (codigoPostal.isEmpty()) {
            Toast.makeText(this, "El código postal es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear y guardar cliente
        Cliente cliente = new Cliente(nombre, direccion, localidad, codigoPostal);
        long id = db.clienteDao().insert(cliente);

        if (id > 0) {
            Toast.makeText(this, "Cliente guardado exitosamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al guardar el cliente", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etNombreCliente.setText("");
        etDireccion.setText("");
        etLocalidad.setText("");
        etCodigoPostal.setText("");
        etNombreCliente.requestFocus();
    }

    private void setupBottomNavigation() {
        findViewById(R.id.nav_inicio).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.nav_ventas).setOnClickListener(v -> {
            Intent intent = new Intent(ClientesActivity.this, NuevaVentaActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_reparaciones).setOnClickListener(v -> {
            Intent intent = new Intent(ClientesActivity.this, NuevaReparacionActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_productos).setOnClickListener(v -> {
            Intent intent = new Intent(ClientesActivity.this, ProductosActivity.class);
            startActivity(intent);
        });
    }
}