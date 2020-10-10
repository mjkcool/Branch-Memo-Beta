package com.example.branchmemo;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public abstract class MemoListDatabase extends RoomDatabase {
    private static MemoListDatabase INSTANCE;

    public abstract MemoListDao memoListDao();

    public static MemoListDatabase getAppDatabase(Context context){
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
