package com.example.banurarandika.project.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "mad_projectc.db";
    public static final String TABLE_NAME = "user_details";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FIRST_NAME";
    public static final String COL_3 = "LAST_NAME";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "PASSWORD";
    public static final String COL_6 = "PHONE";



    //STUDENT TABLE





    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, EMAIL TEXT, PASSWORD TEXT, PHONE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    //inserting data in database
    public boolean insertData(String fname, String lname, String email, String password, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,fname);
        contentValues.put(COL_3,lname);
        contentValues.put(COL_4,email);
        contentValues.put(COL_5,password);
        contentValues.put(COL_6,phone);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    //checking the email and password
    public boolean ckeckLogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +TABLE_NAME+ " WHERE " +COL_4+ "=? AND " +COL_5+ "=? ", new String[]{email,password});

            if (cursor.getCount() > 0) {
                //cursor.moveToNext();
                return true;
            } else
                return false;
    }
    //checking if email exists
    public boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +TABLE_NAME+ " WHERE " +COL_4+ "=? ",new String[]{email});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }
    //get data from database
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " +TABLE_NAME,null);
        return res;
    }
    //update data
    public boolean updateData(String id,String fname, String lname, String email, String password, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1,id);
        contentValues.put(COL_2,fname);
        contentValues.put(COL_3,lname);
        contentValues.put(COL_4,email);
        contentValues.put(COL_5,password);
        contentValues.put(COL_6,phone);
        db.update(TABLE_NAME, contentValues, " ID = ? ",new String[]{id});
        return true;
    }
    //delete data
    public Integer deleteData(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, " ID = ? ", new String[]{email});
    }

    //inserting data in database


   public Cursor search(String id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " +TABLE_NAME+" where ID='"+id+"'",null);
        return res;
    }

    public Cursor searchname(String id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " +TABLE_NAME+" where ID='"+id+"'",null);
        return res;
    }

    //public
}
