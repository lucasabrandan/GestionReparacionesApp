package com.example.gestionreparacionesapp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gestionreparacionesapp.data.db.entity.User;

/**
 * DAO = “Data Access Object”.
 * Define operaciones SQL tipadas para la entidad User.
 */
@Dao
public interface UserDao {

    /**
     * Inserta un usuario nuevo.
     * ABORT: si el email ya existe (índice unique), Room lanza SQLiteConstraintException.
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(User user);

    /**
     * Busca por email (para login o validaciones).
     * Devuelve null si no existe.
     */
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);
}
