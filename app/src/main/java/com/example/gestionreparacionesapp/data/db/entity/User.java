package com.example.gestionreparacionesapp.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Define la tabla "users" en la base de datos de Room.
 * Esta es la versión FINAL que usamos en la arquitectura.
 */
// 'indices' asegura que no se puedan registrar dos usuarios con el mismo email.
@Entity(tableName = "users", indices = {@Index(value = {"email"}, unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    // Ajuste de UX: Usamos "nombreCompleto" para mostrar, en lugar de "username".
    @NonNull
    @ColumnInfo(name = "nombre_completo")
    public String nombreCompleto;

    @NonNull
    public String email;

    // Ajuste de Seguridad: Guardamos el HASH, no la contraseña en texto plano.
    @NonNull
    @ColumnInfo(name = "hashed_password")
    public String hashedPassword;

    @NonNull
    public String telefono;

    // Constructor vacío (Room lo necesita)
    public User() {}

    // Constructor público (lo usamos nosotros para crear usuarios)
    // Fíjate cómo coincide con los campos de arriba.
    public User(@NonNull String nombreCompleto, @NonNull String email, @NonNull String hashedPassword, @NonNull String telefono) {
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.telefono = telefono;
    }

    // --- Getters (Room también los usa) ---

    public int getId() {
        return id;
    }

    @NonNull
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getHashedPassword() {
        return hashedPassword;
    }

    @NonNull
    public String getTelefono() {
        return telefono;
    }
}