package com.example.gestionreparacionesapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.gestionreparacionesapp.databinding.ActivityLoginBinding;
import com.example.gestionreparacionesapp.ui.registro.RegistroActivity;

/**
 * Pantalla principal de inicio de sesión.
 * Incluye soporte para modo claro/oscuro, manejo del teclado y padding seguro.
 */
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflamos el layout con ViewBinding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configuración para respetar notch, barra de estado y navegación
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sysBars.left, sysBars.top, sysBars.right, sysBars.bottom);
            return insets;
        });

        // Inicializar ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // === Configuración del switch Día/Noche ===
        boolean isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        binding.switchTheme.setChecked(isDarkMode);

        binding.switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(this, "Modo oscuro activado 🌙", Toast.LENGTH_SHORT).show();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Toast.makeText(this, "Modo claro activado ☀️", Toast.LENGTH_SHORT).show();
            }
        });

        // === Botón Ingresar ===
        binding.btnIngresar.setOnClickListener(v -> {
            String email = binding.etEmail.getText() != null ? binding.etEmail.getText().toString().trim() : "";
            String password = binding.etPassword.getText() != null ? binding.etPassword.getText().toString().trim() : "";

            // Validación rápida antes de delegar al ViewModel
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            loginViewModel.onLoginClicked(email, password);
        });

        // === Botón Crear cuenta ===
        binding.btnCrearCuenta.setOnClickListener(v ->
                startActivity(new Intent(this, com.example.gestionreparacionesapp.ui.registro.RegistroActivity.class))
        );


        // === Observador del resultado de login ===
        loginViewModel.getLoginResult().observe(this, result -> {
            if (result == null) return;

            if (result.isError()) {
                Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "¡Bienvenido, " + result.getSuccessUserName() + "!", Toast.LENGTH_LONG).show();

                // TODO: Aquí podrías lanzar tu HomeActivity cuando esté lista
                // Intent intent = new Intent(this, HomeActivity.class);
                // startActivity(intent);
                // finish();
            }
        });
    }
}
