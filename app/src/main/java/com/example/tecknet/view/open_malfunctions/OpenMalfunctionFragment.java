package com.example.tecknet.view.open_malfunctions;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.R;
import com.example.tecknet.databinding.FragmentOpenMalfunctionsBinding;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.MalfunctionDetails;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetails;
import com.example.tecknet.model.ProductDetailsInt;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class OpenMalfunctionFragment extends Fragment {

    private OpenMalfunctionViewModel openMalfunctionViewModel;
    private FragmentOpenMalfunctionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        openMalfunctionViewModel =
                new ViewModelProvider(this).get(OpenMalfunctionViewModel.class);

        binding = FragmentOpenMalfunctionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        DatabaseReference r = FirebaseDatabase.getInstance().getReference("mals");
        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<MalfunctionDetailsInt> open_mals = new LinkedList<>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    MalfunctionDetailsInt mal = ds.getValue(MalfunctionDetails.class);
                    assert mal != null;
                    if(mal.isIs_open()){
                        open_mals.add(mal);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1);
                ListView list = (ListView) root.findViewById(R.id.listview);
                DatabaseReference rf = FirebaseDatabase.getInstance().getReference("products");
                for (MalfunctionDetailsInt mal: open_mals) {
                    rf.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ProductDetailsInt p = dataSnapshot.child(String.valueOf(mal.getProduct_id())).getValue(ProductDetails.class);
                            assert p != null;
                            adapter.add("institution: " + mal.getInstitution() + "\tdevice: " + p.getDevice() + "\tcompany: " + p.getCompany());
//                            adapter.add(p.getCompany());
                            list.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG",databaseError.getMessage());
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