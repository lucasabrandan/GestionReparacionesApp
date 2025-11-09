package com.example.gestionreparacionesapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import com.example.gestionreparacionesapp.databinding.ActivityLoginBinding;
import com.example.gestionreparacionesapp.ui.registro.RegistroActivity;

/**
 * La Vista (Activity) para el Login.
 * Es "tonta": solo muestra la UI y le notifica al ViewModel
 * sobre las acciones del usuario.
 */
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Inicializa el ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // 2. Configura los listeners de la UI

        // Listener del botón Ingresar
        binding.btnIngresar.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString();
            // La Activity solo notifica al ViewModel
            loginViewModel.onLoginClicked(email, password);
        });

        // Listener para el texto de Registro
        binding.tvRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });

        // Configura los TextWatchers para limpiar errores (mejora de UX)
        setupTextWatchers();

        // 3. Observa los cambios del ViewModel
        loginViewModel.getLoginResult().observe(this, result -> {
            if (result == null) return; // Ignora si es nulo

            if (result.isError()) {
                // Error: Muestra Toast y marca campos
                Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_LONG).show();
                binding.tilEmail.setError(" "); // Marca el campo (sin texto, solo color)
                binding.tilPassword.setError(" ");
            } else {
                // Éxito: Muestra saludo y navega
                Toast.makeText(this, "¡Bienvenido, " + result.getSuccessUserName() + "!", Toast.LENGTH_LONG).show();

                // TODO: Navegar a HomeActivity
                // Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                // startActivity(intent);
                // finish(); // Cierra el Login para que no pueda volver con "atrás"
            }
        });
    }

    /**
     * Añade listeners a los campos de texto para limpiar los errores de validación
     * en cuanto el usuario empieza a escribir.
     */
    private void setupTextWatchers() {
        binding.etEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilEmail.setError(null); // Limpia el error
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilPassword.setError(null); // Limpia el error
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }
}