package com.example.gestionreparacionesapp.data.repository;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import com.example.gestionreparacionesapp.data.db.AppDatabase;
import com.example.gestionreparacionesapp.data.db.dao.UserDao;
import com.example.gestionreparacionesapp.data.db.entity.User;
import com.example.gestionreparacionesapp.data.utils.SecurityUtils;

/**
 * Repositorio para manejar los datos de Usuario.
 * Sigue el patrón Repository, centralizando el acceso a datos.
 * Es el ÚNICO que sabe si los datos vienen de una DB (Room) o una API.
 */
public class UserRepository {

    private final UserDao userDao;
    private static volatile UserRepository INSTANCE;

    // Constructor privado para el Singleton
    private UserRepository(Context context) {
        // Obtiene la instancia de la DB y el DAO
        AppDatabase db = AppDatabase.getDatabase(context); // Corregido a getDatabase
        this.userDao = db.userDao();
    }

    // Método Singleton para obtener la instancia del repositorio
    public static UserRepository getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRepository(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Lógica de negocio para registrar un usuario.
     * @return null si el registro es exitoso, o un String con el mensaje de error si falla.
     */
    public String registerUser(String nombre, String email, String password, String telefono) {
        try {
            // 1. Hashear la contraseña
            String hashedPassword = SecurityUtils.hashPassword(password);
            // 2. Crear el nuevo usuario (usando los campos correctos de User.java)
            User newUser = new User(nombre, email, hashedPassword, telefono);
            // 3. Intentar insertar en la DB
            AppDatabase.databaseWriteExecutor.execute(() -> { // Ejecutar en hilo de fondo
                userDao.insert(newUser);
            });
            return null; // Éxito
        } catch (SQLiteConstraintException e) {
            // Error: El email ya existe (definido por el @Index en User.java)
            return "El email ingresado ya está registrado.";
        } catch (Exception e) {
            return "Ocurrió un error inesperado: " + e.getMessage();
        }
    }

    /**
     * Lógica de negocio para iniciar sesión.
     * @return El objeto User si el login es exitoso, o null si falla.
     */
    public User loginUser(String email, String plainPassword) {
        // 1. Buscar al usuario por email
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            return null; // Usuario no encontrado
        }

        // 2. Verificar la contraseña hasheada
        // ¡¡AQUÍ ESTABA EL BUG!!
        // Corregido de user.getPassword() a user.getHashedPassword()
        if (SecurityUtils.checkPassword(plainPassword, user.getHashedPassword())) {
            return user; // Éxito: contraseña coincide
        } else {
            return null; // Error: contraseña incorrecta
        }
    }
}