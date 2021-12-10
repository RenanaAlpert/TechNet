package com.example.tecknet.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tecknet.R;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.Controller;

import java.util.Collection;

public class malfunctionDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malfunction_details);

        Collection<MalfunctionDetailsInt> mals = Controller.open_malfunction();
    }
}