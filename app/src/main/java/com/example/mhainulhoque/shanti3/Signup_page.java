package com.example.mhainulhoque.shanti3;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup_page extends AppCompatActivity {
    Button signup;
    private EditText name;
    private EditText email;
    private EditText phoneNum;
    private EditText password;
    private EditText confpass;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);


        firebaseAuth=FirebaseAuth.getInstance();

        db=FirebaseFirestore.getInstance();


        progressBar=(ProgressBar)findViewById(R.id.progress);
//Vedio background
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
                // We wconfpassant our video to play over and over so we set looping to true.
                mMediaPlayer.setLooping(true);
                // We then seek to the current posistion if it has been set and play the video.
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });

        progressBar.setVisibility(View.INVISIBLE);





//signup functiom
        signup = (Button) findViewById(R.id.buttonRegister);
        name = (EditText) findViewById(R.id.editTextName);
        email = (EditText) findViewById(R.id.editEmail);
        phoneNum = (EditText) findViewById(R.id.editTextPhoneNumber);
        password = (EditText) findViewById(R.id.editTextpass);
        confpass = (EditText) findViewById(R.id.editTextConPass);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                final String nameStr = name.getText().toString();
                final String emailStr = email.getText().toString();
                final String phoneNumStr = phoneNum.getText().toString();
                String passStr = password.getText().toString();
                String conpassStr = confpass.getText().toString();

                if(TextUtils.isEmpty(nameStr)&& TextUtils.isEmpty(emailStr) && TextUtils.isEmpty(phoneNumStr) && TextUtils.isEmpty(passStr) && TextUtils.isEmpty(conpassStr)){
                    Toast.makeText(getApplicationContext(), "Insert Your Details", Toast.LENGTH_SHORT).show();

                   progressBar.setVisibility(View.INVISIBLE);
                }
                else if(TextUtils.isEmpty(nameStr)){
                    Toast.makeText(getApplicationContext(), "Insert your name", Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.INVISIBLE);
                }

                else if(TextUtils.isEmpty(emailStr)){
                    Toast.makeText(getApplicationContext(), "Insert your Email", Toast.LENGTH_SHORT).show();

                   progressBar.setVisibility(View.INVISIBLE);
                }

                else if(TextUtils.isEmpty(phoneNumStr)){
                    Toast.makeText(getApplicationContext(), "Insert your Phone number", Toast.LENGTH_SHORT).show();


                    progressBar.setVisibility(View.INVISIBLE);
                }

                else if(TextUtils.isEmpty(passStr)){
                    Toast.makeText(getApplicationContext(), "Insert a Password", Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.INVISIBLE);
                }

                else if(TextUtils.isEmpty(nameStr)){
                    Toast.makeText(getApplicationContext(), "Insert Confirm Passsword", Toast.LENGTH_SHORT).show();

                  progressBar.setVisibility(View.INVISIBLE);
                }

                else if (!passStr.equals(conpassStr)) {
                    Toast.makeText(getApplicationContext(), "Password don't match!", Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.INVISIBLE);
                }
                else {
//                    Intent intent = new Intent(Signup_page.this, Signin_page.class);
//                    startActivity(intent);


                    firebaseAuth.createUserWithEmailAndPassword(emailStr,passStr)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                               progressBar.setVisibility(View.GONE);
                                    if(task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        Map<String,Object>newContact =new HashMap<>();
                                        newContact.put("name ",nameStr);
                                        newContact.put("email ",emailStr);
                                        newContact.put("phone",phoneNumStr);

                                        FirebaseUser user=firebaseAuth.getCurrentUser();
                                        String uid=user.getUid();
                                        db.collection("tbl_user").document(uid)
                                                .set(newContact)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(Signup_page.this,"Data saved",Toast.LENGTH_LONG).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("Data not saved",e.getMessage());
                                                    }
                                                });



                                    }
                                    else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Failed to Register or User already exist", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                        }
            }
        });






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


