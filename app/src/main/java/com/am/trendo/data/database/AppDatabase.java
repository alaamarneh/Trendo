package com.am.trendo.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.am.trendo.R;
import com.am.trendo.model.Sale;

@Database(entities = {Sale.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SaleDao saleDao();

    private static AppDatabase instance;
    private static final Object LOCK = new Object();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, context.getString(R.string.db_name))
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return instance;
    }
}
