package com.example.tecknet.model;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private static DatabaseReference connect_db(String db) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance(); //connect to firebase
        return rootNode.getReference(db);
    }

    /**
     * Add new User.
     *
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
        UserInt us = new User(first_name, last_name, password, mail, role, phone);
        r.child(phone).setValue(us);
    }

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
        ProductDetailsInt pd = new ProductDetails(type, company, device, "", "");
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
        DatabaseReference r = connect_db("Technician/" + tech);
        r.push().setValue(mal);
        r = connect_db("mals/" + mal + "/tech");
        r.setValue(tech);
    }

}