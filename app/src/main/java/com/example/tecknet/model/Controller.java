package com.example.tecknet.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public abstract class Controller{

    /**
     * connect to the DB.
     * @param db
     * @return
     */
    private static DatabaseReference connect_db(String db){
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance(); //connect to firebase
        return rootNode.getReference(db);
    }

    /**
     * Add new user.
     * @param first_name
     * @param last_name
     * @param phone
     * @param mail
     * @param password
     * @param role
     */
    public static void new_user(String first_name, String last_name, String phone, String mail,
                                String password, String role) {
        DatabaseReference r = connect_db("users");
        UserInt us = new user(first_name , last_name ,password , mail , phone, role);
        r.child(phone).setValue(us);
        if(us.getRole().equals("Technician") || us.getRole().equals("טכנאי")){
            TechnicianInt t = new Technician(phone, region.Sharon);
            r = connect_db("Technician");
            r.child(phone).setValue(t);
        }
    }

    /**
     * Add institution
     * @param symbol
     * @param name
     * @param address
     * @param city
     * @param area
     * @param operation_hours
     * @param phone_number
     * @param phone_maintenance
     */
    public static void set_institution(String symbol, String name, String address, String city,
                                       String area, String operation_hours, String phone_number,
                                       String phone_maintenance) {

        DatabaseReference r = connect_db("institution");
        InstitutionDetailsInt ins = new InstitutionDetails(symbol, name, address, city, area, operation_hours, phone_number, phone_maintenance);
        r.child(symbol).setValue(ins);
        MaintenanceManInt mm = new MaintenanceMan(phone_maintenance, symbol);
        r = connect_db("maintenance");
        r.child(phone_maintenance).setValue(mm);
    }

    /**
     * Return the product_id
     * @param device
     * @param company
     * @param type
     * @return
     */
    private static long get_product_id(String device, String company, String type){
        DatabaseReference r = connect_db("products");
        final long[] pid = new long[1];
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ProductDetailsInt product = ds.child(Objects.requireNonNull(ds.getKey())).getValue(ProductDetails.class);
                    assert product != null;
                    if(product.getDevice().equals(device) && product.getCompany().equals(company) && product.getType().equals(type)){
                        pid[0] = product.getProduct_id();
                        break;
                    }
                    /*if(ds.child("device").equals(device) && ds.child("company").equals(company) && ds.child("type").equals(type)){
                        pid[0] = Long.parseLong(Objects.requireNonNull(ds.getKey()));
                        break;
                    }*/
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

    /**
     * open malfunction report
     * @param symbol
     * @param device
     * @param company
     * @param type
     * @param explain
     */
    public static void new_malfunction(String symbol, String device, String company, String type, String explain) {
        long product_id = get_product_id(device, company, type);
        MalfunctionDetailsInt mal = new MalfunctionDetails(product_id, symbol, explain);
        DatabaseReference r = connect_db("mals");
        r.child(String.valueOf(mal.getMal_id())).setValue(mal);
    }

    /**
     * connect to the system
     * @param phone
     * @param pass
     * @return
     */
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
