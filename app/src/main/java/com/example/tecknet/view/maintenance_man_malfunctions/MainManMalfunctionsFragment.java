package com.example.tecknet.view.maintenance_man_malfunctions;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.R;
import com.example.tecknet.databinding.FragmentManaintanceManMalfunctionsBinding;
import com.example.tecknet.databinding.FragmentOpenMalfunctionsBinding;
import com.example.tecknet.model.MalfunctionDetails;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetails;
import com.example.tecknet.model.Technician;
import com.example.tecknet.model.User;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainManMalfunctionsFragment extends Fragment {

    private FragmentManaintanceManMalfunctionsBinding binding;
    private UserViewModel uViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentManaintanceManMalfunctionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ListView list = root.findViewById(R.id.listviewMainManMalfunctions);
        //get user of this app
        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user = uViewModel.getItem().getValue();
        //get from data base
        DatabaseReference r_main_man = FirebaseDatabase.getInstance().getReference("maintenance");
        r_main_man.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotMainMan) {

                DatabaseReference r = FirebaseDatabase.getInstance().getReference("mals");
                r.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshotMals) {
                        final String[] full_adapter_string = {null};
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1);
                        DataSnapshot dsMainManMalsList = dataSnapshotMainMan.child(user.getPhone());
                        if (dsMainManMalsList.hasChild("malfunctions_list")) {
                            for (DataSnapshot da_mal_id : dsMainManMalsList.child("malfunctions_list").getChildren()) {
                                String mal_id = da_mal_id.getValue(String.class);
                                assert dataSnapshotMals.hasChild(mal_id);
                                MalfunctionDetailsInt mal = dataSnapshotMals.child(mal_id).getValue(MalfunctionDetails.class);
                                assert mal != null;
                                if (mal.isIs_open()) {
                                    String explanation = mal.getExplanation();

                                    ProductDetails prod;

                                    if (mal.getProduct_id() == null) {
                                        prod = dataSnapshotMals.child(mal_id).child("productDetails").getValue(ProductDetails.class);

                                    } else {
                                        //todo fix!!
                                        prod = dataSnapshotMals.child("inventory/" + mal.getProduct_id()).getValue(ProductDetails.class);
                                    }
//                                            if (device == null) {
//                                                device = "לא קיים מוצר במערכת";
//                                            }

                                    full_adapter_string[0] +="סוג מוצר: " + prod.getType() + "\nדגם: " + prod.getDevice() + "\n";
                                    full_adapter_string[0] +="חברה: " + prod.getCompany() + "\nפרטי התקלה: " + explanation+"\n";

                                    final UserInt[] tech_u = new UserInt[1];
                                    if (mal.getTech() == null) {
                                        full_adapter_string[0] +="טכנאי: " + "לא הוקצה"+"\n";
                                    } else {
                                        //get tech

                                        r.getDatabase().getReference("users").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot ds_tech) {
                                                tech_u[0] = ds_tech.child(mal.getTech()).getValue(User.class);
                                                full_adapter_string[0] +="שם הטכנאי: " + tech_u[0].getFirstName() + " " + tech_u[0].getFirstName() + "\n";
                                                full_adapter_string[0]+="מס' טלפון: " + tech_u[0].getPhone() + "\n" + "מייל: " + tech_u[0].getEmail()+"\n";
//                                                adapter.add("שם הטכנאי: " + tech_u[0].getFirstName() + " " + tech_u[0].getFirstName() + "\n");
//                                                adapter.add("מס' טלפון: " + tech_u[0].getPhone() + "\n" + "מייל: " + tech_u[0].getEmail());

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Log.d("TAG", databaseError.getMessage());
                                            }
                                        });

                                    }
                                    adapter.add(full_adapter_string[0]);
                                    full_adapter_string[0]=null;
                                }

                            }
                        }
                        list.setAdapter(adapter);
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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

