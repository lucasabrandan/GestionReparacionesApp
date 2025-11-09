package com.example.gestionreparacionesapp.ui.login;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.gestionreparacionesapp.data.repository.UserRepository;
import com.example.gestionreparacionesapp.data.db.entity.User;

/**
 * El "Cerebro" de la LoginActivity.
 * Contiene toda la lógica de negocio y se comunica con el Repositorio.
 */
public class LoginViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    // LiveData privado que solo el ViewModel puede modificar
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        // Obtiene la instancia Singleton del Repositorio
        this.userRepository = UserRepository.getInstance(application.getApplicationContext());
    }

    /**
     * LiveData público que la Activity "observa".
     * No es mutable, para que la Activity no pueda cambiarlo.
     */
    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    /**
     * La Activity llama a este método cuando el usuario presiona "Ingresar".
     */
    public void onLoginClicked(String email, String password) {
        // 1. Validación de campos vacíos
        if (email.isEmpty() || password.isEmpty()) {
            // Publica un resultado de "Error" en el LiveData
            // CORREGIDO: Usamos el método de fábrica estático para errores.
            loginResult.setValue(LoginResult.error("Por favor, complete todos los campos"));
            return;
        }

        // 2. Llama a la lógica de negocio en el Repositorio
        // Asumiendo que UserRepository devuelve un objeto User o null
        User user = userRepository.loginUser(email, password);

        // 3. Publica el resultado (éxito o error) en el LiveData
        if (user != null) {
            // Éxito: publica el "sobre" de éxito con el nombre
            // CORREGIDO: Usamos el método de fábrica estático para éxito.
            // Y usamos el campo "getNombreCompleto()" de User.java
            loginResult.setValue(LoginResult.success(user.getNombreCompleto()));
        } else {
            // Error: publica el "sobre" de error
            // CORREGIDO: Usamos el método de fábrica estático para errores.
            loginResult.setValue(LoginResult.error("Email o contraseña incorrectos"));
        }
    }
}