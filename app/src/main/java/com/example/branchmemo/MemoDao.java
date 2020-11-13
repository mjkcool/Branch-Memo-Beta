package com.example.branchmemo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MemoDao {

    @Query("SELECT * FROM memo_data WHERE code=:code")
    List<MemoVo> getAll(String code);

    @Insert
    void insert(MemoVo memo);

    @Update
    void update(MemoVo memo);

    @Delete
    void delete(MemoVo memo);

    @Query("DELETE FROM memo_data")
    void deleteAll();

    @Query("DELETE FROM memo_data WHERE code=:code_")
    void delete(String code_);

    @Query("SELECT MAX(id) FROM memo_data WHERE code=:code_")
    int getThisId(String code_);
}
