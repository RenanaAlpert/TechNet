package com.example.tecknet.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.example.tecknet.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;

import com.example.tecknet.model.*;

public class LoginActivity extends AppCompatActivity {
    EditText phone , pass;
    TextView forgetPass;
    Button login;
    ProgressBar pBar;
    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = (EditText)findViewById(R.id.phone);
        pass = (EditText) findViewById(R.id.pass);
        forgetPass = findViewById(R.id.forget_pass);
        login =(Button)findViewById(R.id.button);
        pBar = findViewById(R.id.progressBarLogin);
        dbRef = FirebaseDatabase.getInstance().getReference("users");
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
                String phoneS = phone.getText().toString(); //phone string
                String passwordS = pass.getText().toString(); // password string
                if(phoneS.isEmpty()){
                    phone.setError("Enter phone number!");
                    return;
                }
                if(passwordS.isEmpty()){
                    pass.setError("Enter password!");
                    return;
                }
                pBar.setVisibility(View.VISIBLE); //on the login pic
                login(phoneS,passwordS);



            }
        });
    }
    public void login(String phone , String pass){
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(phone).exists()){
                    if(!phone.isEmpty()){
                        user user = snapshot.child(phone).getValue(user.class);
                        if(user.getPass().equals(pass)){
                            Toast.makeText(LoginActivity.this , "Login success" , Toast.LENGTH_LONG).show();


                            if(user.getRole().equals("אב בית")){
                                Intent intent = new Intent(getApplicationContext() , HomeMaintenanceMan.class);
                                intent.putExtra("user" , user);
                                startActivity(intent);
                            }
                            else {
                                startActivity(new Intent(getApplicationContext() , HomeTechnician.class));
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this , "Password incorrect!" , Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(LoginActivity.this , "User is not register" , Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this , "User is not register" , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG",error.getMessage());
            }
        });
    }

}