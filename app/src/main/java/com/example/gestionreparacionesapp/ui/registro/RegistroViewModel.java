package com.example.gestionreparacionesapp.ui.registro;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gestionreparacionesapp.data.db.AppDatabase;
import com.example.gestionreparacionesapp.data.repository.UserRepository;

/**
 * ViewModel de Registro: corre el insert en hilo de fondo y reporta al LiveData.
 */
public class RegistroViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<RegistroResult> registroResult = new MutableLiveData<>();

    public RegistroViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<RegistroResult> getRegistroResult() {
        return registroResult;
    }

    public void onRegistroClicked(String nombre, String email, String password, String telefono) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            String error = userRepository.registerUser(nombre, email, password, telefono);
            if (error == null) {
                registroResult.postValue(RegistroResult.success());
            } else {
                registroResult.postValue(RegistroResult.error(error));
            }
        });
    }
}
