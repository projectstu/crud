package com.example.banurarandika.project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.banurarandika.project.DataBase.DatabaseHelper;

import java.util.ArrayList;

public class UpdateProfile extends AppCompatActivity {

    DatabaseHelper myDB = new DatabaseHelper(this);
    EditText _fname;
    EditText _lname;
    EditText _email;
    Spinner _grade;
    EditText _phone;
    Button updateProfile;

    Button search;
    EditText id;
    //String idget = null;
    String idg,fname,lname,email,grade,value,phone=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  myDB = new DatabaseHelper(this);

        _fname = (EditText) findViewById(R.id.fname);
        _lname = (EditText) findViewById(R.id.lname);
        _email = (EditText) findViewById(R.id.email);
        _grade = (Spinner)findViewById(R.id.grade1);
        _phone = (EditText) findViewById(R.id.phone);
        updateProfile = (Button) findViewById(R.id.button25);

        ArrayList<String> list = new ArrayList<>();
        list.add("Class");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        ArrayAdapter<String> adapter=new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _grade.setAdapter(adapter);



        // Cursor

        // String name = getIntent().getStringExtra("value0");
        //_fname.setText("");


        id = (EditText) findViewById(R.id.id1);
        search = (Button) findViewById(R.id.search);


        //idget = id.getText().toString();

        fill();
        UpdateProfile();
        search1();





    }


    public void UpdateProfile() {
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fname = _fname.getText().toString();
                lname = _lname.getText().toString();
                email = _email.getText().toString();
                grade = _grade.getSelectedItem().toString();
                phone = _phone.getText().toString();

                int x = 0;

                if (fname.equals("") || lname.equals("")  || grade.equals("") || phone.equals("")) {
                    Toast.makeText(getApplicationContext(), "Need to Fill All Fields without email!", Toast.LENGTH_LONG).show();
                    x = 2;
                }

                if (!fname.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                    _fname.setError("Enter only letters");
                    x = 2;


                }
                //if (!lname.matches("[a-zA-z]+")) {
                //    _lname.setError("Enter only letters");
                //    x = 2;


                //}

                if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")&&email.length()>0) {
                    _email.setError("Enter valid email");
                    x = 2;

                }
                if (!grade.matches("[0-9_-]+")) {
                    Toast.makeText(getApplicationContext(), "Select a class", Toast.LENGTH_LONG).show();

                    x = 2;

                }
                if (!phone.matches("[0-9_-]+")) {
                    _phone.setError("Enter only numbers");
                    x = 2;

                }

                int z=0;
                Cursor rs=myDB.searchname(id.getText().toString());

                if((rs.moveToNext()==true)&&(x!=2)){


                    boolean isUpdated = myDB.updateData(id.getText().toString(),_fname.getText().toString(),
                            _lname.getText().toString(),
                            _email.getText().toString(),
                            _grade.getSelectedItem().toString(),
                            _phone.getText().toString());
                    if (isUpdated == true) {
                        Toast.makeText(getApplicationContext(), "Data updated", Toast.LENGTH_LONG).show();

                        id.setText("");
                        _fname.setText("");
                        _lname.setText("");
                        // _lname.setText(lname);
                        _email.setText("");
                        _grade.setSelection(0);
                        _phone.setText("");
                    }}
                else if((rs.moveToNext())!=true && (x!=2)){
                    Toast.makeText(getApplicationContext(), "This ID is not in the list", Toast.LENGTH_LONG).show();
                }}
        });
    }


    public void search1() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // myDB = new DatabaseHelper(this);

                if(id.getText().toString().equals("")){
                    Toast.makeText(UpdateProfile.this,"enter ID First",Toast.LENGTH_LONG).show();
                }

                if(!(id.getText().toString().matches("[0-9_-]+"))){

                    id.setError("Enter only numbers");
                }


                Cursor res = myDB.search(id.getText().toString());
                if (res.moveToNext()) {
                    // idg = res.getString(0);
                    fname = res.getString(1);
                    lname = res.getString(2);
                    email=res.getString(3);
                    grade=res.getString(4);
                    phone=res.getString(5);

                    _fname.setText(fname);
                    _lname.setText(lname);

                    _email.setText(email);
                    _grade.setSelection(Integer.parseInt(grade));
                    _phone.setText(phone);



                }


                else if((id.getText().toString().length()>0)){
                    Toast.makeText(UpdateProfile.this,"Entered ID is not in the list!!",Toast.LENGTH_SHORT).show();

                    _fname.setText("");
                    _lname.setText("");

                    _email.setText("");
                    _grade.setSelection(Integer.parseInt(grade));
                    _phone.setText("");
                }

                //Toast.makeText(UpdateProfile.this,"fname",Toast.LENGTH_SHORT).show();


            }
        });
    }

    public void fill(){
        // Student_List s1=new Student_List();
        //value=s1.passvalue1();


        Intent intent=getIntent();
        String value1=intent.getStringExtra("value0");
        _lname.setText(value1);




        Cursor rs =myDB.searchname(value1);


        if (rs.moveToNext()){
            idg = rs.getString(0);
            fname = rs.getString(1);
            lname = rs.getString(2);
            email=rs.getString(3);
            grade=rs.getString(4);
            phone=rs.getString(5);

            _fname.setText(fname);
            _lname.setText(lname);
            id.setText(idg);

            _email.setText(email);
            _grade.setSelection(Integer.parseInt(grade));
            _phone.setText(phone);



        }


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}