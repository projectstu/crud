package com.example.banurarandika.project.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DataBase extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "mad_projectcc.db";
    public static final String TABLE_NAME = "tutes";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TUTE_NAME";
    public static final String COL_3 = "TUTE_NO";
    public static final String COL_4 = "SUBJECT";
    public static final String COL_5 = "DESCRIPTION";

    //Tute TABLE

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, TUTE_NAME TEXT, TUTE_NO TEXT, SUBJECT TEXT, DESCRIPTION TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    //inserting data in database
    public boolean insertData(String tname, String number, String subject , String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,tname);
        contentValues.put(COL_3,number);
        contentValues.put(COL_4,subject);
        contentValues.put(COL_5,description);
        long tute = db.insert(TABLE_NAME,null,contentValues);
        if (tute == -1)
            return false;
        else
            return true;
    }
    //checking the email and password
  /*  public boolean ckeckLogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +TABLE_NAME+ " WHERE " +COL_4+ "=? AND " +COL_5+ "=? ", new String[]{email,password});

        if (cursor.getCount() > 0) {
            //cursor.moveToNext();
            return true;
        } else
            return false;
    }*/
    //checking if email exists
   /* public boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +TABLE_NAME+ " WHERE " +COL_4+ "=? ",new String[]{email});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }*/
    //get data from database
    public Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " +TABLE_NAME,null);
        return res;
    }
    //update data
    public boolean updateData(String id,String tname, String number, String subject, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1,id);
        contentValues.put(COL_2,tname);
        contentValues.put(COL_3,number);
        contentValues.put(COL_4,subject);
        contentValues.put(COL_5,description);
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


}
