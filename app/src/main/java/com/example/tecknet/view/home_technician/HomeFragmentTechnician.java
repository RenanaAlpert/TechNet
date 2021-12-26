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
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;
import com.example.tecknet.view.home_maintenance_man.HomeViewModelMainMan;

import java.util.Collection;

public class HomeFragmentTechnician extends Fragment {
    private UserViewModel uViewModel;
    private HomeViewModel homeViewModel;
    private HomeViewModel homeViewModelMainMan;

    private FragmentHomeTechnicianBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
//        //get user of this app
        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user=uViewModel.getItem().getValue();
        UserInt user2 = getActivity().getIntent().getParcelableExtra("User");


        binding = FragmentHomeTechnicianBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textView = binding.textHomeTechnician;
        textView.setText( "שלום "+user2.getFirstName() +" " +user2.getLastName() +"!");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}