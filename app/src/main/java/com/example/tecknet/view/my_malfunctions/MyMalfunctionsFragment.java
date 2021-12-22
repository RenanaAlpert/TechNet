package com.example.tecknet.view.my_malfunctions;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.R;
import com.example.tecknet.databinding.FragmentMyMalfunctionsBinding;
import com.example.tecknet.model.InstitutionDetails;
import com.example.tecknet.model.InstitutionDetailsInt;
import com.example.tecknet.model.MalfunctionDetails;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetails;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.model.malfunctionView;
import com.example.tecknet.view.UserViewModel;
import com.example.tecknet.view.open_malfunctions.OpenMalfunctionsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyMalfunctionsFragment extends Fragment {

//    private MyMalfunctionsViewModel myMalfunctionsViewModel;
    private FragmentMyMalfunctionsBinding binding;
    private View root;
    private ListView list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        myMalfunctionsViewModel =
//                new ViewModelProvider(this).get(MyMalfunctionsViewModel.class);

        binding = FragmentMyMalfunctionsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        UserViewModel userView = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user = userView.getItem().getValue();

        ArrayList<malfunctionView> arrMals = new ArrayList<>();
        list = (ListView) root.findViewById(R.id.listview);


        DatabaseReference r = FirebaseDatabase.getInstance().getReference("mals");
        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                                arrMals.add(new malfunctionView(mal, p, ins, user));
                                OpenMalfunctionsAdapter oma = new OpenMalfunctionsAdapter(root.getContext(), R.layout.fragment_open_malfunctions_row, arrMals);
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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}