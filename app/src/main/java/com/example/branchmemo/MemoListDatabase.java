package com.example.branchmemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {MemoListVo.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverters.class})
public abstract class MemoListDatabase extends RoomDatabase {
    private static MemoListDatabase INSTANCE;

    public abstract MemoListDao memoListDao();

    public static synchronized MemoListDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, MemoListDatabase.class, "memolist-db")
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
