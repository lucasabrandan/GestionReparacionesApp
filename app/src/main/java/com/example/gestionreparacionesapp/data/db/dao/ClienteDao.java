package com.example.gestionreparacionesapp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestionreparacionesapp.data.db.entity.Cliente;

import java.util.List;

@Dao
public interface ClienteDao {

    @Insert
    long insert(Cliente cliente);

    @Update
    void update(Cliente cliente);

    @Delete
    void delete(Cliente cliente);

    @Query("SELECT * FROM clientes ORDER BY nombre ASC")
    List<Cliente> getAll();

    @Query("SELECT * FROM clientes WHERE id = :id LIMIT 1")
    Cliente getById(int id);

    @Query("SELECT * FROM clientes WHERE nombre LIKE '%' || :nombre || '%'")
    List<Cliente> buscarPorNombre(String nombre);
}