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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

                //user obj from the prev screen
                UserHelperClass user = getIntent().getParcelableExtra("user");

                /**
                 * NOT GOOD WEY TO ENTER TECH ON HIS DATABASE!!!
                 */
                //todo move this to the controller
                reference.child(user.getPhone()).setValue(sArea);
                reference.child(user.getPhone()).setValue("mal");

                ///todo move to morias screen
                Intent intent = new Intent(TechMenDetailsActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });


    }
}