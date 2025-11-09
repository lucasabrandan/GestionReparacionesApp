package com.example.gestionreparacionesapp.ui.registro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.Toast;
import com.example.gestionreparacionesapp.databinding.ActivityRegistroBinding;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    // Expresión regular para una contraseña segura
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // al menos 1 dígito
                    "(?=.*[a-z])" +         // al menos 1 minúscula
                    "(?=.*[A-Z])" +         // al menos 1 mayúscula
                    "(?=.*[@#$%^&+=!])" +   // al menos 1 símbolo
                    "(?=\\S+$)" +           // sin espacios en blanco
                    ".{8,}" +               // al menos 8 caracteres
                    "$");

    private ActivityRegistroBinding binding;
    private RegistroViewModel registroViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Inicializa el ViewModel
        registroViewModel = new ViewModelProvider(this).get(RegistroViewModel.class);

        // 2. Configura Listeners de la UI
        binding.btnContinuar.setOnClickListener(v -> {
            if (validarCamposLocalmente()) {
                // Si las validaciones de UI (locales) pasan, le mandamos los datos al ViewModel
                String nombre = binding.etNombreCompleto.getText().toString().trim();
                String email = binding.etEmailRegistro.getText().toString().trim();
                String password = binding.etPasswordRegistro.getText().toString();
                String telefono = binding.etTelefono.getText().toString().trim();

                registroViewModel.onRegistroClicked(nombre, email, password, telefono);
            }
        });

        binding.btnCancelar.setOnClickListener(v -> {
            finish(); // Cierra esta activity y vuelve al Login
        });

        // 3. Observa los cambios del ViewModel
        registroViewModel.getRegistroResult().observe(this, result -> {
            if (result == null) return;

            if (result.isError()) {
                // Error (ej. "Email ya existe")
                Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_LONG).show();
            } else {
                // Éxito
                Toast.makeText(this, "¡Registro Exitoso! Ahora puedes iniciar sesión.", Toast.LENGTH_LONG).show();
                finish(); // Cierra esta activity y vuelve al Login
            }
        });
    }

    /**
     * Valida la UI localmente antes de enviar los datos al ViewModel.
     * Muestra errores en los campos.
     */
    private boolean validarCamposLocalmente() {
        // Limpiamos errores anteriores
        binding.tilNombreCompleto.setError(null);
        binding.tilEmailRegistro.setError(null);
        binding.tilEmailConfirm.setError(null);
        binding.tilPasswordRegistro.setError(null);
        binding.tilTelefono.setError(null);

        String nombre = binding.etNombreCompleto.getText().toString().trim();
        String email = binding.etEmailRegistro.getText().toString().trim();
        String emailConfirm = binding.etEmailConfirm.getText().toString().trim();
        String password = binding.etPasswordRegistro.getText().toString();
        String telefono = binding.etTelefono.getText().toString().trim();

        boolean esValido = true;

        if (nombre.isEmpty()) {
            binding.tilNombreCompleto.setError("Nombre requerido");
            esValido = false;
        }

        if (email.isEmpty()) {
            binding.tilEmailRegistro.setError("Email requerido");
            esValido = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmailRegistro.setError("Email inválido");
            esValido = false;
        }

        if (emailConfirm.isEmpty()) {
            binding.tilEmailConfirm.setError("Confirme su email");
            esValido = false;
        } else if (!email.equals(emailConfirm)) {
            binding.tilEmailConfirm.setError("Los emails no coinciden");
            esValido = false;
        }

        if (password.isEmpty()) {
            binding.tilPasswordRegistro.setError("Contraseña requerida");
            esValido = false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            binding.tilPasswordRegistro.setError("La contraseña no cumple los requisitos de seguridad");
            esValido = false;
        }

        if (telefono.isEmpty()) {
            binding.tilTelefono.setError("Teléfono requerido");
            esValido = false;
        } else if (telefono.length() < 9) { // Ej: 1122334455 (10 dígitos)
            binding.tilTelefono.setError("Teléfono inválido");
            esValido = false;
        }

        return esValido;
    }
}