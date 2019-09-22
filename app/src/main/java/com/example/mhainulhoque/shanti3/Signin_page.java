package com.example.mhainulhoque.shanti3;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Signin_page extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth myref;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    Button sigNIn;
    private EditText Email_box;
    private EditText pass_box;
    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;


    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin_page);

          sigNIn = (Button) findViewById(R.id.buttonSignin);
          Email_box = (EditText) findViewById(R.id.editSigninTextEmail);
          pass_box = (EditText) findViewById(R.id.editSinginTextpass);


        progressBar=(ProgressBar)findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);

        mAuth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener(){


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!= null){
                    Intent intent=new Intent(Signin_page.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        };








//Video background
        videoBG=(VideoView)findViewById(R.id.videoV);

        Uri uri=Uri.parse("android.resource://" // First start with this,
                + getPackageName() // then retrieve your package name,
                + "/" // add a slash,
                + R.raw.movie);

        videoBG.setVideoURI(uri);
        videoBG.start();

        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                // We want our video to play over and over so we set looping to true.
                mMediaPlayer.setLooping(true);
                // We then seek to the current posistion if it has been set and play the video.
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });








        TextView textView = findViewById(R.id.register);
        String text = "Do not have account yet?  REGISTER";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(Signin_page.this, Signup_page.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);
            }
        };

        ss.setSpan(clickableSpan,25,34,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());






//signin button








        sigNIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String email =Email_box.getText().toString();
                String pass =pass_box.getText().toString();

                if(TextUtils.isEmpty(email)){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Enter email address ",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(pass)){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Enter Password ",Toast.LENGTH_LONG).show();
                }
               else if(TextUtils.isEmpty(email) && TextUtils.isEmpty(pass)){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Enter Email and Password",Toast.LENGTH_LONG).show();
                }

                else {
                        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.VISIBLE);
                                if (task.isSuccessful()){
                                    progressBar.setVisibility(View.GONE);
                                    //Toast.makeText(Signin_page.this,"Incorrect user and pass",Toast.LENGTH_LONG).show();
                                   // Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    Intent intent = new Intent(Signin_page.this,MainActivity.class);
//                                    startActivity(intent);
                                }
                                else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(),"Login Faied.Give correct information",Toast.LENGTH_LONG).show();

                                }

                            }
                        });
                }

            }




        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Capture the current video position and pause the video.
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the video when resuming the Activity
        videoBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        mMediaPlayer.release();
        mMediaPlayer = null;
    }


}
