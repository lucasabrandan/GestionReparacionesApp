package com.example.gestionreparacionesapp.ui.registro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import com.example.gestionreparacionesapp.databinding.ActivityRegistroBinding; // Importante
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    private ActivityRegistroBinding binding;

    // Expresión Regular para una contraseña segura
    // (min 8 caract, 1 letra, 1 número, 1 símbolo especial)
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // al menos 1 número
                    "(?=.*[a-zA-Z])" +      // cualquier letra
                    "(?=.*[!@#$%^&+=_])" + // al menos 1 símbolo especial
                    "(?=\\S+$)" +           // sin espacios en blanco
                    ".{8,}" +               // al menos 8 caracteres
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Listener para el botón Continuar
        binding.btnContinuar.setOnClickListener(v -> {
            if (validarRegistro()) {
                // TODO: Aquí iría la lógica para guardar el usuario (con ViewModel y Repository)
                Toast.makeText(this, "¡Registro Exitoso!", Toast.LENGTH_LONG).show();

                // Cierra la pantalla de Registro y vuelve al Login
                finish();
            }
        });

        // Listener para el botón Cancelar
        binding.btnCancelar.setOnClickListener(v -> {
            // Simplemente cierra esta pantalla y vuelve al Login
            finish();
        });
    }

    /**
     * Valida todos los campos del formulario de registro.
     * @return true si todos los campos son válidos, false en caso contrario.
     */
    private boolean validarRegistro() {
        // Limpiamos todos los errores previos
        binding.tilNombreCompleto.setError(null);
        binding.tilEmailRegistro.setError(null);
        binding.tilEmailConfirm.setError(null);
        binding.tilPasswordRegistro.setError(null);
        binding.tilTelefono.setError(null);

        // Obtenemos los textos
        String nombre = binding.etNombreCompleto.getText().toString().trim();
        String email = binding.etEmailRegistro.getText().toString().trim();
        String emailConfirm = binding.etEmailConfirm.getText().toString().trim();
        String password = binding.etPasswordRegistro.getText().toString(); // No usamos trim()
        String telefono = binding.etTelefono.getText().toString().trim();

        boolean esValido = true;

        // 1. Validar Nombre
        if (nombre.isEmpty()) {
            binding.tilNombreCompleto.setError("Nombre requerido");
            esValido = false;
        }

        // 2. Validar Email
        if (email.isEmpty()) {
            binding.tilEmailRegistro.setError("Email requerido");
            esValido = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmailRegistro.setError("Formato de email inválido");
            esValido = false;
        }

        // 3. Validar Confirmación de Email
        if (emailConfirm.isEmpty()) {
            binding.tilEmailConfirm.setError("Confirmación de email requerida");
            esValido = false;
        } else if (!email.equals(emailConfirm)) {
            binding.tilEmailConfirm.setError("Los emails no coinciden");
            esValido = false;
        }

        // 4. Validar Contraseña
        if (password.isEmpty()) {
            binding.tilPasswordRegistro.setError("Contraseña requerida");
            esValido = false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            binding.tilPasswordRegistro.setError("La contraseña no cumple los requisitos (min. 8 caract, 1 número, 1 símbolo)");
            esValido = false;
        }

        // 5. Validar Teléfono
        if (telefono.isEmpty()) {
            binding.tilTelefono.setError("Teléfono requerido");
            esValido = false;
        } else if (telefono.length() < 9) {
            // Usamos una validación simple de longitud
            binding.tilTelefono.setError("Teléfono inválido (debe tener al menos 9 dígitos)");
            esValido = false;
        }

        if (!esValido) {
            Toast.makeText(this, "Por favor, corrige los errores", Toast.LENGTH_SHORT).show();
        }

        return esValido;
    }
}