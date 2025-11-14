package com.example.gestionreparacionesapp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestionreparacionesapp.data.db.entity.Reparacion;

import java.util.List;

@Dao
public interface ReparacionDao {

    @Insert
    long insert(Reparacion reparacion);

    @Update
    void update(Reparacion reparacion);

    @Delete
    void delete(Reparacion reparacion);

    @Query("SELECT * FROM reparaciones ORDER BY id DESC")
    List<Reparacion> getAll();

    @Query("SELECT * FROM reparaciones WHERE id = :id LIMIT 1")
    Reparacion getById(int id);

    @Query("SELECT * FROM reparaciones WHERE clienteId = :clienteId ORDER BY fecha DESC")
    List<Reparacion> getByCliente(int clienteId);

    @Query("SELECT * FROM reparaciones WHERE fecha = :fecha ORDER BY id DESC")
    List<Reparacion> getByFecha(String fecha);
}