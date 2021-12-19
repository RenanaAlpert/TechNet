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
import com.example.tecknet.model.malfunctionView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
public class OpenMalfunctionFragment extends Fragment {
//    private OpenMalfunctionViewModel openMalfunctionViewModel;
    private FragmentOpenMalfunctionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        openMalfunctionViewModel =
//                new ViewModelProvider(this).get(OpenMalfunctionViewModel.class);
        binding = FragmentOpenMalfunctionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1);
        ArrayList<malfunctionView> arrMals = new ArrayList<>();
        ListView list = (ListView) root.findViewById(R.id.listview);

        DatabaseReference r = FirebaseDatabase.getInstance().getReference("mals");
        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1);
//                ListView list = (ListView) root.findViewById(R.id.listview);
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    MalfunctionDetailsInt mal = ds.getValue(MalfunctionDetails.class);
                    assert mal != null;
                    if(mal.isIs_open()){
//                        r.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot das) {
//                                String device;
//                                if(mal.getProduct_id()==null){
////                                device = ds.child("productDetails").child("device").getValue(String.class);
//                                    device = das.child(ds.getKey() + "/productDetails/device").getValue(String.class);
//                                }
//                                else{
//                                    device = das.child("technet-f44dd-default-rtdb/institution/" + mal.getInstitution() + "/inventory/" + mal.getProduct_id() + "/device").getValue(String.class);
//                                }
                                r.getDatabase().getReference("institution/" + mal.getInstitution()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot das) {
                                        String device;
                                        if(mal.getProduct_id()==null){
                                            device = ds.child("/productDetails/device").getValue(String.class);
                                        }
                                        else{
                                            device = das.child("inventory/" + mal.getProduct_id() + "/device").getValue(String.class);
                                        }
                                        if (device == null){
                                            device = "לא קיים מוצר במערכת";
                                        }
                                        String area = das.child("area").getValue(String.class);
                                        arrMals.add(new malfunctionView(area, device));
//                                        adapter.add("אזור: " + area +"\nמוצר תקול: " + device);
//                                        list.setAdapter(adapter);
                                        OpenMalfunctionsAdapter oma = new OpenMalfunctionsAdapter(root.getContext(), R.layout.fragment_open_malfunctions_row, arrMals);
                                        list.setAdapter(oma);
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                    }
                }
//                OpenMalfunctionsAdapter oma = new OpenMalfunctionsAdapter(root.getContext(), R.layout.fragment_open_malfunctions_row, arrMals);
//                list.setAdapter(oma);
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