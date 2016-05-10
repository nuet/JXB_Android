package com.lenso.jixiangbao.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by king on 2016/5/10.
 */
public class JXBSQLiteOpenHelp extends SQLiteOpenHelper {
    private final static int JXBSQLITE_VERSION=1;
    private final static String JXBSQLITE_NAME="jxb.db";
    public JXBSQLiteOpenHelp(Context context) {
        super(context, JXBSQLITE_NAME, null, JXBSQLITE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
