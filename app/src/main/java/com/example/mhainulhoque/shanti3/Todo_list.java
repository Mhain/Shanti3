package com.example.mhainulhoque.shanti3;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Todo_list extends AppCompatActivity {
    Button totalCost;
    ListView lstTask;
    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        dbHelper = new DbHelper(this);
        totalCost = (Button) findViewById(R.id.totalCost);


        lstTask = (ListView) findViewById(R.id.lstTask);


        totalCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Will add", Toast.LENGTH_SHORT);
                toast.setMargin(10, 30);
                toast.show();
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.popFloat);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Todo_list.this, Popup_todo.class);
                startActivity(intent);
            }
        });


    }



}