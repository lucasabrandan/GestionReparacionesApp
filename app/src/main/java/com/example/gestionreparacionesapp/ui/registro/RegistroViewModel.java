package com.example.gestionreparacionesapp.ui.registro;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.gestionreparacionesapp.data.repository.UserRepository;

public class RegistroViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<RegistroResult> registroResult = new MutableLiveData<>();

    public RegistroViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = UserRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<RegistroResult> getRegistroResult() {
        return registroResult;
    }

    public void onRegistroClicked(String nombre, String email, String password, String telefono) {
        // La Activity ya validó los campos vacíos, de formato, etc.
        // Aquí solo manejamos la lógica de negocio (llamar al Repositorio).

        try {
            // El Repositorio se encarga de hashear la contraseña
            userRepository.registerUser(nombre, email, password, telefono);

            // Si no hay excepción, el registro fue exitoso
            registroResult.setValue(RegistroResult.success());

        } catch (SQLiteConstraintException e) {
            // Capturamos el error si el email YA EXISTE (por el @Index(unique=true))
            registroResult.setValue(RegistroResult.error("El email ingresado ya está en uso."));
        } catch (Exception e) {
            // Otro error inesperado
            registroResult.setValue(RegistroResult.error("Error inesperado: " + e.getMessage()));
        }
    }
}