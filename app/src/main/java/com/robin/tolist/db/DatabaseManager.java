package com.robin.tolist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.robin.tolist.model.Task;

import java.util.ArrayList;
import java.util.List;

import static com.robin.tolist.db.DatabaseHelper.DATE;
import static com.robin.tolist.db.DatabaseHelper.ID;
import static com.robin.tolist.db.DatabaseHelper.STATUS;
import static com.robin.tolist.db.DatabaseHelper.TABLE_NAME;

public class DatabaseManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        this.context = context;
    }
    public DatabaseManager open()throws SQLException{
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }
    public long insert(Task task){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.TASK,task.getTask());
        contentValues.put(DatabaseHelper.DATE,task.getDate());
        contentValues.put(DatabaseHelper.DETAILS,task.getDetails());
        contentValues.put(DatabaseHelper.PRIORITY,task.getPriority());
        contentValues.put(STATUS,task.getStatus());
        long newRowId = database.insert(TABLE_NAME, null, contentValues);
        return newRowId;
    }
    public ArrayList<Task> queryPending(){
        ArrayList<Task> tasks=new ArrayList<>();
        Cursor cursor=database.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+STATUS+" =?"+" ORDER BY "+DATE,new String[]{"pending"});
        cursor.moveToFirst();
        do {
            tasks.add(new Task(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
                 )
            );
        }while (cursor.moveToNext());
        return tasks;
    }
    public ArrayList<Task> queryCompleted(){
        ArrayList<Task> tasks=new ArrayList<>();
        Cursor cursor=database.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+STATUS+" =?"+" ORDER BY "+DATE,new String[]{"completed"});
        cursor.moveToFirst();
        do {
            tasks.add(new Task(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
                 )
            );
        }while (cursor.moveToNext());
        return tasks;
    }

    public int delete(Task task){
        String whereClause=ID+"=?";
        String [] args={task.getId()+""};
        database=dbHelper.getWritableDatabase();
        return database.delete(TABLE_NAME,whereClause,args);
    }
    public int updateCompleted(Task task){
        String whereClause=ID+"=?";
        String [] args={task.getId()+""};
        ContentValues contentValues=new ContentValues();
        contentValues.put(STATUS,"completed");
        return database.update(TABLE_NAME,contentValues,whereClause,args);
    }
    public int updateTask(Task task){
        String whereClause=ID+"=?";
        String [] args={task.getId()+""};
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.TASK,task.getTask());
        contentValues.put(DatabaseHelper.DATE,task.getDate());
        contentValues.put(DatabaseHelper.DETAILS,task.getDetails());
        contentValues.put(DatabaseHelper.PRIORITY,task.getPriority());
        contentValues.put(STATUS,task.getStatus());
        return database.update(TABLE_NAME,contentValues,whereClause,args);
    }

}
