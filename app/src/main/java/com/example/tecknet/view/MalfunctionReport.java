package com.example.tecknet.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tecknet.R;

/**
 * This class is get details from the view XML
 * and call to the controller and enter the report to database
 *
 */

public class MalfunctionReport extends AppCompatActivity {
    EditText type , model , detailFault;
    Button reportBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malfunction_report);

        type = (EditText) findViewById(R.id.phone_type);
        model = (EditText) findViewById(R.id.model);
        detailFault = (EditText) findViewById(R.id.elsee);

        reportBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String typeS = type.getText().toString();
                String modelS = type.getText().toString();
                String detailFaultS = type.getText().toString();

                //call renna function to add to database
                
            }
        });
    }
}
