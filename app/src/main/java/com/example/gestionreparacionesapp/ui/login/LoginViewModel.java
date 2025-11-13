package com.example.gestionreparacionesapp.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gestionreparacionesapp.data.db.AppDatabase;
import com.example.gestionreparacionesapp.data.db.entity.User;
import com.example.gestionreparacionesapp.data.repository.UserRepository;

/**
 * ViewModel de Login: valida inputs y ejecuta el login en hilo de fondo.
 */
public class LoginViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void onLoginClicked(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            loginResult.setValue(LoginResult.error("Por favor, complete todos los campos"));
            return;
        }

        // Ejecutar la consulta en hilo de fondo
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                User user = userRepository.loginUser(email, password);
                if (user != null) {
                    loginResult.postValue(LoginResult.success(user.getNombreCompleto()));
                } else {
                    loginResult.postValue(LoginResult.error("Email o contraseña incorrectos"));
                }
            } catch (Exception e) {
                loginResult.postValue(LoginResult.error("Error de login: " + e.getMessage()));
            }
        });
    }
}
