package com.example.banurarandika.project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banurarandika.project.DataBase.DatabaseHelper;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_list_item_2;

public class Student_list1 extends AppCompatActivity {
    DatabaseHelper myDB;
    ListView mylist,mylist1;
    Button search;
    EditText id;
    String  value1=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student1__list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mylist = findViewById(R.id.displayview);
        mylist1 = findViewById(R.id.displayview1);

        id = findViewById(R.id.id1);
        search = findViewById(R.id.search);


        ArrayAdapter<String> adapter;
        ArrayAdapter<String> adapter1;

       /* mylist= (ListView)findViewById(R.id.yt);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.activity_exam__list,NAMES );
        mylist.setAdapter(adapter1);
*/

        //listview();

        myDB = new DatabaseHelper(this);
        Cursor res = myDB.viewData();


        ArrayList<String> idarr = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();

        while (res.moveToNext()) {
            String id = res.getString(0);
            String fname = res.getString(1);
            // String lname = res.getString(2);


            idarr.add(id);
            name.add(fname);

        }

        adapter = new ArrayAdapter<String>(this, simple_list_item_1, name);
        mylist.setAdapter(adapter);

        adapter1 = new ArrayAdapter<String>(this, simple_list_item_1, idarr);
        mylist1.setAdapter(adapter1);



        /*search.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {

                idget=id.getText().toString();
                Cursor rs = myDB.search(idget);
                ArrayList<String> searchlist = new ArrayList<>();


                while (rs.moveToNext()) {

                    String id = rs.getString(0);
                    String fname = rs.getString(1);
                    String lname = rs.getString(2);

                    searchlist.add(id + " " + fname + " " + lname);
                }
                //ArrayAdapter<String> adapter2= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, searchlist);

            }

        });*/

        list();
    }
    public void list() {
        mylist1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String value = (String) mylist1.getItemAtPosition(position);
                        Toast.makeText(Student_list1.this, "value: " + value, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), RemoveProfile.class);
                        value1 = (String) mylist1.getItemAtPosition(position);




                        intent.putExtra("value0",value1);
                        // startActivity(intent);
                        Student_list1.this.startActivity(intent);


                        //list();

                    }
                }
        );
        //return value1;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


}
