package com.example.tecknet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    EditText email ,fName ,lName ,pass;
    Spinner role;
    Button signUp;
    FirebaseAuth fAuth;
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
        signUp = (Button) findViewById(R.id.button) ;

        fAuth = FirebaseAuth.getInstance(); //connect to firebase
        pBar = findViewById(R.id.progressBar);

//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext() , MainActivity.class));
//            finish();
//        } //keep the user connect to app after signup

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailS = email.getText().toString().trim();
                String passwordS = pass.getText().toString().trim();
                if(emailS.isEmpty()){
                    email.setError("enter email!!!");
                    return;
                }
                if(passwordS.isEmpty()){
                    pass.setError("enter password!");
                    return;
                }
                    // if we want to check the pass save
                pBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(emailS , passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){
                             Toast.makeText(SignUpActivity.this , "user created" , Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(getApplicationContext() , MainActivity.class));
                                //MainActivity is temp
                         }
                         else{
                             Toast.makeText(SignUpActivity.this , "ERROR!" + task.getException().getMessage() ,
                                     Toast.LENGTH_SHORT).show();

                         }

                    }
                });

            }
        });
    }
}