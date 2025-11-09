package com.example.gestionreparacionesapp.data.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.gestionreparacionesapp.data.db.dao.UserDao;
import com.example.gestionreparacionesapp.data.db.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Gestor principal de la Base de Datos.
 * Sigue el patrón Singleton para asegurar una única instancia.
 */
// 'version' debe incrementarse si cambias el esquema de la DB.
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // Método abstracto para que Room nos provea el DAO.
    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    // ExecutorService para correr operaciones de DB en hilos de fondo (ej. inserts)
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Método Singleton para obtener la instancia de la DB.
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "gestion_reparaciones_db")

                            // ¡¡AQUÍ ESTÁ LA CORRECCIÓN!!
                            // Esta línea permite que el 'loginUser' (que usa .getUserByEmail())
                            // se ejecute en el hilo principal sin crashear.
                            // Esto es una simplificación específica para este TP.
                            .allowMainThreadQueries()

                            .build();
                }
            }
        }
        return INSTANCE;
    }
}