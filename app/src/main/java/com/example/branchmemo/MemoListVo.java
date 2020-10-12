package com.example.branchmemo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Timestamp;

@Entity(tableName = "memolist")
public class MemoListVo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String code;
    private String title;
    private Timestamp dateval;

    public MemoListVo(String code, String title, Timestamp date) {
        this.code = code;
        this.title = title;
        this.dateval = date;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDateval() {
        return dateval;
    }

    public void setDateval(Timestamp dateval) {
        this.dateval = dateval;
    }
}
