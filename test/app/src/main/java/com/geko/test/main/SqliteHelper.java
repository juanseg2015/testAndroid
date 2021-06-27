package com.geko.test.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {
    public static final int DB_Version = 1;
    public static final String DBName = "GekoTest.db";

    private static final String SQL_Create_Users = "CREATE TABLE Users (UserId INTEGER PRIMARY KEY,FullName TEXT,UserMail TEXT,UserPass TEXT,UserPhone TEXT)";
    public SqliteHelper(Context context) {
        super(context, DBName, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_Create_Users);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int validateEmail(String userMail){
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = 0;
        Cursor res = db.rawQuery( "SELECT UserId FROM Users WHERE UserMail='"+userMail+"'", null );
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            userId = res.getInt(res.getColumnIndex("UserId"));
            res.moveToNext();
        }
        return userId;
    }

    public int validateUser(String userMail, String userPass){
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = 0;
        Cursor res = db.rawQuery( "SELECT UserId FROM Users WHERE UserMail='"+userMail+"' and UserPass = '"+userPass +"'", null );
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            userId = res.getInt(res.getColumnIndex("UserId"));
            res.moveToNext();
        }
        return userId;
    }

    public long saveUser(String fullName,String userMail,String userPass,String userPhone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userData = new ContentValues();
        userData.put("FullName", fullName);
        userData.put("UserMail", userMail);
        userData.put("UserPass", userPass);
        userData.put("UserPhone", userPhone);
        long newRowId = db.insert("Users", null, userData);
        return newRowId;
    }

    public ArrayList getUserData(int UserId){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<String>();
        Cursor res = db.rawQuery( "SELECT FullName, UserMail, UserPhone FROM Users WHERE UserId="+UserId, null );
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            arrayList.add(res.getString(res.getColumnIndex("FullName")));
            arrayList.add(res.getString(res.getColumnIndex("UserMail")));
            arrayList.add(res.getString(res.getColumnIndex("UserPhone")));
            res.moveToNext();
        }
        return arrayList;
    }


    /*
    * */
}
