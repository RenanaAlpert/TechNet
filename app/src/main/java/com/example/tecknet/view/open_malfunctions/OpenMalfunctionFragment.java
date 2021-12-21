package com.example.tecknet.view.open_malfunctions;

import android.app.Activity;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tecknet.R;
import com.example.tecknet.databinding.FragmentOpenMalfunctionsBinding;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.InstitutionDetails;
import com.example.tecknet.model.InstitutionDetailsInt;
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

public class OpenMalfunctionFragment extends Fragment implements OpenMalfunctionsAdapter.onClickButton {
    //    private OpenMalfunctionViewModel openMalfunctionViewModel;
    private FragmentOpenMalfunctionsBinding binding;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        openMalfunctionViewModel =
//                new ViewModelProvider(this).get(OpenMalfunctionViewModel.class);
        binding = FragmentOpenMalfunctionsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        ArrayList<malfunctionView> arrMals = new ArrayList<>();
        ListView list = (ListView) root.findViewById(R.id.listview);

        OpenMalfunctionsAdapter.onClickButton listener = this;

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
                                arrMals.add(new malfunctionView(mal, p, ins));
                                OpenMalfunctionsAdapter oma = new OpenMalfunctionsAdapter(root.getContext(), R.layout.fragment_open_malfunctions_row, arrMals, listener);
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

    @Override
    public void passfragment(MalfunctionDetailsInt mal, ProductDetailsInt product, InstitutionDetailsInt ins) {
        Bundle bundle = new Bundle();
        bundle.putString("name", ins.getName());
        bundle.putString("area", ins.getArea());
        bundle.putString("address", ins.getAddress() + " " + ins.getCity());
        bundle.putString("device", product.getDevice());
        bundle.putString("company", product.getCompany());
        bundle.putString("type", product.getType());
        bundle.putString("explain", mal.getExplanation());

        Fragment details = MalfunctionDetailsFragment.newInstance();
        details.setArguments(bundle);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.malfunction_detailes_container, details).commit();
    }
}