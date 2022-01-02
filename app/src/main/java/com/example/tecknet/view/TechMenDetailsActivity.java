package com.example.tecknet.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tecknet.R;
import com.example.tecknet.controller.technician_controller;
import com.example.tecknet.model.UserInt;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This is a screen for entering additinal informaion when technition is signing up.
 */
public class TechMenDetailsActivity extends AppCompatActivity {
    Spinner area;
    Button signUp;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_men_details);

        //todo move this to the controller
        rootNode = FirebaseDatabase.getInstance(); //connect to firebase
        reference = rootNode.getReference("Technician");

        area = (Spinner) findViewById(R.id.area);
        signUp = (Button) findViewById(R.id.button) ;


        signUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String sArea = area.getSelectedItem().toString();

                //User obj from the prev screen
                UserInt user = getIntent().getParcelableExtra("User");

                if(check_area_input(sArea)) {
                    technician_controller.new_tech(user.getPhone(), sArea);

                    Intent intent = new Intent(TechMenDetailsActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
    private boolean check_area_input(String sArea){

        if(sArea.equals("בחר")){
            TextView errorText = (TextView)area.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("אנא בחר אזור");//changes the selected item text to this
            return false;
        }
        return true;
    }
}