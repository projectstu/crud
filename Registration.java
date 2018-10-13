package com.example.banurarandika.project;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.AppCompatButton;
import com.example.banurarandika.project.DataBase.DatabaseHelper;

import java.util.ArrayList;

public class Registration extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText _fname;
    EditText _lname;
    EditText _email;
    Spinner _grade;
    EditText _phone;
    Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDB = new DatabaseHelper(this);

        _fname = (EditText)findViewById(R.id.fname);
        _lname = (EditText)findViewById(R.id.lname);
        _email = (EditText)findViewById(R.id.email);
        _grade = (Spinner)findViewById(R.id.grade1);
        _phone = (EditText)findViewById(R.id.phone);
        reg = (Button)findViewById(R.id.register);

        ArrayList<String> list1 = new ArrayList<>();

        list1.add("Class");
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        list1.add("5");
        list1.add("6");
        list1.add("7");
        list1.add("8");
        list1.add("9");
        list1.add("10");
        list1.add("11");
        list1.add("12");
        list1.add("13");

        ArrayAdapter<String> adapter1=new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _grade.setAdapter(adapter1);

        AddData();



       /* Button log;
        log = findViewById(R.id.button);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Registration.this,Login.class);
                startActivity(i);
            }
        });*/
    }

    public void AddData(){
        reg.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

                                       String fname = _fname.getText().toString();
                                       String lname = _lname.getText().toString();
                                       String email = _email.getText().toString();
                                       String password = _grade.getSelectedItem().toString();
                                       String phone = _phone.getText().toString();





                                       int x = 0;

                                       if (fname.equals("") || lname.equals("") || password.equals("") || phone.equals("")) {
                                           Toast.makeText(getApplicationContext(), "Need to Fill All Fields!", Toast.LENGTH_LONG).show();
                                           x = 2;
                                       }

                /*if (!fname.matches("[a-zA-z]+")) {
                    _fname.setError("Enter only letters");
                    x = 2;


                }*/
                                       if(!fname.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")){
                                           _fname.setError("Enter only letters");
                                           x=2;

                                       }
                                       // if (!lname.matches("[a-zA-z]+")) {
                                       //  _lname.setError("Enter only letters");
                                       //  x = 2;


                                       // }

                                       if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")&&email.length()>0) {
                                           _email.setError("Invalid Email!");
                                           x = 2;

                                       }
                                       if (!password.matches("[0-9_-]+")) {
                                           Toast.makeText(getApplicationContext(), "Select a class", Toast.LENGTH_LONG).show();

                                           x = 2;

                                       }
                                       if (!phone.matches("[0-9_-]+")) {
                                           _phone.setError("Enter only numbers");
                                           x = 2;

                                       }
                                       if (x != 2) {

                                           boolean checkEmail = myDB.checkEmail(email);

                                           boolean isInserted = myDB.insertData(_fname.getText().toString(),
                                                   _lname.getText().toString(),
                                                   _email.getText().toString(),
                                                   _grade.getSelectedItem().toString(),
                                                   _phone.getText().toString());
                                           if (isInserted == true) {
                                               Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();

                                               _fname.setText("");
                                               _lname.setText("");
                                               _email.setText("");
                                               _grade.setSelection(0);
                                               _phone.setText("");
                                           }
                                           else{
                                               Toast.makeText(getApplicationContext(), "Registration not Successful", Toast.LENGTH_LONG).show();
                                           }}
                                   }
                               }
        );
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
