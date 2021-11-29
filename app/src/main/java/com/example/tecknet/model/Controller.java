package com.example.tecknet.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tecknet.MaintenanceMan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Controller{

    private static DatabaseReference connect_db(String db){
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance(); //connect to firebase
        return rootNode.getReference(db);
    }

    public static void new_user(String first_name, String last_name, String phone, String mail, String password, String role) {
        DatabaseReference r = connect_db("users");
        user us = new user(first_name , last_name ,password , mail , phone, role);
        r.child(phone).setValue(us);
    }

    public static void set_institution(String symbol, String name, String address, String city, region area, String operation_hours, String phone_number, String phone_maintenance) {
        DatabaseReference r = connect_db("institution");
        InstitutionDetailsInt ins = new InstitutionDetails(symbol, name, address, city, area, operation_hours, phone_number, phone_maintenance);
        r.child(symbol).setValue(ins);
        MaintenanceManInt mm = new MaintenanceMan(phone_maintenance, symbol);
        r = connect_db("maintenance");
        r.child(phone_maintenance).setValue(symbol);
    }

    public static void new_malfunction(String symbol, String device, String company, String type, String explain) {
        DatabaseReference r = connect_db("products");
        Query q = r.orderByValue().equalTo(device, device).equalTo(company, company).equalTo(type, type);
        long product_id = Long.parseLong(Objects.requireNonNull(q.getRef().getKey()));
        MalfunctionDetailsInt mal = new MalfunctionDetails(product_id, symbol, explain);
        r = connect_db("mals");
        r.child(String.valueOf(mal.getMal_id())).setValue(mal);
    }
}
