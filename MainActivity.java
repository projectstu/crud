package com.example.banurarandika.project;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banurarandika.project.DataBase.DBHelper;
import com.example.banurarandika.project.DataBase.DataBase;
import com.example.banurarandika.project.DataBase.DatabaseHelper;
import com.example.banurarandika.project.DataBase.SQLiteHelper;
import com.example.banurarandika.project.DataBase.databaseHandler;

import java.util.ArrayList;
import java.util.List;

import static com.example.banurarandika.project.Profile.EXTRA_MESSAGE;
import static com.example.banurarandika.project.Profile.EXTRA_MESSAGE2;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public NavigationView navigationView;
    ArrayList<String> basicFields;
    //gridAdapter adapter;
    public static ArrayList<String> divisions ;
    GridView gridView;
    public static databaseHandler handler;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_sch);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = new Intent(getBaseContext(), make_schedule.class);
                startActivity(launchIntent);
            }
        });*/

        basicFields = new ArrayList<>();
        handler = new databaseHandler(this);
        activity = this;


        getSupportActionBar().show();
        /*divisions = new ArrayList();
        divisions.add("COLOMBO - SIYATHRA");
        divisions.add("COLOMBO - NANASA");
        divisions.add("HORANA - WIDULA");
        divisions.add("GAMPAHA");
        divisions.add("BANDARAGAMA");
        divisions.add("KESBAWA");
        divisions.add("KOHUWALA");*/
        location location= new location();
        //gridView = (GridView)findViewById(R.id.grid);
        /*basicFields.add("ATTENDANCE");
        basicFields.add("SCHEDULER");
        basicFields.add("NOTES");
        basicFields.add("PROFILE");
        basicFields.add("CGPA CALCULATOR");*/
        //adapter = new gridAdapter(this,basicFields);
        //gridView.setAdapter(adapter);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed (View drawerView){
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened (View drawerView){
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                reload();
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            }

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new FirstFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_first_layout);

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        dbHelper.addFirstInfo();
        reload();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_first_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FirstFragment()).commit();
        } else if (id == R.id.nav_second_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new SecondFragment()).commit();
        } else if (id == R.id.nav_third_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ThirdFragment()).commit();
        }else if (id == R.id.nav_fourth_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FourthFragment()).commit();
        }else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void clickedProfile(View view) {
        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
    }

    public void clickedScheduler(View view) {
        Intent intent = new Intent(this,scheduler.class);
        startActivity(intent);
    }

    public void clickedLocation(View view) {
        Intent intent = new Intent(this,location.class);
        startActivity(intent);
    }

    public void clickedSearch(View view){
        Intent intent = new Intent(this,SearchScheduler.class);
        startActivity(intent);
    }
    public void clickedAddStudent(View view){
        Intent i = new Intent(this,Registration.class);
        startActivity(i);
    }

    public void clickedUpdateStudent(View view){
        Intent i = new Intent(this,Student_List.class);
        startActivity(i);
    }

    public void clickedDeleteStudent(View view){
        Intent i = new Intent(this,Student_list1.class);
        startActivity(i);
    }

    public void clickedViewStudent(View view){

        DatabaseHelper myDB = new DatabaseHelper(this);
        Cursor res = myDB.viewData();

        if (res.getCount() == 0){
            //show message
            showMessage("No Students", "Nothing Found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("ID : "+ res.getString(0)+"\n");
            buffer.append("FIRST NAME : "+ res.getString(1)+"\n");
            buffer.append("Address : "+ res.getString(2)+"\n");
            buffer.append("EMAIL : "+ res.getString(3)+"\n");
            buffer.append("Grade : "+ res.getString(4)+"\n");
            buffer.append("PHONE : "+ res.getString(5)+"\n");
            buffer.append("\n");


        }
        //show all data
        showMessage("PROFILE", buffer.toString());
    }

//-------------------------------------------------------


    public void clickedAddTute(View view){
        Intent i=new Intent(this,AddTute.class);
        startActivity(i);
    }

    public void clickedUpdateTute(View view){
        Intent i=new Intent(this,Tute_List.class);
        startActivity(i);
    }
    public void clickedDeleteTute(View view){
        Intent i=new Intent(this,RemoveTute.class);
        startActivity(i);
    }
    public void clickedViewTute(View view) {

        DataBase myDB = new DataBase(this);
        Cursor res = myDB.viewData();

        if (res.getCount() == 0) {
            //show message
            showMessage("No Tutes", "Nothing Found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("ID : " + res.getString(0) + "\n");
            buffer.append("TUTE NAME : " + res.getString(1) + "\n");
            buffer.append("TUTE NO : " + res.getString(2) + "\n");
            buffer.append("SUBJECT : " + res.getString(3) + "\n");
            buffer.append("DESCRIPTION : " + res.getString(4) + "\n");
            buffer.append("\n");


        }
        //show all data
        showMessage("PROFILE", buffer.toString());
    }







    //---------------------------------

    public void clickedAddResult(View view){
        Intent intent= new Intent(this,AddResult.class);
        startActivity(intent);
    }

    public void clickedUpdateResult(View view){
        Intent i=new Intent(this,Result_List.class);
        startActivity(i);
    }
    public void clickedDeleteResult(View view){
        Intent i=new Intent(this,RemoveResult.class);
        startActivity(i);
    }

    public void clickedViewResult(View view){

        SQLiteHelper myDB = new SQLiteHelper(this);
        Cursor res = myDB.viewData();

        if (res.getCount() == 0){
            //show message
            showMessage("No Results", "Nothing Found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("ID : "+ res.getString(0)+"\n");
            buffer.append("STUDENT NAME : "+ res.getString(1)+"\n");
            buffer.append("ENROLLMENT KEY : "+ res.getString(2)+"\n");
            buffer.append("MARKS : "+ res.getString(3)+"\n");
            buffer.append("CLASS : "+ res.getString(4)+"\n");
            buffer.append("GPA : "+ res.getString(5)+"\n");
            buffer.append("\n");


        }
        //show all data
        showMessage("RESULT LIST", buffer.toString());
    }


   /* public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }*/

    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    public void reload(){
        Intent intent = getIntent();
        String user = intent.getStringExtra(EXTRA_MESSAGE);
        String email1 = intent.getStringExtra(EXTRA_MESSAGE2);
        if(user!=null||email1!=null){
            View hView =  navigationView.getHeaderView(0);
            TextView username = (TextView)hView.findViewById(R.id.username2);
            TextView email = (TextView)hView.findViewById(R.id.email);
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            try{
                Bitmap image = dbHelper.getImage();
                ImageView img = (ImageView) hView.findViewById(R.id.imageView);
                img.setImageBitmap(image);
            }catch(Exception e){

            }

            username.setText(user);
            email.setText(email1);
        }else{
            List<String> userInfo;
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            userInfo = dbHelper.readUserInfo();
            View hView =  navigationView.getHeaderView(0);
            try {
                Bitmap image = dbHelper.getImage();
                ImageView img = (ImageView) hView.findViewById(R.id.imageView);
                img.setImageBitmap(image);
            }catch (Exception e){

            }
            TextView username = (TextView)hView.findViewById(R.id.username2);
            TextView email = (TextView)hView.findViewById(R.id.email);
            username.setText(userInfo.get(0));
            email.setText(userInfo.get(1));

        }
    }
    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }




}
