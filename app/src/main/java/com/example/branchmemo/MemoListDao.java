package com.example.branchmemo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.sql.Date;
import java.util.List;

@Dao
public interface MemoListDao {
    @Query("SELECT * FROM memolist_data")
    List<MemoListVo> getAll();

    @Query("SELECT * FROM memolist_data WHERE code=:code")
    MemoListVo get(String code);

    @Insert
    void insert(MemoListVo memolist);

    @Update
    void update(MemoListVo memolist);

    @Delete
    void delete(MemoListVo memolist);

    @Query("DELETE FROM memolist_data")
    void deleteAll();

    @Query("select EXISTS (select * from memolist_data where code=:code_) as success")
    int selectCode(String code_);

    @Query("UPDATE memolist_data SET title=:new_notename, dateval=:date WHERE code=:code_")
    void updateNote(String code_,String new_notename, Date date);

    @Query("DELETE FROM memolist_data WHERE code=:code_")
    void delete(String code_);


}
