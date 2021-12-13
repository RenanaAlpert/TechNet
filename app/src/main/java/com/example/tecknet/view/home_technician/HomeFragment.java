package com.example.tecknet.view.home_technician;

import android.os.Bundle;
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
import com.example.tecknet.databinding.FragmentHomeTechnicianBinding;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetailsInt;

import java.util.Collection;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeTechnicianBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeTechnicianBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
//        Collection<MalfunctionDetailsInt> open_mals = Controller.open_malfunction();
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1);
//        for (MalfunctionDetailsInt mal: open_mals){
//            ProductDetailsInt product = Controller.get_product(mal.getProduct_id());
//            adapter.add(product.getDevice());
//            adapter.add(product.getCompany());
//        }
//        ListView list = (ListView) root.findViewById(R.id.listview);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}