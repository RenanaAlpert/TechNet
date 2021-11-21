package com.example.tecknet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email , pass;
    TextView forgetPass;
    Button login;
    FirebaseAuth fAuth;
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        forgetPass = findViewById(R.id.forget_pass);
        login =(Button)findViewById(R.id.button);
        //animation
//        ObjectAnimator obgAn = ObjectAnimator.ofFloat(email , "translationY", 400f , 0f);
//        obgAn.setDuration(3500);
//
//        ObjectAnimator move = ObjectAnimator.ofFloat(email , "alpha", 0f , 1f);
//        move.setDuration(3500);
//        AnimatorSet aniSet = new AnimatorSet();
//        aniSet.play(move).with(obgAn);
//        aniSet.start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailS = email.getText().toString().trim(); //email string
                String passwordS = pass.getText().toString().trim(); // password string
                if(emailS.isEmpty()){
                    email.setError("Enter email!!!");
                    return;
                }
                if(passwordS.isEmpty()){
                    pass.setError("Enter password!");
                    return;
                }
                pBar.setVisibility(View.VISIBLE); //on the login pic

                //enter the details to the database
                fAuth.signInWithEmailAndPassword(emailS,passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if the user detail sava on firebase
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this , "enter" , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext() , MainActivity.class));
                            //MainActivity is temp, we need to change to the next screen
                        }

                        else {
                            Toast.makeText(LoginActivity.this , "ERROR!" + task.getException().getMessage() ,
                                    Toast.LENGTH_SHORT).show();
                            //to do view gonw
                        }
                    }
                });

            }
        });
    }

}