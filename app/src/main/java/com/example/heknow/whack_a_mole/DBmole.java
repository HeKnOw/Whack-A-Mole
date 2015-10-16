package com.example.heknow.whack_a_mole;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeKnOw on 10/14/15.
 */
public class DBmole extends SQLiteOpenHelper {

    private static final int TABLE_VERSION = 2;
    private static final String DATABASE_NAME = "database.db";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_GENDER = "gender";

    public DBmole(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, TABLE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_AGE + " INTEGER DEFAULT 18, " +
                COLUMN_GENDER + " TEXT DEFAULT 'Male', " +
                COLUMN_LEVEL + " INTEGER DEFAULT 1" +
                " );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(global_user user){
        long row_id;
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME,user.getName());
        cv.put(COLUMN_PASSWORD,user.getPass());
        cv.put(COLUMN_AGE,Integer.parseInt(user.getAge()));
        cv.put(COLUMN_GENDER,user.getGender());
        cv.put(COLUMN_LEVEL,user.getLevel());
        SQLiteDatabase db = getWritableDatabase();
        row_id = db.insert(TABLE_USERS, null, cv);
        user.setId(row_id);
        db.close();
    }

    public void updateUser(global_user user){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LEVEL, user.getLevel());
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_USERS, cv, "_id " + "=" + user.getID(), null);
        db.close();
    }

    public ArrayList<global_user> getAllUsersbyLevel(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<global_user> userList = new ArrayList<global_user>();
        int i = 0;

        String[] projection = {
            COLUMN_USERNAME,
            COLUMN_ID,
            COLUMN_LEVEL,
            COLUMN_GENDER,
            COLUMN_AGE
        };
        String sortOrder = COLUMN_LEVEL + " DESC";

        Cursor c = db.query(TABLE_USERS,projection,null,null,null,null,sortOrder);
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_ID)) != null){
                userList.add(i,new global_user());
                userList.get(i).setGender(c.getString(c.getColumnIndex(COLUMN_GENDER)));
                userList.get(i).setName(c.getString(c.getColumnIndex(COLUMN_USERNAME)));
                userList.get(i).setLevel(c.getInt(c.getColumnIndex(COLUMN_LEVEL)));
                userList.get(i).setAge(c.getString(c.getColumnIndex(COLUMN_AGE)));
                userList.get(i).setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
            }
            i++;
            c.moveToNext();
        }
        db.close();
        return userList;
    }



}
