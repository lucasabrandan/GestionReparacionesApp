package com.example.gestionreparacionesapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns; // <-- 1. IMPORTAMOS LA HERRAMIENTA DE VALIDACIÓN DE EMAIL
import android.view.View;
import android.widget.Toast;
import com.example.gestionreparacionesapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// ... (El código de onCreate sigue igual) ...
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
                // TODO: Aquí iría el código para abrir la pantalla de Registro
                // Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                // startActivity(intent);
                Toast.makeText(LoginActivity.this, "Ir a Registro...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Valida las credenciales ingresadas por el usuario.
     */
    private void validarLogin() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString();

        // Limpiamos errores previos
        binding.tilEmail.setError(null);
        binding.tilPassword.setError(null);

        // --- VALIDACIONES MEJORADAS ---

        // CASO 1: Campos vacíos
        if (email.isEmpty() || password.isEmpty()) {
            if (email.isEmpty()) {
                binding.tilEmail.setError("Email requerido");
            }
            if (password.isEmpty()) {
                binding.tilPassword.setError("Contraseña requerida");
            }
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // CASO 2: Email no tiene formato válido
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setError("Formato de email inválido");
            Toast.makeText(this, "Por favor, ingresa un email válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // CASO 3: Contraseña muy corta
        if (password.length() < 6) {
            binding.tilPassword.setError("La contraseña debe tener al menos 6 caracteres");
            Toast.makeText(this, "Contraseña demasiado corta", Toast.LENGTH_SHORT).show();
            return;
        }

        // --- LÓGICA DE AUTENTICACIÓN (CORREGIDA) ---
        // (Esto es temporal. Luego lo hará el ViewModel contra el Repository)

        // CASO 4: Login Exitoso
        if (email.equals("admin@admin.com") && password.equals("123456")) {

            Toast.makeText(this, "¡Login Exitoso! Bienvenido " + email, Toast.LENGTH_LONG).show();

            // TODO: Aquí iría el código para navegar a la pantalla "Home"
            // Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            // startActivity(intent);
            // finish();

            // CASO 5: Login Fallido (Credenciales incorrectas)
        } else {
            binding.tilPassword.setError("Credenciales incorrectas");
            Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_LONG).show();
        }
    }
}