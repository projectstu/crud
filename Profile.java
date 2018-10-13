package com.example.banurarandika.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.banurarandika.project.DataBase.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.banurarandika.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "com.example.banurarandika.MESSAGE2";
    private static final int CAMERA_REQUEST=1888;
    ImageView imageView;
    View myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Profile");

        List<String> userInfo;
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        userInfo = dbHelper.readUserInfo();
        try {
            Bitmap img = dbHelper.getImage();
            ImageView image = (ImageView) findViewById(R.id.imageView4);
            image.setImageBitmap(img);
        }catch (Exception e){

        }
        EditText username = (EditText) findViewById(R.id.editText);
        EditText email = (EditText) findViewById(R.id.editText2);
        username.setText(userInfo.get(0));
        email.setText(userInfo.get(1));

        imageView = (ImageView) findViewById(R.id.imageView4);

        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_REQUEST);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CAMERA_REQUEST) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                dbHelper.addImage(photo);
            }
        }catch (Exception e){

        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        afterSaveButtonClicked();
        finish();
        return true;
    }


    public void onSaveButtonClicked(View v) {
        EditText user = (EditText) findViewById(R.id.editText);
        EditText emailtext = (EditText) findViewById(R.id.editText2);
        String username = user.getText().toString();
        String email = emailtext.getText().toString();
        if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            emailtext.setError("Enter valid email");
            return;
        }
        DBHelper dbHelper = new DBHelper(v.getContext());
        dbHelper.addInfo(username,email);
        afterSaveButtonClicked();
    }

    public void afterSaveButtonClicked(){
        List<String> userInfo;
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        userInfo = dbHelper.readUserInfo();

        if(!userInfo.isEmpty()){
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(EXTRA_MESSAGE,userInfo.get(0));
            intent.putExtra(EXTRA_MESSAGE2,userInfo.get(1));
            startActivity(intent);
        }

    }

}
