package com.example.vicky.noteapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView listView;
   static ArrayAdapter adapter;

  static   ArrayList<String> notes=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= (ListView) findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.vicky.noteapp", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("notes", null);


        if (set == null) {

            notes.add("Example note");

        } else {

            notes = new ArrayList(set);

        }
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),NoteEditingActivity.class);
                intent.putExtra("noteID",position);
                startActivity(intent);
            }
        });

       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
              final int itemToDelete=position;
               new AlertDialog.Builder(MainActivity.this)
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .setTitle("Are you sure?")
                       .setMessage("Do you want to delete this note?")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i) {

                                       notes.remove(itemToDelete);
                                       adapter.notifyDataSetChanged();
                                  SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.vicky.noteapp", Context.MODE_PRIVATE);
                                       HashSet<String>set=new HashSet<String>(MainActivity.notes);
                                       sharedPreferences.edit().putStringSet("notes",set).apply();


                                   }
                               }
                       )
                       .setNegativeButton("No", null)
                       .show();

               return true;
           }
       });




    }










    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add) {

            Intent intent = new Intent(getApplicationContext(),NoteEditingActivity.class);

            startActivity(intent);

            return true;

        }

        return false;
    }
}
