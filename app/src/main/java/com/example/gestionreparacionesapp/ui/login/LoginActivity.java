package com.example.gestionreparacionesapp.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionreparacionesapp.R;
import com.example.gestionreparacionesapp.data.db.AppDatabase;
import com.example.gestionreparacionesapp.data.db.entity.Usuario;
import com.example.gestionreparacionesapp.ui.home.HomeActivity;
import com.example.gestionreparacionesapp.ui.registro.RegistroActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText etEmail, etPassword;
    private SwitchMaterial switchRecordarme;
    private MaterialButton btnIngresar;
    private TextView tvRegistrate, tvRecuperar;

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
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        switchRecordarme = findViewById(R.id.switchRecordarme);
        btnIngresar = findViewById(R.id.btnIngresar);
        tvRegistrate = findViewById(R.id.tvRegistrate);
        tvRecuperar = findViewById(R.id.tvRecuperar);
    }

    private void checkRecordarme() {
        Usuario usuario = db.usuarioDao().getUsuarioRecordado();
        if (usuario != null) {
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

        tvRecuperar.setOnClickListener(v ->
                Toast.makeText(this, "Funcionalidad de recuperación en desarrollo", Toast.LENGTH_SHORT).show()
        );
    }

    private void login() {
        // Limpiamos errores previos
        tilEmail.setError(null);
        tilPassword.setError(null);

        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString() : "";

        // === VALIDACIONES UX ===
        if (email.isEmpty()) {
            tilEmail.setError("Ingresá tu email");
            tilEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Ingresá un email válido");
            tilEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            tilPassword.setError("Ingresá tu contraseña");
            tilPassword.requestFocus();
            return;
        }

        // === CONSULTA A LA BASE ===
        // TODO: cuando apliquemos hash, comparar hash contra hash
        Usuario usuario = db.usuarioDao().findByEmail(email);

        if (usuario == null) {
            tilEmail.setError("Este email no está registrado");
            tilEmail.requestFocus();
            return;
        }

        // Contraseña incorrecta
        if (!usuario.getPassword().equals(password)) {
            tilPassword.setError("Contraseña incorrecta");
            tilPassword.requestFocus();
            return;
        }

        // === LOGIN EXITOSO ===
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
    }
}
