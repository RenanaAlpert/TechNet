package com.example.tecknet.model;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tecknet.MaintenanceMan;
import com.example.tecknet.view.LoginActivity;
import com.example.tecknet.view.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public abstract class Controller{

    private static DatabaseReference connect_db(String db){
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance(); //connect to firebase
        return rootNode.getReference(db);
    }

    public static void new_user(String first_name, String last_name, String phone, String mail,
                                String password, String role) {
        DatabaseReference r = connect_db("users");
        user us = new user(first_name , last_name ,password , mail , phone, role);
        r.child(phone).setValue(us);
    }

    public static void set_institution(String symbol, String name, String address, String city,
                                       String area, String operation_hours, String phone_number,
                                       String phone_maintenance) {

        DatabaseReference r = connect_db("institution");
        InstitutionDetailsInt ins = new InstitutionDetails(symbol, name, address, city, area, operation_hours, phone_number, phone_maintenance);
        r.child(symbol).setValue(ins);
        MaintenanceManInt mm = new MaintenanceMan(phone_maintenance, symbol);
        r = connect_db("maintenance");
        r.child(phone_maintenance).setValue(symbol);
    }

    private static long get_product_id(String device, String company, String type){
        DatabaseReference r = connect_db("products");
        final long[] pid = new long[1];
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.child("device").equals(device) && ds.child("company").equals(company) && ds.child("type").equals(type)){
                        pid[0] = Long.parseLong(Objects.requireNonNull(ds.getKey()));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG",error.getMessage());
            }
        });
        return pid[0];
//        java.lang.IllegalArgumentException: Can't call equalTo() and startAt() combined
//        error
//        Query q = r.orderByValue().equalTo(device, device).equalTo(company, company).equalTo(type, type);
//       here
//        return Long.parseLong(Objects.requireNonNull(q.getRef().getKey()));
    }

    public static void new_malfunction(String symbol, String device, String company, String type, String explain) {
        long product_id = get_product_id(device, company, type);
        MalfunctionDetailsInt mal = new MalfunctionDetails(product_id, symbol, explain);
        DatabaseReference r = connect_db("mals");
        r.child(String.valueOf(mal.getMal_id())).setValue(mal);
    }

    public static void insert(String institution, long product_id){
        DatabaseReference r = connect_db("institution").child(institution);
    }

    public static boolean login(String phone , String pass){
        DatabaseReference r = connect_db("users");
        final boolean[] flag = new boolean[1];
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(phone).exists()){
                    if(!phone.isEmpty()){
                        UserInt user = snapshot.child(phone).getValue(user.class);
                        if(user.getPass().equals(pass)){
                            flag[0] = true;
                        }
                        else {
                            flag[0] = false;
                        }
                    }
                    else{
                        flag[0] = false;
                    }
                }
                else {
                    flag[0] = false;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG",error.getMessage());
            }
        });
        return flag[0];
    }
}
