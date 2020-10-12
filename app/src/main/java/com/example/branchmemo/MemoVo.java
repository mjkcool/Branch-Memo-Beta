package com.example.branchmemo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;
import java.util.Calendar;

@Entity(tableName = "memo_data")
public class MemoVo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String code;
    private String title;
    private String contentbody;
    @TypeConverters(DateConverters.class)
    private Date dateval;

    public MemoVo() { }

    public MemoVo(String code, String title, String content, Date date){
        setCode(code);
        setTitle(title);
        setContentbody(content);
        setDateval(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getContentbody() {
        return contentbody;
    }

    public void setContentbody(String contentbody) {
        this.contentbody = contentbody;
    }

    public Date getDateval() {
        return dateval;
    }

    public void setDateval(Date dateval) { this.dateval = dateval; }
}
