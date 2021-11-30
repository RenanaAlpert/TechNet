package com.example.tecknet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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

                String typeS = type.getText().toString();
                String modelS = model.getText().toString();
                String companyS= company.getText().toString();
                String detailFaultS = detailFault.getText().toString();

                InstitutionDetails ins = getIntent().getParcelableExtra("institution");
                com.example.tecknet.model.Controller.new_malfunction(ins.getInstitution_id(),modelS,companyS,
                        typeS,detailFaultS);

                startActivity(new Intent(MalfunctionReport.this , MainActivity.class));

                //call renna function to add to database
//                new_malfunction(String symbol -> need to extract
//                , String device,OK
//                String company-> need to add to report screen
//                , String type,OK
//                String explain OK)

            }
        });
    }
}
