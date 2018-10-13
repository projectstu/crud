package com.example.banurarandika.project.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.banurarandika.project.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "Project.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE "+ UserMaster.User.TABLE_NAME+" ("+ UserMaster.User._ID+" INTEGER PRIMARY KEY,"+
                UserMaster.User.COLUMN_NAME_USERNAME+" TEXT,"+
                UserMaster.User.COLUMN_NAME_EMAIL+" TEXT, "+
                UserMaster.User.COLUMN_NAME_PHOTO+" BLOB DEFAULT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addInfo(String username,String email){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values =new ContentValues();
        values.put(UserMaster.User.COLUMN_NAME_USERNAME,username);
        values.put(UserMaster.User.COLUMN_NAME_EMAIL,email);
        db.update(UserMaster.User.TABLE_NAME, values, UserMaster.User._ID + " = 1", null);

    }

    public void addFirstInfo(){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values =new ContentValues();
        String qu = "SELECT * FROM "+ UserMaster.User.TABLE_NAME;
        Cursor cursor= db.rawQuery(qu,null);

        if(!cursor.moveToFirst()) {
            values.put(UserMaster.User.COLUMN_NAME_USERNAME, "Android Studio");
            values.put(UserMaster.User.COLUMN_NAME_EMAIL, "androidstudio@gmail.com");
            db.insert(UserMaster.User.TABLE_NAME, null, values);
        }
    }

    public  void addImage(Bitmap image){
        SQLiteDatabase db = getWritableDatabase();
        byte[] data = getBitmapAsByteArray(image); // this is a function

        ContentValues cv = new  ContentValues();
        cv.put(UserMaster.User.COLUMN_NAME_PHOTO,   data);
        db.update(UserMaster.User.TABLE_NAME,cv, UserMaster.User._ID+" = 1",null);


    }

    public Bitmap getImage(){
        SQLiteDatabase db = getReadableDatabase();
        String qu = "SELECT "+UserMaster.User.COLUMN_NAME_PHOTO+" FROM "+ UserMaster.User.TABLE_NAME+" WHERE "+ UserMaster.User._ID+" = ?";
        Cursor cur = db.rawQuery(qu,new String[] {"1"});

        if (cur.moveToFirst()){
            byte[] imgByte = cur.getBlob(0);
            cur.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null ;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public List readUserInfo(){
        SQLiteDatabase db = getReadableDatabase();
        List<String> user= new ArrayList<>();
        String username=null;
        String email=null;
        String userInfo ="SELECT "+UserMaster.User.COLUMN_NAME_USERNAME+" , "+ UserMaster.User.COLUMN_NAME_EMAIL+" FROM "+ UserMaster.User.TABLE_NAME+" LIMIT ?";
        Cursor cursor = db.rawQuery(userInfo,new String[] {"1"});

        if(cursor.moveToFirst()){
             username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
             email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

             user.add(username);
             user.add(email);
        }
        cursor.close();
        return user;
    }
}
