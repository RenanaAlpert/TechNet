package com.example.tecknet.controller;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tecknet.model.Controller;
import com.example.tecknet.model.InstitutionDetails;
import com.example.tecknet.model.MalfunctionDetails;
import com.example.tecknet.model.Technician;
import com.example.tecknet.model.TechnicianInt;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public abstract class technician_controller {

    public static void new_tech(String phone, String area) {
        DatabaseReference r = shared_controller.connect_db("Technician");
        TechnicianInt t = new Technician(phone, area);
        r.child(phone).setValue(t);
    }


    //////////////************ START TECH HOME PAGE ************//////////////

    public static void tech_homePage_see_sumJobs(TextView textJobs , String userPhone){
        DatabaseReference r = shared_controller.connect_db("Technician");

        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final int[] countMal = {0};
                Technician tech =dataSnapshot.child(userPhone).getValue(Technician.class);
                assert tech != null;
                String area = tech.getArea();

                move_on_mals_insId(textJobs ,  area ,countMal);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
    public static void move_on_mals_insId(TextView textJobs ,String area , final int[] countMal){
        DatabaseReference mals = shared_controller.connect_db("mals");
        mals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    MalfunctionDetails myMal = ds.getValue(MalfunctionDetails.class);
                    if(myMal.isIs_open()) {

                        String malIns = myMal.getInstitution();
                        check_mal_area(textJobs, area,countMal , malIns);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
    public static void check_mal_area(TextView textJobs ,String area , final int[] countMal ,String malIns){
        DatabaseReference areaMal = shared_controller.connect_db("institution/" + malIns);//FirebaseDatabase.getInstance().getReference("institution/" + malIns);

        areaMal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                InstitutionDetails ins = dataSnapshot.getValue(InstitutionDetails.class);
                assert ins != null;
                String malArea = ins.getArea();
                if (malArea.equals(area)) {
                    countMal[0]++;
                    textJobs.setText("יש באזורך כעת " + countMal[0] + " עבודות פנויות! ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public static void set_status_nalfunction(String mal, String s){
        DatabaseReference r = shared_controller.connect_db("mals/" + mal);
        r.child("status").setValue(s);
    }

    public static void set_payment_nalfunction(String mal, double pay){
        DatabaseReference r = shared_controller.connect_db("mals/" + mal);
        r.child("payment").setValue(pay);
    }

    public static void take_malfunction(String tech, String mal){
        DatabaseReference r = shared_controller.connect_db("Technician");
        r.child(tech + "/my_mals").push().setValue(mal);
        r = shared_controller.connect_db("mals/" + mal);
        r.child("status").setValue("נלקח לטיפול");
        r.child("tech").setValue(tech);
        r.child("is_open").setValue(false);
    }
    //////////////************ END TECH HOME PAGE ************//////////////

}
