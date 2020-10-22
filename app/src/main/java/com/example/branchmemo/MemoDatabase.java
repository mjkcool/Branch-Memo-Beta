package com.example.branchmemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {MemoVo.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverters.class})
public abstract class MemoDatabase extends RoomDatabase {
    private static MemoDatabase INSTANCE;

    public abstract MemoDao memeDao();

    public static synchronized MemoDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, MemoDatabase.class, "Memo-db")
                    .build();
        }
        return INSTANCE;
    }
    public static void destroyInstance(){
        INSTANCE = null;
    }

}
