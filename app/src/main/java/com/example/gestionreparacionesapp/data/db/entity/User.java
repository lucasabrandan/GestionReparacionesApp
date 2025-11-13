package com.example.gestionreparacionesapp.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Entidad Room que mapea la tabla "users".
 * - Índice único por email (evita usuarios duplicados).
 * - Guardamos la contraseña como HASH (no en texto plano).
 */
@Entity(
        tableName = "users",
        indices = {@Index(value = {"email"}, unique = true)}
)
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "nombre_completo")
    public String nombreCompleto;

    @NonNull
    public String email;

    @NonNull
    @ColumnInfo(name = "hashed_password")
    public String hashedPassword;

    @NonNull
    public String telefono;

    // Constructor vacío requerido por Room
    public User() {}

    // Constructor de conveniencia (lo usamos en el repositorio)
    public User(@NonNull String nombreCompleto,
                @NonNull String email,
                @NonNull String hashedPassword,
                @NonNull String telefono) {
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.telefono = telefono;
    }

    // Getters (útiles para ViewModels/Repos)
    public int getId() { return id; }
    @NonNull public String getNombreCompleto() { return nombreCompleto; }
    @NonNull public String getEmail() { return email; }
    @NonNull public String getHashedPassword() { return hashedPassword; }
    @NonNull public String getTelefono() { return telefono; }
}
