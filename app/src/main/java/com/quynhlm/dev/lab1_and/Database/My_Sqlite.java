package com.quynhlm.dev.lab1_and.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class My_Sqlite extends SQLiteOpenHelper {
    public My_Sqlite(@Nullable Context context) {
        super(context, "lab1_and.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE TODOLIST(id INTEGER,title TEXT,contact TEXT,date TEXT,type TEXT)";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS TODOLIST";
        db.execSQL(dropTable);
        onCreate(db);
    }
}
