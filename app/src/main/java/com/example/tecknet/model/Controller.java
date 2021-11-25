package com.example.tecknet.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Controller{

    private static DatabaseReference conect_db(String db){
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance(); //connect to firebase
        DatabaseReference reference = rootNode.getReference(db);
        return reference;
    }

    public static void new_user(String first_name, String last_name, String phone, String mail, String password, String role) {
        DatabaseReference r = conect_db("users");
        user helper = new user(first_name , last_name ,password , mail , phone);
        r.child(phone).setValue(helper);
    }

    public static void set_institution(String symbol, String name, String address, String city, String area, String phone_number, String phone_maintenance) {
        DatabaseReference r = conect_db("institution");

    }

    public static void new_malfunction(String symbol, String device, String company, String type, String date_of_responsibility, String year_of_production, String explain) {

    }
}
