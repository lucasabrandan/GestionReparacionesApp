package com.example.gestionreparacionesapp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestionreparacionesapp.data.db.entity.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Insert
    long insert(Usuario usuario);

    @Update
    void update(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    Usuario login(String email, String password);

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    Usuario getByEmail(String email);

    @Query("SELECT * FROM usuarios WHERE usuario = :usuario LIMIT 1")
    Usuario getByUsuario(String usuario);

    @Query("SELECT * FROM usuarios WHERE recordarme = 1 LIMIT 1")
    Usuario getUsuarioRecordado();

    @Query("UPDATE usuarios SET recordarme = 0")
    void limpiarRecordarme();

    @Query("SELECT * FROM usuarios")
    List<Usuario> getAll();
}