package com.example.gestionreparacionesapp.data.repository;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.example.gestionreparacionesapp.data.db.AppDatabase;
import com.example.gestionreparacionesapp.data.db.dao.UserDao;
import com.example.gestionreparacionesapp.data.db.entity.User;
import com.example.gestionreparacionesapp.data.utils.SecurityUtils;

/**
 * Repository: encapsula acceso a datos.
 * NO maneja hilos: el ViewModel decide en qué hilo correr.
 */
public class UserRepository {

    private static volatile UserRepository INSTANCE;
    private final UserDao userDao;

    private UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.userDao = db.userDao();
    }

    public static UserRepository getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRepository(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    /** Inserta usuario. Devuelve null si OK, o mensaje de error si falla. */
    public String registerUser(String nombre, String email, String password, String telefono) {
        try {
            String hashed = SecurityUtils.hashPassword(password);
            User newUser = new User(nombre, email, hashed, telefono);
            userDao.insert(newUser); // <-- operación de DB (ejecutar en hilo de fondo)
            return null;
        } catch (SQLiteConstraintException e) {
            return "El email ingresado ya está registrado.";
        } catch (Exception e) {
            return "Ocurrió un error inesperado: " + e.getMessage();
        }
    }

    /** Devuelve el User si credenciales válidas; si no, null. */
    public User loginUser(String email, String plainPassword) {
        User user = userDao.getUserByEmail(email); // <-- operación de DB (hilo de fondo)
        if (user == null) return null;
        return SecurityUtils.checkPassword(plainPassword, user.getHashedPassword())
                ? user
                : null;
    }
}
