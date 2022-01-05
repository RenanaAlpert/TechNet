package com.example.tecknet.model;

import static android.content.ContentValues.TAG;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tecknet.view.HomeMaintenanceMan;
import com.example.tecknet.view.HomeTechnician;
import com.example.tecknet.view.LoginActivity;
import com.example.tecknet.view.MaintenanceManDetailsActivity;
import com.example.tecknet.view.SignUpActivity;
import com.example.tecknet.view.TechMenDetailsActivity;
import com.example.tecknet.view.maintenance_man_malfunctions.PEUAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Controller {

    //created inorder to be used whenever needs to pass information inside anonymous functions
    static ArrayList<ProductExplanationUser> peuArrayList = new ArrayList<>();
    static String myString;
    static ProductExplanationUser peu;


    /**
     * connect to the DB.
     *
     * @param db
     * @return
     */
    public static DatabaseReference connect_db(String db) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance(); //connect to firebase
        return rootNode.getReference(db);
    }
    public static String techString(UserInt user) {
        String techStr = "";
        if (user == null) {
            techStr +="לא הוקצה" + "\n";
        } else {
            techStr += "שם הטכנאי: " + user.getFirstName() + " " + user.getFirstName() + "\n";
            techStr += "מס' טלפון: " + user.getPhone() + "\n" + "מייל: " + user.getEmail() + "\n";
        }
        return techStr;
    }

    /////
    //sign up moved to shared controller
    /////

    ///
    // login move to shared controller
    ///
    ///
    ///

    public static void get_maintenance_man(UserInt user) {
        if (user != null) {
            if (user.getRole() == "אב בית")//todo connect to string
            {
                DatabaseReference r = connect_db("maintenance");


            }
        }
    }

    /////
    // create new tech moved to controller
    /////


    //////
    /// set institution moved to maintenance man controller
    //////

    /**
     * get user from data base by it's key
     *
//     * @param userKey
     * @return
     */
//    public static int getUser(String userKey,UserInt user) {
//        DatabaseReference r = connect_db("users");
//        r.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot ds) {
//                user=ds.child(userKey).getValue(User.class);
////                full_adapter_string[0] += "שם הטכנאי: " + tech_u[0].getFirstName() + " " + tech_u[0].getFirstName() + "\n";
////                full_adapter_string[0] += "מס' טלפון: " + tech_u[0].getPhone() + "\n" + "מייל: " + tech_u[0].getEmail() + "\n";
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d("TAG", databaseError.getMessage());
//            }
//        });
//        return 0;
////        return user.get(0);
//    }



    /// ann new malfunction to main man controller

    ////////////////////////
    //////add_mal_and_extract_istituId IN MAIN MAIN CONTROLLER
    ////////////////
    //////
    //add_product_inventory moved to main man controller
    ////

    public static List<MalfunctionDetailsInt> open_malfunction() {
        List<MalfunctionDetailsInt> malsCol = new LinkedList<>();
        DatabaseReference r = connect_db("mals");
        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    MalfunctionDetailsInt mal = ds.getValue(MalfunctionDetails.class);
                    assert mal != null;
                    if (mal.isIs_open()) {
                        malsCol.add(mal);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", databaseError.getMessage());
            }
        });
        assert !malsCol.isEmpty();
        return malsCol;
    }

    public static Collection<MalfunctionDetailsInt> open_malfunction(String area) {
        Collection<MalfunctionDetailsInt> allMals = open_malfunction();
        Collection<MalfunctionDetailsInt> malsInTheArea = new LinkedList<>();
        for (MalfunctionDetailsInt m : allMals) {
            String ins = m.getInstitution();
            DatabaseReference r = connect_db("institution").child(ins);
            r.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String a = (String) dataSnapshot.child("area").getValue();
                    assert a != null;
                    if (a.equals(area)) {
                        malsInTheArea.add(m);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("TAG", databaseError.getMessage());
                }
            });
        }
        return malsInTheArea;
    }

    public static ProductDetailsInt get_product(long id) {
        DatabaseReference r = connect_db("products");
        final ProductDetailsInt[] p = new ProductDetailsInt[1];
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                p[0] = dataSnapshot.child(String.valueOf(id)).getValue(ProductDetails.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return p[0];
    }

    ////////
    ///    what_insNum_show_spinner_products moved to main man controller
    ///////////


    ////add_malfunction_with_exist_prod in main man controller

   /////show inventory moved to main man controller



//
//    /**
//     * This function go to maintenance man inventory and get a specific product list .
//     *
//     * @param insNum
//     * @param prodId
//     */
//    private static int  get_product_from_inventory(String insNum, String prodId) {
////        ArrayList<ProductDetailsInt> prodArr = new ArrayList<>();
//        DatabaseReference r = connect_db("institution").child(insNum).child("inventory");
//        r.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                DataSnapshot ds = dataSnapshot.child(prodId);
//                ProductDetailsInt prod=ds.getValue(ProductDetails.class);
//                assert prod != null;
//                peu.setProd(prod);
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d("TAG", databaseError.getMessage());
//            }
//        });
//        return 1;
//    }


    ///
    ///delet product inventory moved to main man controller
    ///





    // TODO: 30/12/2021  delete after change tech update details

    public static void update_user_details(String phone , String first, String last){
        DatabaseReference r = connect_db("users/"+phone);
        if(!first.equals("")&&!first.equals(" ") && !first.equals("\n")){
            r.child("firstName").setValue(first);
        }
        if(!last.equals("")&&!last.equals(" ") && !last.equals("\n")){
            r.child("lastName").setValue(last);
        }
    }

    // change pass moved to share controller

    ///////////update ins address in main man controller
    /**
     * yuval for update details
     */

    //update tech area moved to tech controller


    /////////////
    //tech_homePage_see_sumJobs MOVED TO TECH CONTROLLER
    //////////
    //////
    /// mainMan_homePage_see_sumMyJobs moved to main man controller
    ///////

    //////
    /// mainMan_malfanctions moved to main man controller
    ///////
    //////////////////////////////***************************/////////////////////////

    ////change_user_pass in shared controller

}