package com.example.gestionreparacionesapp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.gestionreparacionesapp.data.db.entity.User;

/**
 * DAO (Data Access Object) para la entidad User.
 * Aquí se definen todas las operaciones de base de datos para los usuarios.
 */
@Dao
public interface UserDao {

    /**
     * Inserta un nuevo usuario en la base de datos.
     * Si el email ya existe (definido por el @Index en User), abortará la transacción.
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(User user);

    /**
     * Busca un usuario por su dirección de email.
     * Esta es la consulta que necesitamos para el método de login.
     * @param email El email del usuario a buscar.
     * @return El objeto User si se encuentra, o null si no existe.
     */
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    /**
     * ¡NUEVO MÉTODO!
     * Busca un usuario por su ID (clave primaria).
     * Lo usaremos en el futuro para cargar perfiles de usuario, etc.
     * @param userId El ID del usuario a buscar.
     * @return El objeto User si se encuentra, o null si no existe.
     */
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    User findUserById(int userId);
}