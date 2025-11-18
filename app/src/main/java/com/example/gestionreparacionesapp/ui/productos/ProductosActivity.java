package com.example.gestionreparacionesapp.ui.productos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionreparacionesapp.ui.reparaciones.NuevaReparacionActivity;
import com.example.gestionreparacionesapp.ui.ventas.NuevaVentaActivity;
import com.example.gestionreparacionesapp.R;
import com.example.gestionreparacionesapp.data.db.AppDatabase;
import com.example.gestionreparacionesapp.data.db.entity.Producto;
import com.example.gestionreparacionesapp.ui.clientes.ClientesActivity;

public class ProductosActivity extends AppCompatActivity {

    private EditText etNombreProducto, etPrecio, etCantidad;
    private Button btnCancelar, btnGuardar, btnVerTodosProductos;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        db = AppDatabase.getInstance(this);

        initViews();
        setupListeners();
        setupBottomNavigation();
    }

    private void initViews() {
        etNombreProducto = findViewById(R.id.etNombreProducto);
        etPrecio = findViewById(R.id.etPrecio);
        etCantidad = findViewById(R.id.etCantidad);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVerTodosProductos = findViewById(R.id.btnVerTodosProductos);
    }

    private void setupListeners() {
        btnGuardar.setOnClickListener(v -> guardarProducto());
        btnCancelar.setOnClickListener(v -> limpiarCampos());
        btnVerTodosProductos.setOnClickListener(v -> {
            Intent intent = new Intent(ProductosActivity.this, ListaProductosActivity.class);
            startActivity(intent);
        });
    }

    private void guardarProducto() {
        String nombre = etNombreProducto.getText().toString().trim();
        String precioStr = etPrecio.getText().toString().trim();
        String cantidadStr = etCantidad.getText().toString().trim();

        // Validaciones...
        if (nombre.isEmpty()) {
            Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (precioStr.isEmpty()) {
            Toast.makeText(this, "El precio es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cantidadStr.isEmpty()) {
            Toast.makeText(this, "La cantidad es obligatoria", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio = Double.parseDouble(precioStr);
        int cantidad = Integer.parseInt(cantidadStr);

        if (precio <= 0) {
            Toast.makeText(this, "El precio debe ser mayor a 0", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cantidad < 0) {
            Toast.makeText(this, "La cantidad no puede ser negativa", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✨ Generar SKU simple
        String sku = "SKU-" + System.currentTimeMillis();

        // TODO: obtener userId real desde login
        int userId = 1; // TEMPORAL

        // ✨ Crear producto usando el constructor completo
        Producto producto = new Producto(
                userId,
                sku,
                nombre,
                precio,
                cantidad,
                null   // imageUri
        );

        long id = db.productoDao().insert(producto);

        if (id > 0) {
            Toast.makeText(this, "Producto guardado exitosamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al guardar el producto", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etNombreProducto.setText("");
        etPrecio.setText("");
        etCantidad.setText("");
        etNombreProducto.requestFocus();
    }

    private void setupBottomNavigation() {
        findViewById(R.id.nav_inicio).setOnClickListener(v -> finish());

        findViewById(R.id.nav_ventas).setOnClickListener(v -> {
            Intent intent = new Intent(ProductosActivity.this, NuevaVentaActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_reparaciones).setOnClickListener(v -> {
            Intent intent = new Intent(ProductosActivity.this, NuevaReparacionActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_clientes).setOnClickListener(v -> {
            Intent intent = new Intent(ProductosActivity.this, ClientesActivity.class);
            startActivity(intent);
        });
    }
}