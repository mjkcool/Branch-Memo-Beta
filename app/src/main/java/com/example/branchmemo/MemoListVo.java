package com.example.branchmemo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;

@Entity(tableName = "memolist_data")
public class MemoListVo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String code;
    private String title;
    @TypeConverters(DateConverters.class)
    private Date dateval;

    public MemoListVo() {}

    public MemoListVo(String code, String title, Date date) {
        setCode(code);
        setTitle(title);
        setDateval(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getCode() {
        return code;
    }

    public void setCode(String code) { this.code = code; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateval() {
        return dateval;
    }

    public void setDateval(Date dateval) {
        this.dateval = dateval;
    }
}
