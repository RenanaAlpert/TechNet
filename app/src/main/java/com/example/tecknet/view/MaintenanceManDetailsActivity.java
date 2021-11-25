package com.example.tecknet.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tecknet.R;
import com.example.tecknet.model.InstitutionDetails;
import com.example.tecknet.model.InstitutionDetailsInt;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * this class implement the screen to fill Institution details
 * for maintenance man
 */

public class MaintenanceManDetailsActivity extends AppCompatActivity {
    EditText numIns ,nameIns ,sityIns ,addressIns  ,phoneInc;
    Spinner area;
    Button signUp;

    //need to move to controller
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_man_details);

        //todo move this to the controller
        rootNode = FirebaseDatabase.getInstance(); //connect to firebase
        reference = rootNode.getReference("institution");

        numIns = (EditText) findViewById(R.id.institution_num);
        nameIns = (EditText) findViewById(R.id.institution_name);
        sityIns = (EditText) findViewById(R.id.sity);
        addressIns = (EditText) findViewById(R.id.address);
        area = (Spinner) findViewById(R.id.area);
        phoneInc = (EditText) findViewById(R.id.mobile_number);
        signUp = (Button) findViewById(R.id.button) ;


        signUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String sNumIns = numIns.getText().toString();
                String sNameIns = nameIns.getText().toString();
                String sSityIns = sityIns.getText().toString();
                String sAddressIns = addressIns.getText().toString();
                String sArea = area.getSelectedItem().toString();
                String sPhoneInc = phoneInc.getText().toString();

                //InstitutionDetails obj to add to Institution database
                InstitutionDetails instDetails = new InstitutionDetails(sNumIns, sNameIns , sSityIns ,sAddressIns ,
                        sArea,"",sPhoneInc);

                //user obj from the prev screen
                UserHelperClass user = getIntent().getParcelableExtra("user");

                //todo move this to the controller
                reference.child(instDetails.getInstitution_id()).setValue(instDetails);
                reference.child(instDetails.getInstitution_id()).child("contact").setValue(user);

                ///todo move to morias screen
                Intent intent = new Intent(MaintenanceManDetailsActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });


    }
}