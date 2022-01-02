package com.example.tecknet.controller;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tecknet.R;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.InstitutionDetails;
import com.example.tecknet.model.InstitutionDetailsInt;
import com.example.tecknet.model.MaintenanceMan;
import com.example.tecknet.model.MaintenanceManInt;
import com.example.tecknet.model.MalfunctionDetails;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetails;
import com.example.tecknet.model.ProductDetailsInt;
import com.example.tecknet.view.inventory_maintenance_man.InventoryFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class maintanance_controller {


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

        DatabaseReference r = shared_controller.connect_db("institution");
        InstitutionDetailsInt ins = new InstitutionDetails(symbol, name, address, city, area, operation_hours, phone_number, phone_maintenance);
        r.child(symbol).setValue(ins);
        MaintenanceManInt mm = new MaintenanceMan(phone_maintenance, symbol);
        r = shared_controller.connect_db("maintenance");
        r.child(phone_maintenance).setValue(mm);
    }

    //////////////************ START MAIN MAN HOME PAGE ************//////////////

    public static void mainMan_homePage_see_sumMyJobs(TextView myMals, String userPhone) {
        DatabaseReference r = shared_controller.connect_db("maintenance/" + userPhone + "/malfunctions_list");

        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final int[] countMal = {0};
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String malId = ds.getValue(String.class);
                    //call anoder function
                    check_mal_isOpen(myMals, malId, countMal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private static void check_mal_isOpen(TextView textJobs, String malId, final int[] countMal) {
        DatabaseReference mal = shared_controller.connect_db("mals/" + malId + "/is_open");//FirebaseDatabase.getInstance().getReference("institution/" + malIns);

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
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    //////////////************ END MAIN MAN HOME PAGE ************//////////////

    //////////////************ START ADD PRODUCT TO INVENTORY ************//////////////
    public static void add_product_inventory(String phone, ProductDetailsInt pd) {
        DatabaseReference dataRef = shared_controller.connect_db("maintenance");
        final String[] insSymbol = new String[1];
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists()) {
                    //extract institution number
                    insSymbol[0] = dataSnapshot.child(phone).getValue(MaintenanceMan.class).getInstitution();

                    assert insSymbol[0] != null;

                    DatabaseReference r = shared_controller.connect_db("institution");
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
    //////////////************ END ADD PRODUCT TO INVENTORY ************//////////////

    //////////////************ START INVENTORY ************//////////////
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
    public static void show_inventory(String phone, ArrayList<ProductDetails> arrProd, ListView list, View root,
                                      EditText search) {
        DatabaseReference dataRef = shared_controller.connect_db("maintenance");
        final String[] insSymbol = new String[1];
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists()) {
                    //extract institution number
                    insSymbol[0] = dataSnapshot.child(phone).getValue(MaintenanceMan.class).getInstitution();
                    show_products(insSymbol[0], arrProd, list, root , search);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


    }
    //////////////************ END INVENTORY ************//////////////


    /**
     * This function go to maintenance man inventory and set ListView to the product
     * list .
     *
     * @param insNum
     * @param arrProd
     * @param list
     * @param root
     */
    private static void show_products(String insNum, ArrayList<ProductDetails> arrProd, ListView list, View root ,
                                      EditText search) {
        DatabaseReference r = shared_controller.connect_db("institution").child(insNum).child("inventory");
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
                    list.setTextFilterEnabled(true);
                    list.setAdapter(areasAdapter);
                    search.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            areasAdapter.getFilter().filter(s);
                            areasAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

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

    //////////////************ START DELETE PRODUCT FROM INVENTORY ************//////////////

    /**
     * This function delete this product from the user inventory DB
     *
     * @param prod
     * @param phone
     */
    public static void delete_product_from_inventory(ProductDetailsInt prod, String phone) {
        DatabaseReference dataRef = shared_controller.connect_db("maintenance");
        final String[] insSymbol = new String[1];
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists()) {
                    //extract institution number
                    insSymbol[0] = dataSnapshot.child(phone).getValue(MaintenanceMan.class).getInstitution();
                    DatabaseReference r = shared_controller.connect_db("institution");
                    r.child(insSymbol[0]).child("inventory")
                            .child(prod.getProduct_id()).removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    //////////////************ END DELETE PRODUCT FROM INVENTORY ************//////////////

    //////////////************ START SHOW SPINNER PRODUCT TO REPORT ************//////////////


    /**
     * this function find the user institution number and call
     * show_spinner_products function to show the product in spinner on
     * report malfunction fragment
     *
     * @param phone
     * @param root
     */
    public static void what_insNum_show_spinner_products(Spinner productSpinner, String phone, View root) {
        DatabaseReference dataRef = shared_controller.connect_db("maintenance");
        final String[] insSymbol = new String[1];
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists()) {
                    //extract institution number
                    insSymbol[0] = dataSnapshot.child(phone).getValue(MaintenanceMan.class).getInstitution();
                    assert insSymbol[0]!= null;
                    show_spinner_products(productSpinner, insSymbol[0], root);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
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
    //////////////************ END SHOW SPINNER PRODUCT TO REPORT ************//////////////

    //////////////************ START REPORT ON MAL ON PRODUCT NOT IN USER INVENTORY ************//////////////

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
        DatabaseReference dataRef = shared_controller.connect_db("maintenance");
        final String[] insSymbol = new String[1];

        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userPhone).exists()) {
                    if (!(userPhone.isEmpty())) {
                        insSymbol[0] = dataSnapshot.child(userPhone).getValue(MaintenanceMan.class).getInstitution();
                        assert insSymbol[0]!= null;
                        new_malfunction(userPhone, insSymbol[0], model, company, type, detailFault);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
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
        DatabaseReference r = shared_controller.connect_db("mals");

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

    //////////////************ END REPORT ON MAL OF PRODUCT NOT IN USER INVENTORY ************//////////////

    //////////////************ START REPORT ON MAL OF PRODUCT IN USER INVENTORY ************//////////////

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
        DatabaseReference dataRef = shared_controller.connect_db("maintenance");
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
        DatabaseReference r = shared_controller.connect_db("mals");
        // Generate a reference to a new location and add some data using push()
        DatabaseReference newMalRef = r.push();
        String malId = newMalRef.getKey(); //get string of the uniq key
        mal.setMal_id(malId);
        newMalRef.setValue(mal); //add this to mal database
        //add to maintenance man malfunction list
        add_to_malfunction_list(mainManKey,malId);
    }

    //////////////************ END REPORT ON MAL OF PRODUCT IN USER INVENTORY ************//////////////


    public static void add_to_malfunction_list(String mainManKey,String malId)
    {
        DatabaseReference dataRefMainMan = shared_controller.connect_db("maintenance");
        DatabaseReference malfunctionListRef = dataRefMainMan.child(mainManKey).child("malfunctions_list"); // Generate a reference to a new location and add some data using push()
        String key = malfunctionListRef/*.child(malfunctionListRef.getKey())*/.push().getKey();
        Map<String, Object> map = new HashMap<>();
        map.put(key, malId);
        malfunctionListRef.updateChildren(map);
    }


    //////////////************ START UPDATE INSTITUTION ADDRESS ************//////////////

    public static void update_institution_adrr(String phone , EditText city , EditText addr , Spinner area){
        String cityS = city.getText().toString();
        String addrS = addr.getText().toString();
        String areaS = area.getSelectedItem().toString();

        DatabaseReference r = shared_controller.connect_db("maintenance");
        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String insNum = dataSnapshot.child(phone).getValue(MaintenanceMan.class).getInstitution();
                DatabaseReference update = shared_controller.connect_db("institution/"+insNum);
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
    //////////////************ END UPDATE INSTITUTION ADDRESS ************//////////////


}
