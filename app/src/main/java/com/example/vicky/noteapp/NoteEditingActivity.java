package com.example.vicky.noteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditingActivity extends AppCompatActivity {
    int noteId;
    EditText noteEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editing);
        noteEdit= (EditText) findViewById(R.id.editText);
        Intent intent=getIntent();
        noteId =intent.getIntExtra("noteID",-1);

        if (noteId !=-1){
            noteEdit.setText(MainActivity.notes.get(noteId));
        }else {
            MainActivity.notes.add("");
            noteId=MainActivity.notes.size()-1;
            MainActivity.adapter.notifyDataSetChanged();


        }
        noteEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteId,String.valueOf(s));
                MainActivity.adapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.vicky.noteapp", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet(MainActivity.notes);

                sharedPreferences.edit().putStringSet("notes", set).apply();


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
