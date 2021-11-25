package com.example.tecknet.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tecknet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
    EditText email ,fName ,lName ,pass ,phone;
    Spinner role;
    Button signUp;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ProgressBar pBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText) findViewById(R.id.email);
        fName = (EditText) findViewById(R.id.first_name);
        lName = (EditText) findViewById(R.id.last_name);
        pass = (EditText) findViewById(R.id.pass);
        role = (Spinner) findViewById(R.id.role);
        phone = (EditText) findViewById(R.id.phone);
        signUp = (Button) findViewById(R.id.button) ;

        pBar = findViewById(R.id.progressBar);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Controller c = new Controller();
                pBar = findViewById(R.id.progressBar);

                String fNames = fName.getText().toString();
                String lNames = lName.getText().toString();
                String emailS = email.getText().toString();
                String roles = role.getSelectedItem().toString();
                String passwordS = pass.getText().toString();
                String phoneS = phone.getText().toString();

                boolean ans = check_if_entered_details(fNames,lNames ,emailS,roles,passwordS,phoneS);
                pBar.setVisibility(View.VISIBLE);//loading

                if(ans) {
//                    c.check_if_user_exist(phoneS);
                    c.add_to_database(fNames, lNames, emailS,roles,passwordS, phoneS);
                    signUp();

                }
//                else if(c.check_if_user_exist(phoneS)){
//                    Toast.makeText(SignUpActivity.this , "User is already exist!" , Toast.LENGTH_LONG).show();
//
//                }


            }
        });
    }

    /**
     * this function is private for check if the user enter his details
     * @param fNames
     * @param lNames
     * @param emailS
     * @param passwordS
     * @param phoneS
     * @return true if the user enter all the needed details
     *         false if some detail is missing
     */
    private boolean check_if_entered_details(String fNames ,String lNames ,String emailS,
                                         String roleS,String passwordS ,String phoneS ){

        if(fNames.isEmpty()){
            fName.setError("Please enter your first name");
            return false;
        }
        if(lNames.isEmpty()){
            lName.setError("Please enter your last name");
            return false;
        }
        if(emailS.isEmpty()){
            email.setError("Please enter a email");
            return false;
        }
        if(roleS.equals("בחר")){
            TextView errorText = (TextView)role.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("You need to choose your role!");//changes the selected item text to this
            return false;
        }
        if(passwordS.isEmpty()){
            pass.setError("Please enter a password");
            return false;
        }
        if(phoneS.isEmpty()){
            phone.setError("Please enter a phone number");
            return false;
        }
        return true;


    }

    /**
     * this function move to the next screen
     */
    private void signUp() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }
}