package com.example.tecknet.controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class shared_controller {



    protected static DatabaseReference connect_db(String db) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance(); //connect to firebase
        return rootNode.getReference(db);
    }
}
