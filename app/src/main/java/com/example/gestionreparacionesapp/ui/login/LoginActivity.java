package com.example.gestionreparacionesapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable; // <-- IMPORTAR ESTO
import android.text.TextWatcher; // <-- IMPORTAR ESTO
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import com.example.gestionreparacionesapp.databinding.ActivityLoginBinding;
import com.example.gestionreparacionesapp.ui.registro.RegistroActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configura el listener para el botón "Ingresar"
        binding.btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarLogin();
            }
        });

        // Configura el listener para "Registrate"
        binding.tvRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        // === ¡¡NUEVO MÉTODO PARA CORREGIR UX!! ===
        setupTextWatchers();
    }

    /**
     * Añade TextWatchers a los campos para limpiar los errores automáticamente
     * en cuanto el usuario empieza a escribir.
     */
    private void setupTextWatchers() {
        // TextWatcher para el Email
        binding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No es necesario
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // En cuanto el texto cambia, borramos el error
                if (binding.tilEmail.getError() != null) {
                    binding.tilEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No es necesario
            }
        });

        // TextWatcher para la Contraseña
        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No es necesario
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // En cuanto el texto cambia, borramos el error
                if (binding.tilPassword.getError() != null) {
                    binding.tilPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No es necesario
            }
        });
    }

    /**
     * Valida las credenciales ingresadas por el usuario.
     */
    private void validarLogin() {
        // Obtenemos el texto de los campos
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString();

        // Limpiamos errores previos
        binding.tilEmail.setError(null);
        binding.tilPassword.setError(null);

        // 1. Validar campos vacíos
        if (email.isEmpty()) {
            binding.tilEmail.setError("Email requerido");
            return; // Corta la ejecución
        }
        if (password.isEmpty()) {
            binding.tilPassword.setError("Contraseña requerida");
            return; // Corta la ejecución
        }

        // 2. Validar formato de Email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setError("Formato de email inválido");
            return;
        }

        // 3. Validar longitud de Contraseña
        if (password.length() < 6) {
            binding.tilPassword.setError("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        // 4. Lógica de Autenticación (Temporal "Hardcodeada")
        // TODO: Reemplazar esto con la lógica del ViewModel (MVVM)
        if (email.equals("admin@admin.com") && password.equals("123456")) {
            // LOGIN EXITOSO
            Toast.makeText(this, "¡Login Exitoso! Bienvenido.", Toast.LENGTH_LONG).show();

            // TODO: Navegar a la pantalla "HomeActivity"
            // Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            // startActivity(intent);
            // finish(); // Cierra el Login

        } else {
            // LOGIN FALLIDO
            Toast.makeText(this, "Error: Email o contraseña incorrectos", Toast.LENGTH_LONG).show();
            binding.tilEmail.setError(" "); // Marca el error sin texto
            binding.tilPassword.setError(" "); // Marca el error sin texto
        }
    }
}