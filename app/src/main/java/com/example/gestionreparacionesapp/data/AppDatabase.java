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
 * Room DB (Singleton).
 * NO usamos allowMainThreadQueries: todas las operaciones van en hilos de fondo.
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;

    // Pool de hilos para ejecutar operaciones de DB
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "gestion_reparaciones_db"
                            )
                            // IMPORTANTE: sin allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
