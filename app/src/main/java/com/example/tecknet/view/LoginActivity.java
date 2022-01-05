package com.example.tecknet.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tecknet.controller.ValidInputs;
import com.example.tecknet.controller.shared_controller;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.example.tecknet.R;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity /**implements GoogleApiClient.OnConnectionFailedListener**/ {
    EditText phone , pass ,email;
    TextView forgetPass;
    Button login;
    ProgressBar pBar;
    /**DatabaseReference dbRef;**/
//    SignInButton loginGoogle;
//    private GoogleApiClient googleAPIclient;
//    private static final int SIGN_IN =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        forgetPass = findViewById(R.id.forget_pass);
        login =(Button)findViewById(R.id.button);
        pBar = findViewById(R.id.progressBarLogin);

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail().build();
//        googleAPIclient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API , gso).build();
//        loginGoogle = findViewById(R.id.google_signin);
//        loginGoogle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleAPIclient);
//                startActivityForResult(intent, SIGN_IN);
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailS = email.getText().toString(); //phone string
                String passwordS = pass.getText().toString(); // password string
                if (if_enter_details(emailS, passwordS) && is_valid_detaild(emailS)) {
                    pBar.setVisibility(View.VISIBLE);
                    shared_controller.login(v , emailS ,passwordS);



                    clear_login_editext();

                }
            }
        });
    }

    /**
     * Private function how clear the text from the edit text view
     */
    private void clear_login_editext(){
        email.getText().clear();
        pass.getText().clear();

    }

    /**
     * this function check if the phone number is valide
     * @param mail
     * @return true if phone number is valid
     *      else false
     */
    private boolean is_valid_detaild(String mail ){

        if(!ValidInputs.valid_email(mail)){
            email.setError("כתובת מייל לא חוקית!");
            return false;
        }
        return true;
    }

    /**
     * this function check if the user enter login details
     * @param mailsS
     * @param passwordS
     * @return true if the user enter hus details
     *      else false
     */
    private boolean if_enter_details(String mailsS, String passwordS){
        if(mailsS.isEmpty()){
            email.setError("אנא הכנס כתובת מייל");
            return false ;
        }
        if(passwordS.isEmpty()){
            pass.setError("אנא הכנס סיסמה");
            return false;
        }
        return true;
    }

//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
//        super.onActivityResult(requestCode,resultCode,data);
//        if(requestCode == SIGN_IN){
//            GoogleSignInResult res = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            if(res.isSuccess()){
//
//                startActivity(new Intent(getApplicationContext(), HomeMaintenanceMan.class));
//                finish();
//            }
//            else{
//                Toast.makeText(LoginActivity.this, "כניסה נכשלה", Toast.LENGTH_LONG).show();
//
//            }
//        }
//    }
}