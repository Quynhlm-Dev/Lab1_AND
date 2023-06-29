package com.quynhlm.dev.lab1_and.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.quynhlm.dev.lab1_and.Database.My_Sqlite;
import com.quynhlm.dev.lab1_and.Model.Todo_Model;

import java.util.ArrayList;

public class TodoDao {
    My_Sqlite my_sqlite;
    Context context;

    public TodoDao(Context context) {
        my_sqlite = new My_Sqlite(context);
        this.context = context;
    }

    public boolean insert_data(Todo_Model todo_model) {
        SQLiteDatabase database = my_sqlite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", todo_model.getId());
        cv.put("title", todo_model.getTitle());
        cv.put("contact", todo_model.getContact());
        cv.put("date", todo_model.getDate());
        cv.put("type", todo_model.getType());
        long check = database.insert("TODOLIST", null, cv);
        return check != -1;
    }

    public boolean update_data(Todo_Model todo_model) {
        SQLiteDatabase database = my_sqlite.getWritableDatabase();
        String[] dk = {String.valueOf(todo_model.getId())};
        ContentValues cv = new ContentValues();
        cv.put("id", todo_model.getId());
        cv.put("title", todo_model.getTitle());
        cv.put("contact", todo_model.getContact());
        cv.put("date", todo_model.getDate());
        cv.put("type", todo_model.getType());
        long check = database.update("TODOLIST", cv, "id=?", dk);
        return check != -1;
    }

    public boolean delete_data(int id) {
        SQLiteDatabase database = my_sqlite.getWritableDatabase();
        String[] dk = {String.valueOf(id)};
        long check = database.delete("TODOLIST", "id=?", dk);
        return check != -1;
    }


    public ArrayList<Todo_Model> selectAll() {
        ArrayList<Todo_Model> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = my_sqlite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TODOLIST", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                String data = cursor.getString(3);
                String type = cursor.getString(4);

                Todo_Model todo_model = new Todo_Model(id, title, content, data, type);
                list.add(todo_model);
                cursor.moveToNext();
            }
            cursor.close();
        } else {
            Toast.makeText(context, "Danh sach trong", Toast.LENGTH_SHORT).show();
        }
        return list;
    }
}
