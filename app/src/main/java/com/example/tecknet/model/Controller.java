package com.example.tecknet.model;

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
import com.example.tecknet.view.MaintenanceManDetailsActivity;
import com.example.tecknet.view.TechMenDetailsActivity;
import com.example.tecknet.view.maintenance_man_malfunctions.PEUAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

    public static void new_user_auth_real_db(UserInt user ,View root ){
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        fAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPass()).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Controller.add_new_user_to_Auth(fAuth.getUid() ,user.getPhone());
                            Controller.new_user(user);
                            Toast.makeText(root.getContext() ,"רישום הצליח", Toast.LENGTH_LONG).show();

                            move_to_register_continue(user, root);
                        }
                        else {
                            Toast.makeText(root.getContext() ,"בעיה ברישום!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    /**
     * this function move to the next screen
     */
    private static void move_to_register_continue(UserInt user, View root){
        if(user.getRole().equals("טכנאי")){
            Intent intent = new Intent();
            intent.setClass(root.getContext(), TechMenDetailsActivity.class);
            intent.putExtra("User" , user);
            root.getContext().startActivity(intent);
        }
        else{
            Intent intent = new Intent();
            intent.setClass(root.getContext(), MaintenanceManDetailsActivity.class);
            intent.putExtra("User" , user);
            root.getContext().startActivity(intent);
        }

    }

    /**
     * Add new User.
     *
     */
    public static void new_user(UserInt user) {
        DatabaseReference r = connect_db("users");
        r.child(user.getPhone()).setValue(user);
    }

    /**
     * This controller function is enter to AuthToReal table the new user id - phone
     * we want the access to user db be more essy
     * @param userId
     * @param phoneNum
     */
    public static void add_new_user_to_Auth(String userId , String phoneNum){
        DatabaseReference r = connect_db("AuthToReal");
        r.child(userId).setValue(phoneNum);

    }

    /////////*********/////////////**********//////////**********///////////
    public static void login(View root ,String email ,String password){
        FirebaseAuth fAuth =FirebaseAuth.getInstance();

        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userId = fAuth.getUid();
                    Controller.login_get_user(root,userId);
                    Toast.makeText(root.getContext(), "כניסה הוצלחה", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(root.getContext(), "שם משתמש או סיסמה לא נכונים או שמשתמש לא קיים.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public static void login_get_user(View root , String userId){
        DatabaseReference r = connect_db("AuthToReal");
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phone = (String) dataSnapshot.child(userId).getValue();
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/" + phone);
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        assert user != null;
                        Controller.move_to_home_pages(user , root);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }});
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
    private static void move_to_home_pages(UserInt user, View root){
        if(user.getRole().equals("טכנאי")){
            Intent intent = new Intent();
            intent.setClass(root.getContext(), HomeTechnician.class);
            intent.putExtra("User" , user);
            root.getContext().startActivity(intent);

        }
        else{
            Intent intent = new Intent();
            intent.setClass(root.getContext(), HomeMaintenanceMan.class);
            intent.putExtra("User" , user);
            root.getContext().startActivity(intent);
        }
    }

    /////////*********/////////////**********//////////**********///////////

    public static void get_maintenance_man(UserInt user) {
        if (user != null) {
            if (user.getRole() == "אב בית")//todo connect to string
            {
                DatabaseReference r = connect_db("maintenance");


            }
        }
    }

    //yuval change and superset from the new User
    public static void new_tech(String phone, String area) {
        DatabaseReference r = connect_db("Technician");
        TechnicianInt t = new Technician(phone, area);
        r.child(phone).setValue(t);
    }


    /**
     * Add institution
     *
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
        //todo check symble dosen't already exist
        r.child(symbol).setValue(ins);
        MaintenanceManInt mm = new MaintenanceMan(phone_maintenance, symbol);
        r = connect_db("maintenance");
        r.child(phone_maintenance).setValue(mm);
    }

    /**
     * get user from data base by it's key
     *
     * @param userKey
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

    /**
     * @param adapter
     */
    public static void add_adapter_malfunction_list( ArrayAdapter<String> adapter) {
        adapter.clear();
        for (ProductExplanationUser tmpPeu :peuArrayList ) {
            String full_string = "";
            full_string=tmpPeu.getProd().toString();
//            full_string += "סוג מוצר: " + tmpPeu.getProd().getType() + "\nדגם: " + tmpPeu.getProd().getDevice() + "\n";
//            full_string += "חברה: " + tmpPeu.getProd().getCompany() + "\nפרטי התקלה: " + tmpPeu.getMalfunctionExplanation() + "\n";
            if (tmpPeu.getUser() == null) {
                full_string += "טכנאי: " + "לא הוקצה" + "\n";
            }
            else
            {
                full_string +="שם הטכנאי: " + tmpPeu.getUser().getFirstName() + " " + tmpPeu.getUser().getFirstName() + "\n";
                full_string+="מס' טלפון: " + tmpPeu.getUser().getPhone() + "\n" + "מייל: " + tmpPeu.getUser().getEmail()+"\n";
            }
            adapter.add(full_string);


        }
    }

    /**
     * @param user
     * @return
     */
    public static void get_malfunction_list(UserInt user) {
        //get from data base
        //connect to maintenance man' to get malfunction list
        DatabaseReference r_main_man = FirebaseDatabase.getInstance().getReference("maintenance");
        r_main_man.addValueEventListener(new ValueEventListener() {//listener for maintenance
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotMainMan) {
                DatabaseReference r = FirebaseDatabase.getInstance().getReference("mals");
                r.addValueEventListener(new ValueEventListener() {//listener for mals
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshotMals) {
                        peuArrayList.clear();//empty list
                        peu = new ProductExplanationUser();
                        DataSnapshot dsMainManMalsList = dataSnapshotMainMan.child(user.getPhone());
                        if (dsMainManMalsList.hasChild("malfunctions_list")) {
                            //for every malfunction of this mainMan, get full info from malfunctions.
                            for (DataSnapshot da_mal_id : dsMainManMalsList.child("malfunctions_list").getChildren()) {
                                String mal_id = da_mal_id.getValue(String.class);
                                assert dataSnapshotMals.hasChild(mal_id);
                                MalfunctionDetailsInt mal = dataSnapshotMals.child(mal_id).getValue(MalfunctionDetails.class);
                                assert mal != null;
                                if (mal.isIs_open()) {
                                    String explanation = mal.getExplanation();
                                    ProductDetailsInt prod;

                                    if (mal.getProduct_id() == null) {
                                        prod = dataSnapshotMals.child(mal_id).child("productDetails").getValue(ProductDetails.class);
                                        peu.setProd(prod);

                                    } else { //get product detailes from inventory
                                        String insId = dsMainManMalsList.child("institution").getValue().toString();
                                        DatabaseReference r = connect_db("institution").child(insId).child("inventory");
                                        r.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                DataSnapshot ds = dataSnapshot.child(mal.getProduct_id());
                                                ProductDetailsInt prod=ds.getValue(ProductDetails.class);
                                                assert prod != null;
                                                peu.setProd(prod);

                                            }


                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Log.d("TAG", databaseError.getMessage());
                                            }
                                        });
                                    }

                                    peu.setMalfunctionExplanation(explanation);
                                    if (mal.getTech() == null) {
                                        peu.setUser(null);
                                    } else {
                                        DatabaseReference r = connect_db("users");
                                        r.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot ds) {
                                                UserInt user=ds.child(mal.getTech()).getValue(User.class);
                                                peu.setUser(user);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Log.d("TAG", databaseError.getMessage());
                                            }
                                        });
                                    }

                                    peuArrayList.add(peu);
                                    peu = new ProductExplanationUser();
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("TAG", databaseError.getMessage());
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", databaseError.getMessage());
            }
        });
    }

    public static void add_to_malfunction_list(String mainManKey,String malId)
    {
        DatabaseReference dataRefMainMan = connect_db("maintenance");
        DatabaseReference malfunctionListRef = dataRefMainMan.child(mainManKey).child("malfunctions_list"); // Generate a reference to a new location and add some data using push()
        String key = malfunctionListRef/*.child(malfunctionListRef.getKey())*/.push().getKey();
        Map<String, Object> map = new HashMap<>();
        map.put(key, malId);
        malfunctionListRef.updateChildren(map);
    }

    /**
     * open malfunction report
     *
     * @param symbol
     * @param device
     * @param company
     * @param type
     * @param explain
     */
    // TODO moriya fix
    public static void new_malfunction(String phoneMainMan, String symbol, String device, String company, String type, String explain) {
        MalfunctionDetailsInt mal = new MalfunctionDetails(null, symbol, explain);
        DatabaseReference r = connect_db("mals");

        // Generate a reference to a new location and add some data using push()
        DatabaseReference newMalRef = r.push();
        String malId = newMalRef.getKey(); //get string of the uniq key
        mal.setMal_id(malId);
        newMalRef.setValue(mal); //add this to mal database


        //add to maintenance man malfunction list
       add_to_malfunction_list(phoneMainMan,malId);


        //add product detail to the mal
        ProductDetailsInt pd = new ProductDetails(device, company, type, "", "");
        r.child(malId).child("productDetails").setValue(pd);
    }

    /**
     * extract institution id from data base and call to function to add new malfunction to data base
     *
     * @param userPhone
     * @param model
     * @param company
     * @param type
     * @param detailFault
     */
    public static void add_mal_and_extract_istituId(String userPhone, String model, String company,
                                                    String type, String detailFault) {
        DatabaseReference dataRef = connect_db("maintenance");
        final String[] insSymbol = new String[1];

        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userPhone).exists()) {
                    if (!(userPhone.isEmpty())) {
                        insSymbol[0] = dataSnapshot.child(userPhone).getValue(MaintenanceMan.class).getInstitution();

                        new_malfunction(userPhone, insSymbol[0], model, company, type, detailFault);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public static void add_product_inventory(String phone, ProductDetailsInt pd) {
        DatabaseReference dataRef = connect_db("maintenance");
        final String[] insSymbol = new String[1];
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists()) {
                    //extract institution number
                    insSymbol[0] = dataSnapshot.child(phone).getValue(MaintenanceMan.class).getInstitution();
                    DatabaseReference r = connect_db("institution");
                    DatabaseReference newProdRef = r.child(insSymbol[0]).child("inventory").push(); // Generate a reference to a new location and add some data using push()
                    String prodId = newProdRef.getKey(); //get string of the uniq key
                    pd.setProduct_id(prodId);
                    newProdRef.setValue(pd); //add this to institution.inventory database
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

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

    /**
     * this function find the user institution number and call
     * show_spinner_products function to show the product in spinner on
     * report malfunction fragment
     *
     * @param phone
     * @param root
     */
    public static void what_insNum_show_spinner_products(Spinner productSpinner, String phone, View root) {
        DatabaseReference dataRef = connect_db("maintenance");
        final String[] insSymbol = new String[1];
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists()) {
                    //extract institution number
                    insSymbol[0] = dataSnapshot.child(phone).getValue(MaintenanceMan.class).getInstitution();
                    show_spinner_products(productSpinner, insSymbol[0], root);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    /**
     * this function get the institution number and show in spinner the institution products
     *
     * @param insNumber
     * @param root
     */
    private static void show_spinner_products(final Spinner productSpinner, String insNumber, View root) {
        DatabaseReference fDatabaseRoot = FirebaseDatabase.getInstance().getReference();

        fDatabaseRoot.child("institution").child(insNumber).child("inventory").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // initialize the array
                final List<ProductDetails> products = new ArrayList<ProductDetails>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    ProductDetails p = areaSnapshot.getValue(ProductDetails.class);
                    products.add(p);
                }
                Collections.sort(products);
                products.add(new ProductDetails("אחר" , "", "" ,"",""));
                ArrayAdapter<ProductDetails> productsAdapter = new ArrayAdapter<ProductDetails>(root.getContext(), android.R.layout.simple_spinner_item, products);
                productsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                productSpinner.setAdapter(productsAdapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * this function get the product details and explanation of the problem and user.phone
     * find the institution number.
     * call new_malfunction_with_existProd that enter to database
     *
     * @param prod
     * @param explain
     * @param mainManKey
     */
    public static void add_malfunction_with_exist_prod(ProductDetailsInt prod, String explain, String mainManKey) {
        DatabaseReference dataRef = connect_db("maintenance");
        final String[] insSymbol = new String[1];
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(mainManKey).exists()) {
                    //extract institution number
                    insSymbol[0] = dataSnapshot.child(mainManKey).getValue(MaintenanceMan.class).getInstitution();
                    new_malfunction_with_existProd(mainManKey,prod , explain ,insSymbol[0] );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    /**
     * this function get product , explanation and institution number
     * and enter the malfunction to database.
     * connect to mals table
     * get unique ID to this report
     * set the MalfunctionDetails id to the given id
     * add id to DB
     *
     * @param prod
     * @param explain
     * @param insNumber
     */
    private static void new_malfunction_with_existProd(String mainManKey,ProductDetailsInt prod, String explain, String insNumber) {
        MalfunctionDetailsInt mal = new MalfunctionDetails(prod.getProduct_id(), insNumber, explain);
        DatabaseReference r = connect_db("mals");
        // Generate a reference to a new location and add some data using push()
        DatabaseReference newMalRef = r.push();
        String malId = newMalRef.getKey(); //get string of the uniq key
        mal.setMal_id(malId);
        newMalRef.setValue(mal); //add this to mal database
        //add to maintenance man malfunction list
        add_to_malfunction_list(mainManKey,malId);
    }

    /**
     * This function is call from 'inventory fragment' class to show to the maintenance man
     * his inventory , here he extract his institution id -
     * and call to show_products function to move on the product and show in screen.
     *
     * @param phone
     * @param arrProd
     * @param list
     * @param root
     */
    public static void show_inventory(String phone, ArrayList<ProductDetails> arrProd, ListView list, View root) {
        DatabaseReference dataRef = connect_db("maintenance");
        final String[] insSymbol = new String[1];
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists()) {
                    //extract institution number
                    insSymbol[0] = dataSnapshot.child(phone).getValue(MaintenanceMan.class).getInstitution();
                    show_products(insSymbol[0], arrProd, list, root);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
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

    /**
     * This function go to maintenance man inventory and set ListView to the product
     * list .
     *
     * @param insNum
     * @param arrProd
     * @param list
     * @param root
     */
    private static void show_products(String insNum, ArrayList<ProductDetails> arrProd, ListView list, View root) {
        DatabaseReference r = connect_db("institution").child(insNum).child("inventory");
        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ProductDetails prod = ds.getValue(ProductDetails.class);
                    assert prod != null;
//                    String productStr = create_string_from_product(prod);
                    arrProd.add(prod);

                }
                if (!arrProd.isEmpty()) {
                    Collections.sort(arrProd);
                    ArrayAdapter<ProductDetails> areasAdapter = new ArrayAdapter<ProductDetails>(root.getContext(), android.R.layout.simple_list_item_1, arrProd);
                    list.setAdapter(areasAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", databaseError.getMessage());
            }
        });
    }

    /**
     * This function delete this product from the user inventory DB
     *
     * @param prod
     * @param phone
     */
    public static void delete_product_from_inventory(ProductDetailsInt prod, String phone) {
        DatabaseReference dataRef = connect_db("maintenance");
        final String[] insSymbol = new String[1];
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists()) {
                    //extract institution number
                    insSymbol[0] = dataSnapshot.child(phone).getValue(MaintenanceMan.class).getInstitution();
                    DatabaseReference r = connect_db("institution");
                    r.child(insSymbol[0]).child("inventory")
                            .child(prod.getProduct_id()).removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public static void take_malfunction(String tech, String mal){
        DatabaseReference r = connect_db("Technician");
        r.child(tech + "/my_mals").push().setValue(mal);
        r = connect_db("mals/" + mal);
        r.child("status").setValue("נלקח לטיפול");
        r.child("tech").setValue(tech);
        r.child("is_open").setValue(false);
    }

    public static void set_status_nalfunction(String mal, String s){
        DatabaseReference r = connect_db("mals/" + mal);
        r.child("status").setValue(s);
    }

    /**
     * yuval for update details
     */
    public static void update_user_details(String phone , String first, String last){
        DatabaseReference r = connect_db("users/"+phone);
        if(!first.equals("")&&!first.equals(" ") && !first.equals("\n")){
            r.child("firstName").setValue(first);
        }
        if(!last.equals("")&&!last.equals(" ") && !last.equals("\n")){
            r.child("lastName").setValue(last);
        }
    }

    /**
     * yuval for update details
     */
    public static void update_user_pass(String phone , EditText new1 ){
        DatabaseReference r = connect_db("users/"+phone);
        String new1str  = new1.getText().toString();
        r.child("pass").setValue(new1str);

    }
    /**
     * yuval for update details
     */
    public static void update_institution_adrr(String phone , EditText city , EditText addr , Spinner area){
        String cityS = city.getText().toString();
        String addrS = addr.getText().toString();
        String areaS = area.getSelectedItem().toString();

        DatabaseReference r = connect_db("maintenance");
        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String insNum = dataSnapshot.child(phone).getValue(MaintenanceMan.class).getInstitution();
                DatabaseReference update = connect_db("institution/"+insNum);
                if(!cityS.equals("") && !cityS.equals(" ")&& !cityS.equals("\n")){
                    update.child("city").setValue(cityS);
                }
                if(!addrS.equals("") && !addrS.equals(" ")&& !addrS.equals("\n")){
                    update.child("address").setValue(addrS);
                }
                if(!areaS.equals("בחר")){
                    update.child("area").setValue(areaS);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    /**
     * yuval for update details
     */
    public static void update_technician_area(String phone , Spinner area){
        DatabaseReference r = connect_db("Technician/"+phone );
        String areaStr = area.getSelectedItem().toString();
        r.child("area").setValue(areaStr);

    }
    //////////////////////////////////////////////////////////////////////////////
    public static void tech_homePage_see_sumJobs(TextView textJobs ,String userPhone){
        DatabaseReference r = connect_db("Technician");

        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final int[] countMal = {0};
                Technician tech =dataSnapshot.child(userPhone).getValue(Technician.class);
                assert tech != null;
                String area = tech.getArea();

                Controller.move_on_mals_insId(textJobs ,  area ,countMal);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void move_on_mals_insId(TextView textJobs ,String area , final int[] countMal){
        DatabaseReference mals =connect_db("mals");
        mals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    MalfunctionDetails myMal = ds.getValue(MalfunctionDetails.class);
                    if(myMal.isIs_open()) {

                        String malIns = myMal.getInstitution();
                        Controller.check_mal_area(textJobs, area,countMal , malIns);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void check_mal_area(TextView textJobs ,String area , final int[] countMal ,String malIns){
        DatabaseReference areaMal = connect_db("institution/" + malIns);//FirebaseDatabase.getInstance().getReference("institution/" + malIns);

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
    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////***************************/////////////////////////
    public  static  void  mainMan_homePage_see_sumMyJobs(TextView myMals ,String userPhone){
        DatabaseReference r = connect_db("maintenance/"+userPhone+"/malfunctions_list");

        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final int[] countMal = {0};
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String malId= ds.getValue(String.class);
                    //call anoder function
                    Controller.check_mal_isOpen(myMals ,malId,countMal);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
    private static void check_mal_isOpen(TextView textJobs ,String malId , final int[] countMal ){
        DatabaseReference mal = connect_db("mals/" + malId +"/is_open");//FirebaseDatabase.getInstance().getReference("institution/" + malIns);

        mal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean ins = dataSnapshot.getValue(boolean.class);
                if (ins) {
                    countMal[0]++;
                    textJobs.setText("יש לך " + countMal[0] + " תקלות פתוחות עדיין! ");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public static void loadDataInListview(UserInt user, ArrayList<ProductExplanationUser> peuModalArrayList, ListView  malfunctionsList , int layout, Context context) {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        Controller.connect_db("maintenance").addValueEventListener(new ValueEventListener() {//listener for maintenance
            @Override
            public void onDataChange(@NonNull DataSnapshot dsMainMan) {
                DataSnapshot dsMalList = dsMainMan.child(user.getPhone());
                if (dsMalList.hasChild("malfunctions_list")) {
                    Controller.connect_db("mals").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dsMals) {
                            for (DataSnapshot ds_mal_id : dsMalList.child("malfunctions_list").getChildren()) {
                                String mal_id = ds_mal_id.getValue(String.class);
                                assert dsMals.hasChild(mal_id);
                                MalfunctionDetailsInt mal = dsMals.child(mal_id).getValue(MalfunctionDetails.class);

                                assert mal != null;
                                Controller.connect_db("institution").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dsIns) {
                                        Controller.connect_db("users").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dsUsers) {
                                                String explanation = mal.getExplanation();
                                                ProductDetailsInt prod;
                                                UserInt tech;

                                                if (mal.getProduct_id() == null) {
                                                    prod = dsMals.child(mal_id).child("productDetails").getValue(ProductDetails.class);

                                                } else {
                                                    String insId = dsMalList.child("institution").getValue(String.class);

                                                    DataSnapshot dsProd = dsIns.child(insId).child("inventory").child(mal.getProduct_id());
                                                    prod = dsProd.getValue(ProductDetails.class);

                                                }
                                                if (mal.getTech() == null) {
                                                    tech = null;

                                                } else {

                                                    tech = dsUsers.child(mal.getTech()).getValue(User.class);


                                                }
                                                peuModalArrayList.add(new ProductExplanationUser(prod, explanation, tech));
                                                PEUAdapter peuAdapter = new PEUAdapter(context, layout, peuModalArrayList);
                                                malfunctionsList.setAdapter(peuAdapter);

                                            }


                                            @Override
                                            public void onCancelled(@NonNull DatabaseError
                                                                            databaseError) {

                                            }
                                        });


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError
                                                                    databaseError) {

                                    }


                                });
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }//listener for mals


                    });

//                Toast.makeText(MainManMalfunctionsFragment.this, "Fail to load data..", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    //////////////////////////////***************************/////////////////////////


}