package com.example.csoka.lab_02_android.repository;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.example.csoka.lab_02_android.dao.PostDao;
import com.example.csoka.lab_02_android.model.Post;

@Database(entities = {Post.class}, version = 4, exportSchema = false)
public abstract class PostRoomDatabase extends RoomDatabase {
    public abstract PostDao postDao();

    private static volatile PostRoomDatabase INSTANCE;

    public static PostRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PostRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PostRoomDatabase.class, "Posts")
                            //Does not Migrate; it clears & rebuilds the database
                            .fallbackToDestructiveMigration()
                            //.addCallback(staticRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
