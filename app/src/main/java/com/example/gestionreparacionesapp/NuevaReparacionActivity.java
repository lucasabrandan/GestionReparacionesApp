package com.example.gestionreparacionesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionreparacionesapp.data.db.AppDatabase;
import com.example.gestionreparacionesapp.data.db.entity.Cliente;
import com.example.gestionreparacionesapp.data.db.entity.Producto;
import com.example.gestionreparacionesapp.data.db.entity.Reparacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NuevaReparacionActivity extends AppCompatActivity {

    private Spinner spinnerCliente;
    private EditText etDireccion, etLocalidad, etCodigoPostal, etDescripcion;
    private LinearLayout containerProductos;
    private Button btnAnadirProducto, btnCancelar, btnGuardar, btnVerTodasReparaciones;
    private TextView tvSubtotal, tvTotal;

    private AppDatabase db;
    private List<Cliente> listaClientes;
    private List<Producto> listaProductos;
    private Cliente clienteSeleccionado;
    private List<ProductoReparacion> productosEnReparacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_reparacion);

        db = AppDatabase.getInstance(this);
        productosEnReparacion = new ArrayList<>();

        initViews();
        cargarClientes();
        cargarProductos();
        setupListeners();
        setupBottomNavigation();

        // Agregar primer producto automáticamente
        agregarNuevoProducto();
    }

    private void initViews() {
        spinnerCliente = findViewById(R.id.spinnerCliente);
        etDireccion = findViewById(R.id.etDireccion);
        etLocalidad = findViewById(R.id.etLocalidad);
        etCodigoPostal = findViewById(R.id.etCodigoPostal);
        etDescripcion = findViewById(R.id.etDescripcion);
        containerProductos = findViewById(R.id.containerProductos);
        btnAnadirProducto = findViewById(R.id.btnAnadirProducto);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVerTodasReparaciones = findViewById(R.id.btnVerTodasReparaciones);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvTotal = findViewById(R.id.tvTotal);
    }

    private void cargarClientes() {
        listaClientes = db.clienteDao().getAll();

        if (listaClientes.isEmpty()) {
            Toast.makeText(this, "No hay clientes registrados. Crea uno primero.", Toast.LENGTH_LONG).show();
            return;
        }

        List<String> nombresClientes = new ArrayList<>();
        nombresClientes.add("Selecciona un cliente");
        for (Cliente c : listaClientes) {
            nombresClientes.add(c.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nombresClientes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCliente.setAdapter(adapter);

        spinnerCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    clienteSeleccionado = null;
                    limpiarDatosCliente();
                } else {
                    clienteSeleccionado = listaClientes.get(position - 1);
                    mostrarDatosCliente(clienteSeleccionado);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void cargarProductos() {
        listaProductos = db.productoDao().getDisponibles();

        if (listaProductos.isEmpty()) {
            Toast.makeText(this, "No hay productos disponibles. Crea uno primero.", Toast.LENGTH_LONG).show();
        }
    }

    private void mostrarDatosCliente(Cliente cliente) {
        etDireccion.setText(cliente.getDireccion());
        etLocalidad.setText(cliente.getLocalidad());
        etCodigoPostal.setText(cliente.getCodigoPostal());
    }

    private void limpiarDatosCliente() {
        etDireccion.setText("");
        etLocalidad.setText("");
        etCodigoPostal.setText("");
    }

    private void setupListeners() {
        btnAnadirProducto.setOnClickListener(v -> agregarNuevoProducto());
        btnCancelar.setOnClickListener(v -> finish());
        btnGuardar.setOnClickListener(v -> guardarReparacion());
        btnVerTodasReparaciones.setOnClickListener(v -> {
            Intent intent = new Intent(NuevaReparacionActivity.this, ListaReparacionesActivity.class);
            startActivity(intent);
        });
    }

    private void agregarNuevoProducto() {
        if (listaProductos.isEmpty()) {
            Toast.makeText(this, "No hay productos disponibles", Toast.LENGTH_SHORT).show();
            return;
        }

        View itemView = LayoutInflater.from(this).inflate(R.layout.item_producto_venta, containerProductos, false);

        Spinner spinnerProducto = itemView.findViewById(R.id.spinnerProducto);
        EditText etCantidad = itemView.findViewById(R.id.etCantidad);
        Button btnEliminar = itemView.findViewById(R.id.btnEliminar);

        List<String> nombresProductos = new ArrayList<>();
        nombresProductos.add("Selecciona un producto");
        for (Producto p : listaProductos) {
            nombresProductos.add(p.getNombre() + " - $" + p.getPrecio());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nombresProductos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProducto.setAdapter(adapter);

        spinnerProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calcularTotales();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        etCantidad.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcularTotales();
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        btnEliminar.setOnClickListener(v -> {
            containerProductos.removeView(itemView);
            calcularTotales();
        });

        containerProductos.addView(itemView);
    }

    private void calcularTotales() {
        double subtotal = 0;

        for (int i = 0; i < containerProductos.getChildCount(); i++) {
            View itemView = containerProductos.getChildAt(i);
            Spinner spinnerProducto = itemView.findViewById(R.id.spinnerProducto);
            EditText etCantidad = itemView.findViewById(R.id.etCantidad);

            int posicionProducto = spinnerProducto.getSelectedItemPosition();
            if (posicionProducto > 0) {
                Producto producto = listaProductos.get(posicionProducto - 1);
                String cantidadStr = etCantidad.getText().toString();
                int cantidad = cantidadStr.isEmpty() ? 0 : Integer.parseInt(cantidadStr);

                subtotal += producto.getPrecio() * cantidad;
            }
        }

        tvSubtotal.setText("$" + String.format(Locale.getDefault(), "%.2f", subtotal));
        tvTotal.setText("$" + String.format(Locale.getDefault(), "%.2f", subtotal));
    }

    private void guardarReparacion() {
        // Validar cliente
        if (clienteSeleccionado == null) {
            Toast.makeText(this, "Debes seleccionar un cliente", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar descripción
        String descripcion = etDescripcion.getText().toString().trim();
        if (descripcion.isEmpty()) {
            Toast.makeText(this, "Debes agregar una descripción del trabajo", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que haya al menos un producto
        productosEnReparacion.clear();
        for (int i = 0; i < containerProductos.getChildCount(); i++) {
            View itemView = containerProductos.getChildAt(i);
            Spinner spinnerProducto = itemView.findViewById(R.id.spinnerProducto);
            EditText etCantidad = itemView.findViewById(R.id.etCantidad);

            int posicionProducto = spinnerProducto.getSelectedItemPosition();
            if (posicionProducto > 0) {
                Producto producto = listaProductos.get(posicionProducto - 1);
                String cantidadStr = etCantidad.getText().toString();
                int cantidad = cantidadStr.isEmpty() ? 0 : Integer.parseInt(cantidadStr);

                if (cantidad > 0) {
                    productosEnReparacion.add(new ProductoReparacion(producto, cantidad));
                }
            }
        }

        if (productosEnReparacion.isEmpty()) {
            Toast.makeText(this, "Debes agregar al menos un producto", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calcular totales
        double subtotal = 0;
        for (ProductoReparacion pr : productosEnReparacion) {
            subtotal += pr.getSubtotal();
        }
        double total = subtotal;

        // Crear JSON de productos
        StringBuilder productosJson = new StringBuilder("[");
        for (int i = 0; i < productosEnReparacion.size(); i++) {
            ProductoReparacion pr = productosEnReparacion.get(i);
            productosJson.append("{\"nombre\":\"").append(pr.getProducto().getNombre())
                    .append("\",\"precio\":").append(pr.getProducto().getPrecio())
                    .append(",\"cantidad\":").append(pr.getCantidad())
                    .append("}");
            if (i < productosEnReparacion.size() - 1) {
                productosJson.append(",");
            }
        }
        productosJson.append("]");

        // Obtener fecha actual
        String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Guardar reparación
        Reparacion reparacion = new Reparacion(clienteSeleccionado.getId(), fecha, descripcion, subtotal, total, productosJson.toString());
        long id = db.reparacionDao().insert(reparacion);

        if (id > 0) {
            // Actualizar stock de productos
            for (ProductoReparacion pr : productosEnReparacion) {
                Producto p = pr.getProducto();
                p.setCantidad(p.getCantidad() - pr.getCantidad());
                db.productoDao().update(p);
            }

            Toast.makeText(this, "¡Reparación guardada con éxito!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al guardar la reparación", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupBottomNavigation() {
        findViewById(R.id.nav_inicio).setOnClickListener(v -> finish());

        findViewById(R.id.nav_ventas).setOnClickListener(v -> {
            Intent intent = new Intent(NuevaReparacionActivity.this, NuevaVentaActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_reparaciones).setOnClickListener(v -> {
            // Ya estamos aquí
        });

        findViewById(R.id.nav_clientes).setOnClickListener(v -> {
            Intent intent = new Intent(NuevaReparacionActivity.this, ClientesActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_productos).setOnClickListener(v -> {
            Intent intent = new Intent(NuevaReparacionActivity.this, ProductosActivity.class);
            startActivity(intent);
        });
    }
}