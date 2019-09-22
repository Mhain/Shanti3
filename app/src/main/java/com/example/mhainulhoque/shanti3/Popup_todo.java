package com.example.mhainulhoque.shanti3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class Popup_todo extends AppCompatActivity {

    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    EditText actyvty;
    EditText cost;
    Button addCost;
    TodoList todo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_todo);

        actyvty = (EditText) findViewById(R.id.activity);
        cost = (EditText) findViewById(R.id.cost);
        addCost = (Button) findViewById(R.id.addBtn);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;           
        getWindow().setLayout((int) (width * .8), (int) (height * .6));


        addCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String costActvy = cost.getText().toString();
                int  Tcost= Integer.parseInt(costActvy);
                String taskb = String.valueOf(actyvty.getText());
                 String task = Tcost +" "+ taskb;

                dbHelper.insertNewTask(task);

                todo.loadTaskList();



            }
        });

    }
}