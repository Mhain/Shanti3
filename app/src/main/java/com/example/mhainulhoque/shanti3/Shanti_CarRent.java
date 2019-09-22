package com.example.mhainulhoque.shanti3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

public class Shanti_CarRent extends AppCompatActivity {

        private static final String TAG ="Shanti_CarRent";

        Button book;

        private TextView pDisplayDate;
        private TextView rDisplayDate;
        private TextView pDisplayTime;
        private TextView rDisplayTime;

        EditText pickLoc;
        EditText dropLoc;

        FirebaseFirestore db;
        FirebaseAuth mAuth;
        FirebaseUser user;


        private String pDate;
        private String ptime;
        private String rDate;
        private String rTime;


        private DatePickerDialog.OnDateSetListener pDatasetListener;
        private DatePickerDialog.OnDateSetListener rDatasetListener;

    private TimePickerDialog.OnTimeSetListener pTimeListener;
    private TimePickerDialog.OnTimeSetListener rTimeListener;

    ImageView car1;
    ImageView car2;
    ImageView noah1;
    ImageView noah2;
    ImageView hiace1;
    ImageView hiace2;

    TextView alrt;

    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shanti__car_rent);

        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();


        book=(Button)findViewById(R.id.cfb);

        car1=(ImageView)findViewById(R.id.car1);
        car2=(ImageView)findViewById(R.id.car2);
        noah1=(ImageView)findViewById(R.id.noah1);
        noah2=(ImageView)findViewById(R.id.noah2);
        hiace1=(ImageView)findViewById(R.id.hiace1);
        hiace2=(ImageView)findViewById(R.id.hiace2);

        alrt=(TextView)findViewById(R.id.alrt);

        checkBox=(CheckBox)findViewById(R.id.checkbox);

        car1.setVisibility(car1.INVISIBLE);
        noah1.setVisibility(noah1.INVISIBLE);
        hiace1.setVisibility(hiace1.INVISIBLE);

    pickLoc=(EditText)findViewById(R.id.pLoc);
    dropLoc=(EditText)findViewById(R.id.dLoc);

    pDisplayDate =(TextView) findViewById(R.id.pickDate);
    rDisplayDate=(TextView) findViewById(R.id.retDate);
    pDisplayTime=(TextView) findViewById(R.id.pickTime);
    rDisplayTime=(TextView)findViewById(R.id.retTime);

    rDisplayDate.setVisibility(rDisplayDate.INVISIBLE);
    rDisplayTime.setVisibility(rDisplayTime.INVISIBLE);

    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                rDisplayDate.setVisibility(rDisplayDate.VISIBLE);
                rDisplayTime.setVisibility(rDisplayTime.VISIBLE);
            }
            else {
                rDisplayDate.setVisibility(rDisplayDate.INVISIBLE);
                rDisplayTime.setVisibility(rDisplayTime.INVISIBLE);
            }
        }
    });

    pDisplayDate.setOnClickListener(new View.OnClickListener(){


        @Override
        public void onClick(View v) {
            Calendar cal=Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog =new DatePickerDialog(Shanti_CarRent.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    pDatasetListener,year,month,day
                    );
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    });

    pDisplayTime.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar cal=Calendar.getInstance();
            int hour=cal.get(Calendar.HOUR_OF_DAY);
            int minute=cal.get(Calendar.MINUTE);
            boolean is12HourView = false;
         TimePickerDialog  timePickerDialog;
            timePickerDialog = new TimePickerDialog(Shanti_CarRent.this,pTimeListener,
                    hour,minute,is12HourView);

            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            timePickerDialog.show();
        }
    });



        pDatasetListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                Log.d(TAG,"onDataSet: mm/dd/yyyy:"+month+"/"+day+"/"+year);
                String date=month+"/"+day+"/"+year;
                pDisplayDate.setText(date);
                pDate=date;

            }
        };

        
        
