package com.example.mytaskbud.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Planners.class},version=6)
public abstract class LocalDataBase extends RoomDatabase{
    public abstract LdbDao ldbDao();

    public static LocalDataBase INSTANCE;
    public static LocalDataBase getLocalDataBase(final Context context){
        if (INSTANCE == null){
            synchronized (LocalDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDataBase.class, "local-database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}
