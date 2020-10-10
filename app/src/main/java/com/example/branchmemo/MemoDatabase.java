package com.example.branchmemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MemoVo.class}, version = 1)
public abstract class MemoDatabase extends RoomDatabase {
    private static MemoDatabase INSTANCE;

    public abstract MemoDao memeDao();

    public static MemoDatabase getAppDatabase(Context context){
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
