package com.example.gestionreparacionesapp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestionreparacionesapp.data.db.entity.Venta;

import java.util.List;

@Dao
public interface VentaDao {

    @Insert
    long insert(Venta venta);

    @Update
    void update(Venta venta);

    @Delete
    void delete(Venta venta);

    @Query("SELECT * FROM ventas ORDER BY id DESC")
    List<Venta> getAll();

    @Query("SELECT * FROM ventas WHERE id = :id LIMIT 1")
    Venta getById(int id);

    @Query("SELECT * FROM ventas WHERE clienteId = :clienteId ORDER BY fecha DESC")
    List<Venta> getByCliente(int clienteId);

    @Query("SELECT * FROM ventas WHERE fecha = :fecha ORDER BY id DESC")
    List<Venta> getByFecha(String fecha);
}