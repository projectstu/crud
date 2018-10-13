package com.example.banurarandika.project;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchScheduler extends AppCompatActivity {
    EditText keyword ;
    Spinner select;
    Button search;
    ListView listView;
    ArrayList<String> subs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_scheduler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search");

        subs = new ArrayList<>();
        keyword = (EditText) findViewById(R.id.key);
        select = (Spinner) findViewById(R.id.spinnerselect);
        search = (Button) findViewById(R.id.button4);
        listView = (ListView) findViewById(R.id.searchrList);

        ArrayList<String> list = new ArrayList<>();
        list.add("Schedule");
        list.add("Location");

        ArrayAdapter<String> adapter=new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select.setAdapter(adapter);

        search();
    }

    private void search(){
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subs.clear();
                if(keyword.getText().toString().equals("")){
                    Toast.makeText(SearchScheduler.this,"Enter the search keyword",Toast.LENGTH_LONG).show();
                    ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1,subs);
                    listView.setAdapter(adapter);
                }else{
                    Cursor cursor=null;
                    String selectkey= select.getSelectedItem().toString();
                    String key = keyword.getText().toString();

                    if(selectkey.equals("Schedule")) {
                        String qu = "SELECT * FROM " + selectkey +" WHERE subject LIKE '%"+key+"%'";
                        cursor = MainActivity.handler.execQuery(qu);
                    }else if(selectkey.equals("Location")){
                        String qu = "SELECT * FROM " + selectkey +" WHERE classlocation LIKE '%"+key+"%'";
                        cursor = MainActivity.handler.execQuery(qu);
                    }

                    if(cursor==null||cursor.getCount()==0)
                    {
                        Toast.makeText(getBaseContext(),"No Results Available",Toast.LENGTH_LONG).show();
                    }else
                    {
                        cursor.moveToFirst();
                        while(!cursor.isAfterLast())
                        {
                            if(selectkey.equals("Schedule")) {
                                subs.add(cursor.getString(1) + "\nfor " + cursor.getString(0) + "\nat " + cursor.getString(2) + " : " + cursor.getString(3));
                                cursor.moveToNext();
                            }else if(selectkey.equals("Location")){
                                subs.add(cursor.getString(1) );
                                cursor.moveToNext();
                            }
                        }
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1,subs);
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
