package com.example.mhainulhoque.shanti3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    Thread mythread=new Thread(){
        @Override
        public void run() {
            try {
                sleep(3500);
                Intent intent=new Intent(getApplicationContext(),Signin_page.class);
                startActivity(intent);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    mythread.start();
    }
}
