package com.ksteach81.dbexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MemoDbHelper extends SQLiteOpenHelper {
    private static MemoDbHelper sInstance;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "memo.db";
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
                    MemoContract.MemoEntry.TABLE_NAME,
                    MemoContract.MemoEntry._ID,
                    MemoContract.MemoEntry.COLUMN_NAME_TITLE,
                    MemoContract.MemoEntry.COLUMN_NAME_CONTENTS);

    private static final String SQl_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MemoContract.MemoEntry.TABLE_NAME;

    public static MemoDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MemoDbHelper(context);
        }
        return sInstance;
    }

    private MemoDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 최초의 db 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 테이블 업데이트
        db.execSQL(SQl_DELETE_ENTRIES);
        onCreate(db);
    }
}
