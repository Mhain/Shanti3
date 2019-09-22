package com.example.mhainulhoque.shanti3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private slideApdater myAdpter;

    private DrawerLayout dl;
    private  ActionBarDrawerToggle abd;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager=(ViewPager)findViewById(R.id.viewPager);
        myAdpter=new slideApdater(this);
        viewPager.setAdapter(myAdpter);

            dl=(DrawerLayout)findViewById(R.id.dl);
            abd=new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
            abd.setDrawerIndicatorEnabled(true);

            dl.addDrawerListener(abd);
            abd.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            NavigationView nav_view=(NavigationView)findViewById(R.id.nav_view);
            nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id= menuItem.getItemId();
                if (id==R.id.User_profile){
                    Intent intent = new Intent(MainActivity.this,UserProfile.class);
                    startActivity(intent);
                }
                if (id==R.id.emergency){
                    Toast.makeText(MainActivity.this,"emergency",Toast.LENGTH_SHORT).show();
                }
                if (id==R.id.logOut){
                    mAuth=FirebaseAuth.getInstance();
                    mAuth.signOut();
                    startActivity(new Intent(MainActivity.this,Signin_page.class));

                }
                        return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return abd.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);


    }

    public void Slider_click(View view){

        Intent intent=new Intent(MainActivity.this,dash_board.class);
        startActivity(intent);

    }


    public void todo(View view){

        Intent intent=new Intent(MainActivity.this,CalculatorActivity.class);
        startActivity(intent);

    }

    public void RentCar(View view){

        Intent intent=new Intent(MainActivity.this,Shanti_CarRent.class);
        startActivity(intent);

    }



}
