package com.example.tecknet.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tecknet.R;
import com.example.tecknet.controller.ValidInputs;
import com.example.tecknet.controller.MaintenanceController;
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

                if(check_if_entered_details(sNumIns ,sNameIns , sCityIns,sAddressIns,sArea , sPhoneInc )
                    && check_if_valid_details(sNumIns ,sPhoneInc)) {

                    //todo check sNumIns doesn't already exist
                    //call to controller
                    MaintenanceController.set_institution(sNumIns, sNameIns, sAddressIns,
                            sCityIns, sArea, "", sPhoneInc, myUser.getPhone());

                    Intent intent = new Intent(MaintenanceManDetailsActivity.this, MainActivity.class);
                    intent.putExtra("User", myUser);
                    //show msg to the screen
                    Toast.makeText(MaintenanceManDetailsActivity.this, "SignUp institution success", Toast.LENGTH_LONG).show();

                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    /**
     * this function check if the user enter details
     * @param sNumIns
     * @param sNameIns
     * @param sCityIns
     * @param sAddressIns
     * @param sArea
     * @param sPhoneInc
     * @return true if the user fill all the edit text
     *      else false
     */
    private boolean check_if_entered_details(String sNumIns  , String sNameIns , String sCityIns,
                                             String sAddressIns, String sArea , String sPhoneInc ){
        if(sNumIns.isEmpty()){
            numIns.setError("אנא הכנס סמל מוסד (או מספר מוסד)");
            return false;
        }
        if(sNameIns.isEmpty()){
            nameIns.setError("אנא הכנס שם מוסד");
            return false;
        }
        if(sCityIns.isEmpty()){
            cityIns.setError("אנא הכנס עיר מוסד");
            return false;
        }
        if(sAddressIns.isEmpty()){
            addressIns.setError("אנא הכנס כתובת מוסד");
            return false;
        }
        if(sPhoneInc.isEmpty()){
            phoneInc.setError("אנא הכנס מספר טלפון של המוסד");
            return false;
        }
        if(sArea.equals("בחר")){
            TextView errorText = (TextView)area.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("אנא בחר אזור");//changes the selected item text to this
            return false;
        }
        return true;
    }

    /**
     * this function check if the phone number and ins' number is valide
     * @param insNum
     * @param insPhone
     * @return true if phone number & ins' number is valid
     *      else false
     */
    private boolean check_if_valid_details(String insNum , String insPhone){

        if(!ValidInputs.valid_institution_num(insNum)){
            numIns.setError("מספר מוסד לא חוקי!");
            return false;

        }
        if(!ValidInputs.valid_phone(insPhone)
            && !ValidInputs.valid_ins_phone_number(insPhone)){
            phoneInc.setError("מספר טלפון מוסד לא חוקי!");
            return false;

        }
        return true;
    }
}