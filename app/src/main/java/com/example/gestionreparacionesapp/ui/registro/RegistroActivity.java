package com.example.gestionreparacionesapp.ui.registro;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionreparacionesapp.databinding.ActivityRegistroBinding;

/**
 * Pantalla de registro de usuario.
 * - Usa ViewBinding (activity_registro.xml)
 * - Evita edge-to-edge para que el título no choque con la cámara.
 * - Validaciones mínimas de ejemplo.
 */
public class RegistroActivity extends AppCompatActivity {

    private ActivityRegistroBinding binding;
    private boolean pwdVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // SIN EdgeToEdge.enable(...) para que respetemos el status bar y notch.
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // --- AppBar simple con back ---
        setSupportActionBar(binding.topAppBar);
        binding.topAppBar.setNavigationOnClickListener(v -> onBackPressed());

        // --- Alternar visibilidad de contraseña ---
        binding.tilPassword.setEndIconOnClickListener(v -> togglePassword());

        // --- Botón Registrar ---
        binding.btnRegistrarme.setOnClickListener(v -> onRegisterClicked());

        // --- Botón Cancelar ---
        binding.btnCancelar.setOnClickListener(v -> onBackPressed());
    }

    private void togglePassword() {
        pwdVisible = !pwdVisible;
        if (pwdVisible) {
            binding.etPassword.setTransformationMethod(null);
        } else {
            binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        binding.etPassword.setSelection(binding.etPassword.getText().length());
    }

    private void onRegisterClicked() {
        String nombre = binding.etNombre.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String email2 = binding.etEmail2.getText().toString().trim();
        String pwd = binding.etPassword.getText().toString();
        String tel = binding.etTelefono.getText().toString().trim();

        // Validaciones rápidas (puedes reemplazar por tus reglas)
        if (nombre.isEmpty()) {
            binding.tilNombre.setError("Ingrese su nombre completo");
            return;
        } else binding.tilNombre.setError(null);

        if (email.isEmpty()) {
            binding.tilEmail.setError("Ingrese su correo");
            return;
        } else binding.tilEmail.setError(null);

        if (!email.equals(email2)) {
            binding.tilEmail2.setError("El correo no coincide");
            return;
        } else binding.tilEmail2.setError(null);

        if (pwd.length() < 8) {
            binding.tilPassword.setError("Mínimo 8 caracteres");
            return;
        } else binding.tilPassword.setError(null);

        if (tel.isEmpty()) {
            binding.tilTelefono.setError("Ingrese su teléfono");
            return;
        } else binding.tilTelefono.setError(null);

        // Aquí iría la llamada a tu ViewModel/Repository para registrar.
        Toast.makeText(this, "¡Registro OK (demo)!", Toast.LENGTH_SHORT).show();
        finish(); // volver al login
    }
}
