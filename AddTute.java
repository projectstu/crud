package com.example.banurarandika.project;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.banurarandika.project.DataBase.DataBase;

import java.util.ArrayList;

public class AddTute extends AppCompatActivity {
    DataBase myDB;
    EditText _tname;
    EditText _number;
    EditText _subject;
    EditText _description;
    Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tute);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDB = new DataBase(this);

        _tname = (EditText) findViewById(R.id.tname);
        _number = (EditText) findViewById(R.id.number);
        _subject = (EditText) findViewById(R.id.subject);
        _description = (EditText) findViewById(R.id.description);
        reg = (Button) findViewById(R.id.insert);


        AddData();
    }
    public void AddData(){
        reg.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

                                       String tname =_tname.getText().toString();
                                       String number =_number.getText().toString();
                                       String subject =_subject.getText().toString();
                                       String description =_description.getText().toString();





                                       int x = 0;

                                       if (tname.equals("") || number.equals("") || subject.equals("") || description.equals("")) {
                                           Toast.makeText(getApplicationContext(), "Need to Fill All Fields!", Toast.LENGTH_LONG).show();
                                           x = 2;
                                       }


                                       if(!tname.matches("^[a-zA-a-]{3,15}$")){
                                           _tname.setError("Enter only letters");
                                           x=2;

                                       }

                                       if (x != 2) {



                                           boolean isInserted = myDB.insertData(_tname.getText().toString(),
                                                   _number.getText().toString(),
                                                   _subject.getText().toString(),
                                                   _description.getText().toString());
                                           if (isInserted == true) {
                                               Toast.makeText(getApplicationContext(), "Add Successfully", Toast.LENGTH_LONG).show();

                                               _tname.setText("");
                                               _number.setText("");
                                               _subject.setText("");
                                               _description.setText("");
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

