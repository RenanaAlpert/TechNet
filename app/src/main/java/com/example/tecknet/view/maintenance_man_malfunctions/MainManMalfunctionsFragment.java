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
        Controller.loadDataInListview(user,peuModalArrayList,malfunctionsList,R.layout.single_malfunction_maintenace_display,this.getContext());

        return root;
    }


}

