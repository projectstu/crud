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

import com.example.banurarandika.project.DataBase.SQLiteHelper;

import java.util.ArrayList;

public class UpdateResult extends AppCompatActivity {

    SQLiteHelper myDB = new SQLiteHelper(this);
    EditText _sname;
    EditText _number;
    EditText _marks;
    Spinner _grade;
    EditText _gpa;
    Button updateresult;

    Button search;
    EditText id;
    //String idget = null;
    String idg,sname,number,marks,grade,value,gpa=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  myDB = new DatabaseHelper(this);

        _sname = (EditText) findViewById(R.id.sname);
        _number = (EditText) findViewById(R.id.number);
        _marks = (EditText) findViewById(R.id.marks);
        _grade = (Spinner)findViewById(R.id.grade11);
        _gpa = (EditText) findViewById(R.id.gpa);
        updateresult = (Button) findViewById(R.id.button255);

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
        updateresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sname = _sname.getText().toString();
                number = _number.getText().toString();
                marks = _marks.getText().toString();
                grade = _grade.getSelectedItem().toString();
                gpa = _gpa.getText().toString();

                int x = 0;

                if (sname.equals("") || number.equals("")  || grade.equals("") || gpa.equals("")) {
                    Toast.makeText(getApplicationContext(), "Need to Fill All Fields without email!", Toast.LENGTH_LONG).show();
                    x = 2;
                }

                if (!sname.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                    _sname.setError("Enter only letters");
                    x = 2;


                }
                //if (!lname.matches("[a-zA-z]+")) {
                //    _lname.setError("Enter only letters");
                //    x = 2;


                //}

              /*  if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")&&email.length()>0) {
                    _email.setError("Enter valid email");
                    x = 2;

                }*/
                if (!grade.matches("[0-9_-]+")) {
                    Toast.makeText(getApplicationContext(), "Select a class", Toast.LENGTH_LONG).show();

                    x = 2;

                }
                if (!gpa.matches("[0-9_-]+")) {
                    _gpa.setError("Enter only numbers");
                    x = 2;

                }

                int z=0;
                Cursor rs=myDB.searchname(id.getText().toString());

                if((rs.moveToNext()==true)&&(x!=2)){


                    boolean isUpdated = myDB.updateData(id.getText().toString(),_sname.getText().toString(),
                            _number.getText().toString(),
                            _marks.getText().toString(),
                            _grade.getSelectedItem().toString(),
                            _gpa.getText().toString());
                    if (isUpdated == true) {
                        Toast.makeText(getApplicationContext(), "Data updated", Toast.LENGTH_LONG).show();

                        id.setText("");
                        _sname.setText("");
                        _number.setText("");
                        // _lname.setText(lname);
                        _marks.setText("");
                        _grade.setSelection(0);
                        _gpa.setText("");
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
                    Toast.makeText(UpdateResult.this,"enter ID First",Toast.LENGTH_LONG).show();
                }

                if(!(id.getText().toString().matches("[0-9_-]+"))){

                    id.setError("Enter only numbers");
                }


                Cursor res = myDB.search(id.getText().toString());
                if (res.moveToNext()) {
                    // idg = res.getString(0);
                    sname = res.getString(1);
                    number = res.getString(2);
                    marks=res.getString(3);
                    grade=res.getString(4);
                    gpa=res.getString(5);

                    _sname.setText(sname);
                    _number.setText(number);

                    _marks.setText(marks);
                    _grade.setSelection(Integer.parseInt(grade));
                    _gpa.setText(gpa);



                }


                else if((id.getText().toString().length()>0)){
                    Toast.makeText(UpdateResult.this,"Entered ID is not in the list!!",Toast.LENGTH_SHORT).show();

                    _sname.setText("");
                    _number.setText("");

                    _marks.setText("");
                    _grade.setSelection(Integer.parseInt(grade));
                    _gpa.setText("");
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
        _number.setText(value1);




        Cursor rs =myDB.searchname(value1);


        if (rs.moveToNext()){
            idg = rs.getString(0);
            sname = rs.getString(1);
            number = rs.getString(2);
            marks=rs.getString(3);
            grade=rs.getString(4);
            gpa=rs.getString(5);

            _sname.setText(sname);
            _number.setText(number);
            id.setText(idg);

            _marks.setText(marks);
            _grade.setSelection(Integer.parseInt(grade));
            _gpa.setText(gpa);



        }


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
