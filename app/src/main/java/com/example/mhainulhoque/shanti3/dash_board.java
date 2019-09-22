package com.example.mhainulhoque.shanti3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class dash_board extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board);
    }
    public void loc(View view){

        Intent intent=new Intent(dash_board.this,Loc_details.class);
        startActivity(intent);

    }

    public void food(View view){

        Intent intent=new Intent(dash_board.this,foods_drinks.class);
        startActivity(intent);

    }

    public void DayPlan(View view){

        Intent intent=new Intent(dash_board.this,TodoList.class);
        startActivity(intent);

    }
}
