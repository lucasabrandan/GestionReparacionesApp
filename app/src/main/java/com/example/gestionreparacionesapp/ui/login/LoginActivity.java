package com.example.gestionreparacionesapp.ui.login;

// --- AQUÍ ESTÁN TODOS LOS IMPORTS ---
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
// Este es el import clave que se genera solo
import com.example.gestionreparacionesapp.databinding.ActivityLoginBinding;
// ------------------------------------

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

        // CASO 1: Campos vacíos (Error)
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();

            if (email.isEmpty()) {
                binding.tilEmail.setError("Email requerido");
            } else {
                binding.tilEmail.setError(null);
            }

            if (password.isEmpty()) {
                binding.tilPassword.setError("Contraseña requerida");
            } else {
                binding.tilPassword.setError(null);
            }
            return;
        } else {
            binding.tilEmail.setError(null);
            binding.tilPassword.setError(null);
        }

        // CASO 2: Login Fallido (Lógica de ejemplo)
        if (email.equals("admin@admin.com") && password.equals("123")) {
            Toast.makeText(this, "Login Fallido: Contraseña incorrecta", Toast.LENGTH_LONG).show();
            binding.tilPassword.setError("Contraseña incorrecta");
            return;
        }

        // CASO 3: Login Exitoso (Lógica de ejemplo)
        Toast.makeText(this, "¡Login Exitoso! Bienvenido " + email, Toast.LENGTH_LONG).show();

        // TODO: Aquí iría el código para navegar a la pantalla "Home"
        // Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        // startActivity(intent);
        // finish();
    }
}
