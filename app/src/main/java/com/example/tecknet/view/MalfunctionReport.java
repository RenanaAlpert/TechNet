package com.example.tecknet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tecknet.R;
import com.example.tecknet.model.InstitutionDetails;

/**
 * This class is get details from the view XML
 * and call to the controller and enter the report to database
 *
 */

public class MalfunctionReport extends AppCompatActivity {
    EditText type , model ,company , detailFault;
    Button reportBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malfunction_report);

        type = (EditText) findViewById(R.id.phone_type);
        model = (EditText) findViewById(R.id.model);
        company = (EditText) findViewById(R.id.company);
        detailFault = (EditText) findViewById(R.id.elsee);
        reportBut = (Button) findViewById(R.id.button) ;

        reportBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //extract the report details from the user
                String typeS = type.getText().toString();
                String modelS = model.getText().toString();
                String companyS= company.getText().toString();
                String detailFaultS = detailFault.getText().toString();

                //enter to InstitutionDetails object to insert to firebase
                InstitutionDetails ins = getIntent().getParcelableExtra("institution");
                //call to new_malfunction function from the controller
                com.example.tecknet.model.Controller.new_malfunction(ins.getInstitution_id(),modelS,companyS,
                        typeS,detailFaultS);

                //show msg to the screen
                Toast.makeText(MalfunctionReport.this , "Report success" , Toast.LENGTH_LONG).show();
                //todo move to the needed screen
                startActivity(new Intent(MalfunctionReport.this , MainActivity.class));
            }
        });
    }
}
