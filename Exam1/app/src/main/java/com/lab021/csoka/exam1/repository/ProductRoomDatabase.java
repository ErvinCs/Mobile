package com.lab021.csoka.exam1.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.lab021.csoka.exam1.dao.RequestDao;
import com.lab021.csoka.exam1.model.Request;

@Database(entities = {Request.class}, version = 3, exportSchema = false)
public abstract class ProductRoomDatabase extends RoomDatabase {
    public abstract RequestDao productDao();

    private static volatile ProductRoomDatabase INSTANCE;

    public static ProductRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProductRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProductRoomDatabase.class, "requests")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
