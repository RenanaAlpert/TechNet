package com.example.tecknet.view.maintenance_man_malfunctions;

//import static com.example.tecknet.model.Controller.add_adapter_malfunction_list;
//import static com.example.tecknet.model.Controller.get_malfunction_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.R;
import com.example.tecknet.databinding.FragmentManaintanceManMalfunctionsBinding;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.MalfunctionDetails;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetails;
import com.example.tecknet.model.ProductDetailsInt;
import com.example.tecknet.model.ProductExplanationUser;
import com.example.tecknet.model.User;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainManMalfunctionsFragment extends Fragment {

    private FragmentManaintanceManMalfunctionsBinding binding;
    private UserViewModel uViewModel;
    private UserInt user;
    private View root;

    // creating a variable for our list view,
    // arraylist and firebase Firestore.
    ListView malfunctionsList;
    ArrayList<ProductExplanationUser> peuModalArrayList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentManaintanceManMalfunctionsBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        //get user of this app

        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        user = uViewModel.getItem().getValue();

        //get from data base
        // below line is use to initialize our variables
        malfunctionsList = root.findViewById(R.id.listviewMainManMalfunctions);
        peuModalArrayList = new ArrayList<>();

        // here we are calling a method
        // to load data in our list view.
        loadDatainListview();

        return root;
    }

    private void loadDatainListview() {


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
                                                PEUAdapter peuAdapter = new PEUAdapter(root.getContext(), R.layout.single_malfunction_maintenace_display, peuModalArrayList);
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
}

