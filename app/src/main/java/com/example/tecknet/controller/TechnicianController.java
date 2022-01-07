package com.example.tecknet.controller;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tecknet.R;
import com.example.tecknet.model.InstitutionDetails;
import com.example.tecknet.model.InstitutionDetailsInt;
import com.example.tecknet.model.MalfunctionDetails;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetails;
import com.example.tecknet.model.Technician;
import com.example.tecknet.model.TechnicianInt;
import com.example.tecknet.model.User;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.model.MalfunctionView;
import com.example.tecknet.view.my_malfunctions.MyMalfunctionsAdapter;
import com.example.tecknet.view.open_malfunctions.OpenMalfunctionsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public abstract class TechnicianController {

    public static void new_tech(String phone, String area) {
        DatabaseReference r = SharedController.connect_db("Technician");
        TechnicianInt t = new Technician(phone, area);
        r.child(phone).setValue(t);
    }


    //////////////************ START TECH HOME PAGE ************//////////////

    public static void tech_homePage_see_sumJobs(TextView textJobs, String userPhone) {
        DatabaseReference r = SharedController.connect_db("Technician");

        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final int[] countMal = {0};
                Technician tech = dataSnapshot.child(userPhone).getValue(Technician.class);
                assert tech != null;
                String area = tech.getArea();

                move_on_mals_insId(textJobs, area, countMal);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static void move_on_mals_insId(TextView textJobs, String area, final int[] countMal) {
        DatabaseReference mals = SharedController.connect_db("mals");
        mals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    MalfunctionDetails myMal = ds.getValue(MalfunctionDetails.class);
                    if (myMal.isIs_open()) {

                        String malIns = myMal.getInstitution();
                        check_mal_area(textJobs, area, countMal, malIns);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static void check_mal_area(TextView textJobs, String area, final int[] countMal, String malIns) {
        DatabaseReference areaMal = SharedController.connect_db("institution/" + malIns);//FirebaseDatabase.getInstance().getReference("institution/" + malIns);

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
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public static void set_payment_nalfunction(String mal, double pay) {
        DatabaseReference r = SharedController.connect_db("mals/" + mal);
        r.child("payment").setValue(pay);
    }

    public static void set_status_malfunction(String mal, String status) {
        DatabaseReference r = SharedController.connect_db("mals/" + mal);
        r.child("status").setValue(status);
    }

    public static void take_malfunction(String tech, String mal) {
        DatabaseReference r = SharedController.connect_db("Technician");
        r.child(tech + "/my_mals").push().setValue(mal);
        r = SharedController.connect_db("mals/" + mal);
        r.child("status").setValue("נלקח לטיפול");
        r.child("tech").setValue(tech);
        r.child("is_open").setValue(false);
    }
    //////////////************ END TECH HOME PAGE ************//////////////

    public static void update_technician_area(String phone, Spinner area) {
        DatabaseReference r = SharedController.connect_db("Technician/" + phone);
        String areaStr = area.getSelectedItem().toString();
        r.child("area").setValue(areaStr);

    }

    public static void load_open_malfunctions_list(UserInt user, Context root, ListView list) {
        DatabaseReference r = SharedController.connect_db("mals");
        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MalfunctionView> arrMals = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    MalfunctionDetailsInt mal = ds.getValue(MalfunctionDetails.class);
                    assert mal != null;
                    if (mal.isIs_open()) {
                        r.getDatabase().getReference("institution/" + mal.getInstitution()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot das) {
                                InstitutionDetailsInt ins = das.getValue(InstitutionDetails.class);
                                ProductDetails p;
                                if (mal.getProduct_id() == null) {
                                    p = ds.child("productDetails").getValue(ProductDetails.class);
                                } else {
                                    p = das.child("inventory/" + mal.getProduct_id()).getValue(ProductDetails.class);
                                }
                                arrMals.add(new MalfunctionView(mal, p, ins, user));
                                OpenMalfunctionsAdapter oma = new OpenMalfunctionsAdapter(root, R.layout.fragment_open_malfunctions_row, arrMals);
                                list.setAdapter(oma);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", databaseError.getMessage());
            }
        });
    }

    public static void load_my_malfunctions_list(UserInt user, Context root, ListView list) {
        DatabaseReference r = SharedController.connect_db("Technician");
        assert user != null;
        r.child(user.getPhone() + "/my_mals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                ArrayList<MalfunctionView> arrMals = new ArrayList<>();
                for (DataSnapshot dataSnapshot : dataSnapshot1.getChildren()) {
                    arrMals.clear();
                    String mal_id = dataSnapshot.getValue(String.class);
                    r.getDatabase().getReference("mals").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot ds) {
                            arrMals.clear();
                            assert mal_id != null;
                            MalfunctionDetailsInt mal = ds.child(mal_id).getValue(MalfunctionDetails.class);
                            assert mal != null;
                            if(!mal.getStatus().equals("שולם")) {
                                arrMals.clear();
                                r.getDatabase().getReference("institution/" + mal.getInstitution()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot das) {
                                        System.out.println("what are you din hereee3");
                                        arrMals.clear();
                                        InstitutionDetailsInt ins = das.getValue(InstitutionDetails.class);
                                        ProductDetails p;
                                        if (mal.getProduct_id() == null) {
                                            p = ds.child(mal_id + "/productDetails").getValue(ProductDetails.class);
                                        } else {
                                            p = das.child("inventory/" + mal.getProduct_id()).getValue(ProductDetails.class);
                                        }
                                        arrMals.clear();
                                        assert ins != null;
//                                        assert p != null;
                                        System.out.println("is nukk? " + p);
                                        if (p != null) {
                                            arrMals.clear();
                                            r.getDatabase().getReference("users").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    UserInt maintenance = dataSnapshot.child(ins.getContact()).getValue(User.class);
                                                    arrMals.add(new MalfunctionView(mal, p, ins, maintenance));
                                                    System.out.println("hereeeeeeee + arrMals.size :" + arrMals.size());
                                                    MyMalfunctionsAdapter oma = new MyMalfunctionsAdapter(root, R.layout.fragment_my_malfunctions_row, arrMals);
                                                    list.setAdapter(oma);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                        arrMals.clear();////

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("TAG", databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}