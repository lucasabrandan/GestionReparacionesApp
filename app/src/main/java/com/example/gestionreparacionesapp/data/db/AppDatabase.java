package com.example.gestionreparacionesapp.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gestionreparacionesapp.data.db.dao.ClienteDao;
import com.example.gestionreparacionesapp.data.db.dao.ProductoDao;
import com.example.gestionreparacionesapp.data.db.dao.ReparacionDao;
import com.example.gestionreparacionesapp.data.db.dao.UsuarioDao;
import com.example.gestionreparacionesapp.data.db.dao.VentaDao;
import com.example.gestionreparacionesapp.data.db.entity.Cliente;
import com.example.gestionreparacionesapp.data.db.entity.Producto;
import com.example.gestionreparacionesapp.data.db.entity.Reparacion;
import com.example.gestionreparacionesapp.data.db.entity.Usuario;
import com.example.gestionreparacionesapp.data.db.entity.Venta;

@Database(entities = {Usuario.class, Cliente.class, Producto.class, Venta.class, Reparacion.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UsuarioDao usuarioDao();
    public abstract ClienteDao clienteDao();
    public abstract ProductoDao productoDao();
    public abstract VentaDao ventaDao();
    public abstract ReparacionDao reparacionDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "reparaciones_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}