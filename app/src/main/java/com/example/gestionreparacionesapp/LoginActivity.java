package com.example.gestionreparacionesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.gestionreparacionesapp.data.db.AppDatabase;
import com.example.gestionreparacionesapp.data.db.entity.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private SwitchCompat switchRecordarme;
    private Button btnIngresar;
    private TextView tvRegistrate;
    private AppDatabase db;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = AppDatabase.getInstance(this);
        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);

        initViews();
        checkRecordarme();
        setupListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        switchRecordarme = findViewById(R.id.switchRecordarme);
        btnIngresar = findViewById(R.id.btnIngresar);
        tvRegistrate = findViewById(R.id.tvRegistrate);
    }

    private void checkRecordarme() {
        Usuario usuario = db.usuarioDao().getUsuarioRecordado();
        if (usuario != null) {
            // Si hay un usuario recordado, ir directo al Home
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("usuario_nombre", usuario.getNombreCompleto());
            intent.putExtra("usuario_id", usuario.getId());
            startActivity(intent);
            finish();
        }
    }

    private void setupListeners() {
        btnIngresar.setOnClickListener(v -> login());
        tvRegistrate.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = db.usuarioDao().login(email, password);

        if (usuario != null) {
            // Login exitoso
            if (switchRecordarme.isChecked()) {
                db.usuarioDao().limpiarRecordarme();
                usuario.setRecordarme(true);
                db.usuarioDao().update(usuario);
            }

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("usuario_nombre", usuario.getNombreCompleto());
            intent.putExtra("usuario_id", usuario.getId());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
}