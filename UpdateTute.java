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

import com.example.banurarandika.project.DataBase.DataBase;

import java.util.ArrayList;

public class UpdateTute extends AppCompatActivity {

    DataBase myDB = new DataBase(this);
    EditText _tname;
    EditText _number;
    EditText _subject;
    EditText _description;
    Button updatetute;

    Button search;
    EditText id;
    //String idget = null;
    String idg,tname,number,subject,/*subject*/value,description=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_tute);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  myDB = new DatabaseHelper(this);

        _tname = (EditText) findViewById(R.id.tname);
        _number = (EditText) findViewById(R.id.number);
        _subject = (EditText) findViewById(R.id.subject);
        _description = (EditText) findViewById(R.id.description);
        updatetute = (Button) findViewById(R.id.button255);

        // Cursor
        id = (EditText) findViewById(R.id.id1);
        search = (Button) findViewById(R.id.search);


        //idget = id.getText().toString();

        fill();
        UpdateProfile();
        search1();
        }
        public void UpdateProfile() {
        updatetute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tname = _tname.getText().toString();
                number = _number.getText().toString();
                subject = _subject.getText().toString();
                description = _description.getText().toString();

                int x = 0;

                if (tname.equals("") || number.equals("")  || description.equals("")) {
                    Toast.makeText(getApplicationContext(), "Need to Fill All Fields without email!", Toast.LENGTH_LONG).show();
                    x = 2;
                }

                if (!tname.matches("[a-zA-z]+")) {
                    _tname.setError("Enter only letters");
                    x = 2;
                    }

                if (!description.matches("[a-zA-z]+")) {
                    _description.setError("Enter only letters");
                    x = 2;
                    }

                int z=0;
                Cursor rs=myDB.searchname(id.getText().toString());

                if((rs.moveToNext()==true)&&(x!=2)){


                    boolean isUpdated = myDB.updateData(id.getText().toString(),_tname.getText().toString(),
                            _number.getText().toString(),
                            _subject.getText().toString(),
                            _description.getText().toString());
                    if (isUpdated == true) {
                        Toast.makeText(getApplicationContext(), "Data updated", Toast.LENGTH_LONG).show();

                        id.setText("");
                        _tname.setText("");
                        _number.setText("");
                        _subject.setText("");
                        _description.setText("");
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
                    Toast.makeText(UpdateTute.this,"enter ID First",Toast.LENGTH_LONG).show();
                }

                if(!(id.getText().toString().matches("[0-9_-]+"))){

                    id.setError("Enter only numbers");
                }


                Cursor res = myDB.search(id.getText().toString());
                if (res.moveToNext()) {
                    // idg = res.getString(0);
                    tname = res.getString(1);
                    number = res.getString(2);
                    subject=res.getString(3);
                    description=res.getString(5);

                    _tname.setText(tname);
                    _number.setText(number);

                    _subject.setText(subject);
                    _description.setText(description);
                    }
                    else if((id.getText().toString().length()>0)){
                    Toast.makeText(UpdateTute.this,"Entered ID is not in the list!!",Toast.LENGTH_SHORT).show();

                    _tname.setText("");
                    _number.setText("");

                    _subject.setText("");
                    _description.setText("");
                }
                }
        });
    }

    public void fill(){
        // Tute_List t1=new Tute_List();
        //value=s1.passvalue1();


        Intent intent=getIntent();
        String value1=intent.getStringExtra("value0");
        _number.setText(value1);

        Cursor rs =myDB.searchname(value1);

        if (rs.moveToNext()){
            idg = rs.getString(0);
            tname = rs.getString(1);
            number = rs.getString(2);
            subject=rs.getString(3);
            description=rs.getString(4);

            _tname.setText(tname);
            _number.setText(number);
            id.setText(idg);

            _subject.setText(subject);
            _description.setText(description);
            }
            }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
