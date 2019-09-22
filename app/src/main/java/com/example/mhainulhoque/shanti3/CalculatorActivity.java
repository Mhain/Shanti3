package com.example.mhainulhoque.shanti3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mhainulhoque.shanti3.R;

import java.util.ArrayList;

public class CalculatorActivity extends AppCompatActivity {
    ArrayList<String> costArray;
    ListView addCostListView;;
    EditText AddCostText,memberText;
    ArrayAdapter<String> arrayAdapter ;
    Button addBtn;
    Button totalCostbtn;
    Button perpersoncostBtn;
    String appreciation;
    double sum =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        addBtn =(Button)findViewById(R.id.addbutton) ;
        AddCostText = (EditText) findViewById(R.id.AddCostField);

        addCostListView = (ListView) findViewById(R.id.costList);
        memberText  =(EditText)findViewById(R.id.membertxt);

        costArray = new ArrayList<String>();

        loadArrayList(getApplicationContext());
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, costArray);
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                appreciation = AddCostText.getText().toString();
                if(appreciation.equals("")){
                    Toast.makeText(CalculatorActivity.this,"Please Enter value",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CalculatorActivity.this, "Added : "+AddCostText.getText(), Toast.LENGTH_SHORT).show();
                    costArray.add(appreciation);


                    saveArrayList();
                    arrayAdapter.notifyDataSetChanged();
                    AddCostText.setText("");


                }
            }

        });
        addCostListView.setAdapter(arrayAdapter);

        addCostListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                costArray.remove(position);
                saveArrayList();
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(CalculatorActivity.this,"Item Deleted",Toast.LENGTH_SHORT).show();

            }
        });



    }



    private  void saveArrayList(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putInt("appreciations_size",costArray.size());

        for(int i = 0 ; i < costArray.size();i++){
            editor.remove("appreciations"+i);
            editor.putString("appreciations"+i,costArray.get(i));
        }
        editor.commit();
    }

    private void loadArrayList(Context context){

        SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(context);
        costArray.clear();
        int size  = sharedPreferences2.getInt("appreciations_size",0);
        for(int i = 0 ; i<size ; i++){
            costArray.add(sharedPreferences2.getString("appreciations"+i,null));
        }

    }

    public void TotalCost(View view){


        sum =0;

        for(int i = 0 ; i<costArray.size() ; i++){
            sum = sum+Double.parseDouble(costArray.get(i));
        }
        Toast.makeText(CalculatorActivity.this,"Total Cost "+sum+" Tk ",Toast.LENGTH_LONG).show();



    }

    public void PerPersonCost(View view){
        String  mem = memberText.getText().toString();


        if(mem.equals("")){
            Toast.makeText(CalculatorActivity.this,"Pleaser Enter Total Member",Toast.LENGTH_SHORT).show();
        }
        else
        {
            sum =0;

            for(int i = 0 ; i<costArray.size() ; i++){
                sum = sum+Double.parseDouble(costArray.get(i));
            }
            double mems = Double.parseDouble(mem);
            double avg = sum/mems;
            Toast.makeText(CalculatorActivity.this,"Per Person Average Cost "+avg+" Tk ",Toast.LENGTH_LONG).show();



        }
    }


    public  void Clean(View view){


        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Do you definitely want to reset everything?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        costArray.clear();
                        arrayAdapter.clear();
                        arrayAdapter.notifyDataSetChanged();
                        memberText.setText("");
                        AddCostText.setText("");
                        saveArrayList();
                        Toast.makeText(CalculatorActivity.this,"Reset Done!",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No",null)
                .show();





    }
}
