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
        UserInt us = new user(first_name , last_name ,password , mail , phone, role);
        r.child(phone).setValue(us);
    }

    public static void set_institution(String symbol, String name, String address, String city, region area, String operation_hours, String phone_number, String phone_maintenance) {
        DatabaseReference r = conect_db("institution");
        InstitutionDetailsInt ins = new InstitutionDetails(symbol, name, address, city, area, operation_hours, phone_number, phone_maintenance);
        r.child(symbol).setValue(ins);
    }

    public static void new_malfunction(String symbol, String device, String company, String type, String explain) {

    }
}
