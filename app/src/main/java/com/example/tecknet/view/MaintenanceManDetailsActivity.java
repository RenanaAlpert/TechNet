package com.example.tecknet.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tecknet.R;
import com.example.tecknet.model.InstitutionDetails;
import com.example.tecknet.model.User;

/**
 * this class implement the screen to fill Institution details
 * for maintenance man
 */

public class MaintenanceManDetailsActivity extends AppCompatActivity {
    EditText numIns ,nameIns ,cityIns ,addressIns  ,phoneInc;
    Spinner area;
    Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_man_details);


        numIns =  findViewById(R.id.institution_num);
        nameIns = findViewById(R.id.institution_name);
        cityIns =  findViewById(R.id.sity);
        addressIns =  findViewById(R.id.address);
        area =  findViewById(R.id.area);
        phoneInc =  findViewById(R.id.mobile_number);
        signUp = findViewById(R.id.button) ;


        signUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String sNumIns = numIns.getText().toString();
                String sNameIns = nameIns.getText().toString();
                String sCityIns = cityIns.getText().toString();
                String sAddressIns = addressIns.getText().toString();
                String sArea = area.getSelectedItem().toString();
                String sPhoneInc = phoneInc.getText().toString();

                //User obj from the prev screen
                User myUser = getIntent().getParcelableExtra("User");
                InstitutionDetails ins = new InstitutionDetails(sNumIns, sNameIns,sAddressIns,
                        sCityIns, sArea, "", sPhoneInc, myUser.getPhone());

                //todo check sNumIns doesn't already exist
                //call to controller
                com.example.tecknet.model.Controller.set_institution(sNumIns, sNameIns,sAddressIns,
                        sCityIns, sArea, "", sPhoneInc, myUser.getPhone()) ;

                ///todo move to morias screen
                Intent intent = new Intent(MaintenanceManDetailsActivity.this, HomeMaintenanceMan.class);
                intent.putExtra("User" ,myUser);
//                intent.putExtra("institution" , ins);
                //show msg to the screen
                Toast.makeText(MaintenanceManDetailsActivity.this , "SignUp institution success" , Toast.LENGTH_LONG).show();
                
                startActivity(intent);


            }
        });


    }
}