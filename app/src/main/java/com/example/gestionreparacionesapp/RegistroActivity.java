package com.example.gestionreparacionesapp;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionreparacionesapp.data.db.AppDatabase;
import com.example.gestionreparacionesapp.data.db.entity.Usuario;

import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombreCompleto, etUsuario, etCorreo, etConfirmarCorreo, etContrasena, etTelefono;
    private Button btnCancelar, btnContinuar;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = AppDatabase.getInstance(this);

        initViews();
        setupListeners();
    }

    private void initViews() {
        etNombreCompleto = findViewById(R.id.etNombreCompleto);
        etUsuario = findViewById(R.id.etUsuario);
        etCorreo = findViewById(R.id.etCorreo);
        etConfirmarCorreo = findViewById(R.id.etConfirmarCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        etTelefono = findViewById(R.id.etTelefono);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnContinuar = findViewById(R.id.btnContinuar);
    }

    private void setupListeners() {
        btnCancelar.setOnClickListener(v -> finish());
        btnContinuar.setOnClickListener(v -> registrar());
    }

    private void registrar() {
        String nombre = etNombreCompleto.getText().toString().trim();
        String usuario = etUsuario.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String confirmarCorreo = etConfirmarCorreo.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();

        // Validaciones
        if (nombre.isEmpty() || usuario.isEmpty() || correo.isEmpty() ||
                confirmarCorreo.isEmpty() || contrasena.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!correo.equals(confirmarCorreo)) {
            Toast.makeText(this, "Los correos no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar contraseña: mínimo 8 caracteres, alfanuméricos y 1 signo
        if (!validarContrasena(contrasena)) {
            Toast.makeText(this, "La contraseña debe tener mínimo 8 caracteres, letras, números y 1 signo", Toast.LENGTH_LONG).show();
            return;
        }

        // Verificar si el email ya existe
        if (db.usuarioDao().getByEmail(correo) != null) {
            Toast.makeText(this, "Este correo ya está registrado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el usuario ya existe
        if (db.usuarioDao().getByUsuario(usuario) != null) {
            Toast.makeText(this, "Este usuario ya está registrado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardar usuario
        Usuario nuevoUsuario = new Usuario(nombre, usuario, correo, contrasena, telefono);
        long id = db.usuarioDao().insert(nuevoUsuario);

        if (id > 0) {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarContrasena(String contrasena) {
        // Mínimo 8 caracteres, al menos una letra, un número y un símbolo
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
        return pattern.matcher(contrasena).matches();
    }
}