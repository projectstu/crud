package com.example.banurarandika.project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.banurarandika.project.DataBase.DataBase;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class Tute_List extends AppCompatActivity {

    DataBase myDB;
    ListView mylist, mylist1;
    Button search;
    EditText id, tname;
    String value1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tute__list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mylist = findViewById(R.id.displayview);
        mylist1 = findViewById(R.id.displayview1);

        id = findViewById(R.id.id1);

        //search = findViewById(R.id.searchb);
        //tname = findViewById(R.id.searchd);


        ArrayAdapter<String> adapter;
        ArrayAdapter<String> adapter1;


        //listview();

        myDB = new DataBase(this);
        Cursor res = myDB.viewData();


        ArrayList<String> idarr = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();

        while (res.moveToNext()) {
            String id = res.getString(0);
            String tname = res.getString(1);

            idarr.add(id);
            name.add(tname);

        }

        adapter = new ArrayAdapter<String>(this, simple_list_item_1, name);
        mylist.setAdapter(adapter);

        adapter1 = new ArrayAdapter<String>(this, simple_list_item_1, idarr);
        mylist1.setAdapter(adapter1);




        list();
    }

    public void list() {
        mylist1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String value = (String) mylist1.getItemAtPosition(position);
                        Toast.makeText(Tute_List.this, "value: " + value, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), UpdateTute.class);
                        value1 = (String) mylist1.getItemAtPosition(position);


                        intent.putExtra("value0", value1);
                        // startActivity(intent);
                        Tute_List.this.startActivity(intent);


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