pTimeListener=new TimePickerDialog.OnTimeSetListener() {
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String AMPM = "AM";
        if(hourOfDay>11)
        {
            //Get the current hour as AM PM 12 hour format
            hourOfDay = hourOfDay-12;
            AMPM = "PM";
        }
        Log.d(TAG, "onTimeSet: hour/minute:"+ hourOfDay +"/"+minute );
        String time=hourOfDay+":"+minute+":"+AMPM;
        pDisplayTime.setText(time);
         ptime=time;
    }
};





    //return date


        rDisplayDate.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =new DatePickerDialog(Shanti_CarRent.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        rDatasetListener,year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });



        rDisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int hour=cal.get(Calendar.HOUR_OF_DAY);
                int minute=cal.get(Calendar.MINUTE);
                boolean is12HourView = false;
                TimePickerDialog  timePickerDialog;
                timePickerDialog = new TimePickerDialog(Shanti_CarRent.this,rTimeListener,
                        hour,minute,is12HourView);

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        rDatasetListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                Log.d(TAG,"onDataSet: mm/dd/yyyy:"+month+"/"+day+"/"+year);
                String date=month+"/"+day+"/"+year;
                rDisplayDate.setText(date);
                rDate=date;
            }
        };



        rTimeListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String AMPM = "AM";
                if(hourOfDay>11)
                {
                    //Get the current hour as AM PM 12 hour format
                    hourOfDay = hourOfDay-12;
                    AMPM = "PM";
                }
                Log.d(TAG, "onTimeSet: hour/minute:"+ hourOfDay +"/"+minute );
                String time=hourOfDay+":"+minute+":"+AMPM;
                rDisplayTime.setText(time);
                rTime=time;
            }
        };

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final String pickL =pickLoc.getText().toString();
            final String returnL =dropLoc.getText().toString();



                if(TextUtils.isEmpty(pDate) || TextUtils.isEmpty(ptime) || TextUtils.isEmpty(pickL) || TextUtils.isEmpty(returnL) || TextUtils.isEmpty(rTime) ||TextUtils.isEmpty(rDate)  ){
                    Toast.makeText(getApplicationContext(), "Insert all the details", Toast.LENGTH_SHORT).show();

                }
                else {
                    FirebaseUser user=mAuth.getCurrentUser();
                    String uid=user.getUid();
//                   final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        //           String usersa ="tbl_user/"+uid+"/name";
//                DatabaseReference ref = database.getReference();
//              //      Log.d("url",ref.toString());
//
////                    DatabaseReference usetble = FirebaseDatabase.getInstance().getReference("tbl_user");
////                    DatabaseReference useid = usetble.child(uid.toString());
////                    Log.wtf("userid ", useid.toString());
////                  DatabaseReference username = useid.child("name");
////                    Log.wtf("useridname ", username.toString());
//                    ref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Log.i(TAG, dataSnapshot.child("name").getValue(String.class));
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            Log.w(TAG, "onCancelled", databaseError.toException());
//                        }
//                    });
//                    //Log.wtf("username ",uid.toString());
                    Map<String,Object> newContact =new HashMap<>();
                    newContact.put("PickUp location ",pickL);
                    newContact.put("PickUp time ",ptime);
                    newContact.put("PickUp date ",pDate);
                    newContact.put("Return Location",returnL);
                    newContact.put("Return time",rTime);
                    newContact.put("Return date ",rDate);
                    db.collection("tbl_rental").document(uid)
                            .set(newContact)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Shanti_CarRent.this,"Car booked,we will call you in a few moments",Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Data not saved",e.getMessage());
                                }
                            });
                }

            }
        });
    }

    public void car(View view){
        car1.setVisibility(car1.VISIBLE);
        car2.setVisibility(car2.INVISIBLE);
        noah1.setVisibility(noah1.INVISIBLE);
        noah2.setVisibility(noah2.VISIBLE);
        hiace1.setVisibility(hiace1.INVISIBLE);
        hiace2.setVisibility(hiace2.VISIBLE);

        alrt.setText("* This service is for 4 person");

    }

    public void noah(View view){
        car1.setVisibility(car1.INVISIBLE);
        car2.setVisibility(car2.VISIBLE);
        noah1.setVisibility(noah1.VISIBLE);
        noah2.setVisibility(noah2.INVISIBLE);
        hiace1.setVisibility(hiace1.INVISIBLE);
        hiace2.setVisibility(hiace2.VISIBLE);
        alrt.setText("* This service is for 6 person");

    }

    public void hiace(View view){
        car1.setVisibility(car1.INVISIBLE);
        car2.setVisibility(car2.VISIBLE);
        noah1.setVisibility(noah1.INVISIBLE);
        noah2.setVisibility(noah2.VISIBLE);
        hiace1.setVisibility(hiace1.VISIBLE);
        hiace2.setVisibility(hiace2.INVISIBLE);
        alrt.setText("* This service is for 9 person");
    }


}
