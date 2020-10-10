package com.example.branchmemo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@Entity(tableName = "memo_data")
public class MemoVo {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String code;
    public String title;
    public String contentbody;
    public Timestamp dateval;

    public MemoVo(String title, String content) {
        this.code = (new CodeCreater()).getNewCode();
        this.title = title;
        this.contentbody = content;
        setDateval();
    }
    public MemoVo(String code, String title, String content){
        this.code = code;
        this.title = title;
        this.contentbody = content;
        setDateval();
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

    public Timestamp getDateval() {
        return dateval;
    }

    public void setDateval() {
        Calendar cal = Calendar.getInstance();
        this.dateval = new Timestamp(cal.getTimeInMillis());
    }
}
