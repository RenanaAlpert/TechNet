package com.example.tecknet.view.waiting_for_payment;

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
import com.example.tecknet.controller.maintenance_controller;
import com.example.tecknet.databinding.FragmentMaintenanceWaitingForPaymentBinding;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.ProductExplanationUser;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;

import java.util.ArrayList;


public class WaitingForPayment extends Fragment {

    private FragmentMaintenanceWaitingForPaymentBinding binding;
    private UserViewModel uViewModel;
    private UserInt user;
    private View root;

    // creating a variable for our list view,
    // arraylist and firebase Firestore.
    ListView malfunctionsList;
    ArrayList<ProductExplanationUser> peuModalArrayList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMaintenanceWaitingForPaymentBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        //get user of this app

        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        user = uViewModel.getItem().getValue();

        //get from data base
        // below line is use to initialize our variables
        malfunctionsList = root.findViewById(R.id.listview_waiting_for_payment);
        peuModalArrayList = new ArrayList<>();

        // here we are calling a method
        // to load data in our list view.
        maintenance_controller.loadDataInListview(user,peuModalArrayList,malfunctionsList, R.layout.singal_main_man_waiting_for_payment,this.getContext());

        return root;
    }


}

