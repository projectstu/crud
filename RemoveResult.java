package com.example.banurarandika.project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.banurarandika.project.DataBase.SQLiteHelper;

public class RemoveResult extends AppCompatActivity {

    SQLiteHelper myDB = new SQLiteHelper(this);

    EditText id, name;
    Button deleteresult, search;
    String sname,idg = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Remove Result");
        myDB = new SQLiteHelper(this);

        id = (EditText) findViewById(R.id.iddd);
        //id1 = (EditText) findViewById()
        name = (EditText) findViewById(R.id.namedd);
        deleteresult = (Button) findViewById(R.id.removedd);
        search = (Button)findViewById(R.id.searchd);


        fill();
        searchd();
        DeleteProfile();


    }

    public void DeleteProfile() {
        deleteresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int x = 0;
                if ( id.equals("")) {
                    Toast.makeText(getApplicationContext(), "Need to Fill All Fields!", Toast.LENGTH_LONG).show();
                    x = 2;
                }

                if (!sname.matches("[a-zA-z]+")) {
                    name.setError("Enter only letters");
                    x = 2;


                }

                if (!id.getText().toString().matches("[0-9_-]+")) {
                    id.setError("Enter only numbers");
                    x = 2;


                }

                if (x != 2) {


                    Integer deleteRows = myDB.deleteData(id.getText().toString());
                    if (deleteRows > 0) {
                        Toast.makeText(getApplicationContext(), "Your Account Removed.", Toast.LENGTH_LONG).show();
                        name.setText("");
                        id.setText("");
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Wrong ID", Toast.LENGTH_LONG).show();


                }
            }
        });

    }

    public void searchd() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((id.getText().toString().equals(""))){
                    Toast.makeText(RemoveResult.this,"enter ID First",Toast.LENGTH_LONG).show();
                }

                if(!(id.getText().toString().matches("[0-9_-]+"))){

                    id.setError("Enter only numbers");
                }

                Cursor res = myDB.search(id.getText().toString());
                if(res.moveToNext()) {
                    // idg = res.getString(0);
                    sname = res.getString(1);
                    name.setText(sname);

                }



                else if(id.getText().toString().length()>0) {
                    Toast.makeText(RemoveResult.this,"Entered ID is not in the list!",Toast.LENGTH_SHORT).show();


                }

            }
        });

    }

    public void fill(){
        // Student_List s1=new Student_List();
        //value=s1.passvalue1();


        Intent intent=getIntent();
        String value1=intent.getStringExtra("value0");





        Cursor rs =myDB.searchname(value1);


        if (rs.moveToNext()){
            idg = rs.getString(0);
            sname = rs.getString(1);

            id.setText(idg);
            name.setText(sname);







        }


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
