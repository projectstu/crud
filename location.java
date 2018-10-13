package com.example.banurarandika.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class location extends AppCompatActivity implements AdapterView.OnItemLongClickListener{

    ListView listView;
    ArrayAdapter adapter;
    public static ArrayList<String> subs;
    ArrayList<Integer> subx;
    ArrayList<String> times;
    Activity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locaton);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Location");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_sch_location);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonclicked(view);

            }
        });

        subs = new ArrayList<>();
        times = new ArrayList<>();
        subx = new ArrayList<>();
        listView = (ListView)findViewById(R.id.schedulerList);
        loadSchedules();
        listView.setOnItemLongClickListener(this);
    }


    public void loadSchedules() {

        subs.clear();
        times.clear();
        subx.clear();
        String qu = "SELECT * FROM LOCATION ORDER BY id";
        Cursor cursor = MainActivity.handler.execQuery(qu);
        if(cursor==null||cursor.getCount()==0)
        {
            Toast.makeText(getBaseContext(),"No Locations Available",Toast.LENGTH_LONG).show();
        }else
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                subx.add(cursor.getInt(0));
                subs.add(cursor.getString(1) );
                cursor.moveToNext();
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1,subs);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
        loadSchedules();
        String selection[] = {"Update","Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select");
        builder.setItems(selection, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 1){
                    AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                    alert.setTitle("Delete Schedule?");
                    alert.setMessage("Do you want to delete this schedule ?");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String qu = "DELETE FROM LOCATION WHERE id = " + subx.get(position);
                            if (MainActivity.handler.execAction(qu)) {
                                Toast.makeText(getBaseContext(), "Deleted", Toast.LENGTH_LONG).show();
                                loadSchedules();
                            } else {
                                Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_LONG).show();
                                loadSchedules();
                            }
                            dialog.dismiss();
                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }else if(i == 0) {
                    updatebuttonclicked(view,subx.get(position),subs.get(position));
                }
            }
        });
        builder.show();
        loadSchedules(); 
        return true;
    }

    public void refresh(MenuItem item) {
        loadSchedules();
    }

    private void buttonclicked(View v){

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.class_location,null);
        final EditText eLocation = alertLayout.findViewById(R.id.location);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Add");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String location = eLocation.getText().toString();
                if(eLocation.length()<2)
                {
                    Toast.makeText(getBaseContext(),"Enter Valid Location",Toast.LENGTH_SHORT).show();
                    return;
                }
                String sql = "INSERT INTO LOCATION(classlocation) VALUES('"+location+"');";
                Log.d("Location", sql);
                if(MainActivity.handler.execAction(sql))
                {
                    Toast.makeText(getBaseContext(),"Location Successfully Added", Toast.LENGTH_LONG).show();
                    dialogInterface.dismiss();
                }else
                    Toast.makeText(getBaseContext(),"Failed To Add Location", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog dialog= alert.create();
        dialog.show();
    }

    private void updatebuttonclicked(View v,final int position,String loc){

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.class_location,null);
        final EditText eLocation = alertLayout.findViewById(R.id.location);
        eLocation.setText(loc);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Add");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String location = eLocation.getText().toString();
                if(eLocation.length()<2)
                {
                    Toast.makeText(getBaseContext(),"Enter Valid Location",Toast.LENGTH_SHORT).show();
                    return;
                }
                String sql = "UPDATE LOCATION SET classlocation = '"+location+"' where id = "+position+";";
                Log.d("Location", sql);
                if(MainActivity.handler.execAction(sql))
                {
                    Toast.makeText(getBaseContext(),"Update Successfully Done", Toast.LENGTH_LONG).show();
                    dialogInterface.dismiss();
                }else
                    Toast.makeText(getBaseContext(),"Failed To Update", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog dialog= alert.create();
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scheduler_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void first_schedule(){
        subs = new ArrayList<>();
        times = new ArrayList<>();
        subx = new ArrayList<>();
        subs.clear();
        times.clear();
        subx.clear();
        String qu = "SELECT * FROM LOCATION ORDER BY id";
        Cursor cursor = MainActivity.handler.execQuery(qu);
        if(cursor==null||cursor.getCount()==0)
        {

        }else
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                subx.add(cursor.getInt(0));
                subs.add(cursor.getString(1) );
                cursor.moveToNext();
            }
        }
    }

}
