package com.example.tecknet.view.my_malfunctions;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.R;
import com.example.tecknet.controller.technician_controller;
import com.example.tecknet.controller.shared_controller;
import com.example.tecknet.databinding.FragmentMyMalfunctionsBinding;
import com.example.tecknet.model.InstitutionDetails;
import com.example.tecknet.model.InstitutionDetailsInt;
import com.example.tecknet.model.MalfunctionDetails;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetails;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;
import com.example.tecknet.model.MalfunctionView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyMalfunctionsFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private FragmentMyMalfunctionsBinding binding;
    private View root;
    public ListView list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyMalfunctionsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        UserViewModel userView = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user = userView.getItem().getValue();

        list = (ListView) root.findViewById(R.id.mylistview);

        assert user != null;
        technician_controller.load_my_malfunctions_list(user, root.getContext(), list);

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}