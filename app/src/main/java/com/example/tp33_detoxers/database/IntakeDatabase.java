package com.example.tp33_detoxers.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tp33_detoxers.dao.IntakeDao;
import com.example.tp33_detoxers.model.IntakeProduct;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {IntakeProduct.class}, version = 2, exportSchema = false)
public abstract class IntakeDatabase extends RoomDatabase {
    public abstract IntakeDao intakeDao();

    private static IntakeDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized IntakeDatabase getInstance(final Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), IntakeDatabase.class, "IntakeDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
