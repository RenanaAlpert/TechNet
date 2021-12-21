package com.example.tecknet.view.maintenance_man_malfunctions;

import static com.example.tecknet.model.Controller.add_adapter_malfunction_list;
import static com.example.tecknet.model.Controller.get_malfunction_list;

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1);
        get_malfunction_list(user);
        add_adapter_malfunction_list(adapter);
        list.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

