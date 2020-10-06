package com.example.branchmemo;

import android.provider.BaseColumns;

import java.sql.PreparedStatement;

public final class FeedReaderContract {
    private static PreparedStatement pstmt;

    private FeedReaderContract() {}
    private static class FeedEntry implements BaseColumns {
        public static final String COLUMN_NAME_INDEXVAL = "indexval";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENTBODY = "contentbody";
        public static final String COLUMN_NAME_DATEVAL = "dateval";
    }

    public String SQL_INITIAL = "CREATE TABLE tablelistinfo (";

    public static final String SQL_CREATE = "CREATE TABLE  ?  (" +
            FeedEntry.COLUMN_NAME_INDEXVAL + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FeedEntry.COLUMN_NAME_TITLE + " VARCHAR," +
            FeedEntry.COLUMN_NAME_CONTENTBODY + " TEXT" +
            FeedEntry.COLUMN_NAME_DATEVAL + " DATETIME DEFAULT DATETIME())";

    public static final String SQL_INSERT = "INSERT INTO ? ("+FeedEntry.COLUMN_NAME_TITLE
            +", "+FeedEntry.COLUMN_NAME_CONTENTBODY+") values(?, ?)";
    public static final String SQL_DELETE = "DELETE FROM ? WHERE indexval = ?";

    //수정 후
    public static final String SQL_UPDATE = "UPDATE student SET "+FeedEntry.COLUMN_NAME_INDEXVAL+"=MAX("+FeedEntry.COLUMN_NAME_INDEXVAL+"-1), "
            +FeedEntry.COLUMN_NAME_TITLE+"=?, "
            +FeedEntry.COLUMN_NAME_CONTENTBODY+"=?, "+FeedEntry.COLUMN_NAME_DATEVAL+"=datetime()" +
            " WHERE "+FeedEntry.COLUMN_NAME_INDEXVAL+"=MAX("+FeedEntry.COLUMN_NAME_INDEXVAL+")";
    public static final String SQL_DROP = "DROP TABLE IF EXISTS ?";


}