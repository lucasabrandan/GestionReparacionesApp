package com.example.gestionreparacionesapp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestionreparacionesapp.data.db.entity.Producto;

import java.util.List;

@Dao
public interface ProductoDao {

    @Insert
    long insert(Producto producto);

    @Update
    void update(Producto producto);

    @Delete
    void delete(Producto producto);

    @Query("SELECT * FROM productos ORDER BY nombre ASC")
    List<Producto> getAll();

    @Query("SELECT * FROM productos WHERE id = :id LIMIT 1")
    Producto getById(int id);

    @Query("SELECT * FROM productos WHERE nombre LIKE '%' || :nombre || '%'")
    List<Producto> buscarPorNombre(String nombre);

    @Query("SELECT * FROM productos WHERE cantidad > 0 ORDER BY nombre ASC")
    List<Producto> getDisponibles();
}