package com.example.banurarandika.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.banurarandika.project.DataBase.SQLiteHelper;

import java.util.ArrayList;

public class AddResult extends AppCompatActivity {
    SQLiteHelper myDB;
    EditText _sname;
    EditText _number;
    EditText _marks;
    Spinner _grade;
    EditText _gpa;
    Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_result);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Result");
        myDB = new SQLiteHelper(this);

        _sname = (EditText) findViewById(R.id.sname);
        _number = (EditText) findViewById(R.id.number);
        _marks = (EditText) findViewById(R.id.marks);
        _grade = (Spinner) findViewById(R.id.grade11);
        _gpa = (EditText) findViewById(R.id.gpa);
        reg = (Button) findViewById(R.id.insert);

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

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _grade.setAdapter(adapter1);

        AddData();
    }
    public void AddData(){
        reg.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

                                       String sname =_sname.getText().toString();
                                       String number =_number.getText().toString();
                                       String marks =_marks.getText().toString();
                                       String grade =_grade.getSelectedItem().toString();
                                       String gpa =_gpa.getText().toString();





                                       int x = 0;

                                       if (sname.equals("") || number.equals("") || marks.equals("") || gpa.equals("")) {
                                           Toast.makeText(getApplicationContext(), "Need to Fill All Fields!", Toast.LENGTH_LONG).show();
                                           x = 2;
                                       }

                                      /* if (!sname.matches("[a-zA-z]+")) {
                                           _sname.setError("Enter only letters");
                                           x = 2;


                                       }*/
                                      if(!sname.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")){
                                            _sname.setError("Enter only letters");
                                            x=2;

                                       }
                                       // if (!lname.matches("[a-zA-z]+")) {
                                       //  _lname.setError("Enter only letters");
                                       //  x = 2;


                                       // }

                                     /*  if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")&&email.length()>0) {
                                           _email.setError("Enter only letters");
                                           x = 2;

                                       }*/
                                       if (!grade.matches("[0-9_-]+")) {
                                           Toast.makeText(getApplicationContext(), "Select a class", Toast.LENGTH_LONG).show();

                                           x = 2;

                                       }
                                       /*if (!phone.matches("[0-9_-]+")) {
                                           _phone.setError("Enter only numbers");
                                           x = 2;

                                       }*/
                                       if (x != 2) {

                                          // boolean checkEmail = myDB.checkEmail(email);

                                           boolean isInserted = myDB.insertData(_sname.getText().toString(),
                                                   _number.getText().toString(),
                                                   _marks.getText().toString(),
                                                   _grade.getSelectedItem().toString(),
                                                   _gpa.getText().toString());
                                           if (isInserted == true) {
                                               Toast.makeText(getApplicationContext(), "Add Successfully", Toast.LENGTH_LONG).show();

                                               _sname.setText("");
                                               _number.setText("");
                                               _marks.setText("");
                                               _grade.setSelection(0);
                                               _gpa.setText("");
                                           }
                                           else{
                                               Toast.makeText(getApplicationContext(), "  Unsuccessful", Toast.LENGTH_LONG).show();
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
