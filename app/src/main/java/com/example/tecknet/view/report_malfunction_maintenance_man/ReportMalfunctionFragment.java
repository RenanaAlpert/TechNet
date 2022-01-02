package com.example.tecknet.view.report_malfunction_maintenance_man;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.R;
import com.example.tecknet.controller.maintanance_controller;
import com.example.tecknet.databinding.FragmentReportMalfunctionMaintenanceManBinding;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.InstitutionDetails;

import com.example.tecknet.model.MaintenanceMan;
import com.example.tecknet.model.MaintenanceManInt;
import com.example.tecknet.model.ProductDetails;
import com.example.tecknet.model.Technician;
import com.example.tecknet.model.User;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.LoginActivity;
import com.example.tecknet.view.MainActivity;
import com.example.tecknet.view.UserViewModel;

import com.example.tecknet.view.update_profile_main_man.UpdateProfileMainManFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ReportMalfunctionFragment extends Fragment {

    private UserViewModel uViewModel;
    private FragmentReportMalfunctionMaintenanceManBinding binding;
    EditText detailFault;
    EditText device, type  ,company ;
    Button reportBut;

    Spinner prodSpinner;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        //connect to edit_text user enters
        binding = FragmentReportMalfunctionMaintenanceManBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        device = binding.device; //phone/computer /....
        type = binding.model;
        company = binding.company;

        detailFault = binding.elsee;
        reportBut = binding.button ;
        prodSpinner = binding.products;


        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user=uViewModel.getItem().getValue();


        //show the inventory of this user in spinner
        maintanance_controller.what_insNum_show_spinner_products(prodSpinner, user.getPhone(), root);

        //if user select item
        prodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //extract the report details from the User

                ProductDetails prod = (ProductDetails) parent.getSelectedItem();
                if (prod.getDevice().equals("אחר")) {
                    device.setVisibility(View.VISIBLE);
                    type.setVisibility(View.VISIBLE);
                    company.setVisibility(View.VISIBLE);

                    reportBut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            String deviceS = device.getText().toString();
                            String typeS = type.getText().toString(); //*//
                            String companyS = company.getText().toString();
                            String detailFaultS = detailFault.getText().toString();

                            if (check_if_entered_details(deviceS, typeS, companyS, detailFaultS)) {
                                //call to this function from the controller how found the
                                //institution id and call new_malfunction how add this mal to DB
                                maintanance_controller.add_mal_and_extract_istituId(user.getPhone(), deviceS, companyS, typeS, detailFaultS);

                                clear_edit_text(); //clear from edit text
                                Toast.makeText(getActivity(), "Report success", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
                else {
                    device.setVisibility(View.GONE);
                    type.setVisibility(View.GONE);
                    company.setVisibility(View.GONE);
                    reportBut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String detailFaultS = detailFault.getText().toString();

                            //check if the user entered details
                            if (check_if_entered_details(detailFaultS)) {
                                //add this mal to database
                                maintanance_controller.add_malfunction_with_exist_prod(prod, detailFaultS, user.getPhone());

                                clear_edit_text();
                                Toast.makeText(getActivity(), "Report success", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


    return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Private function how clear the text from the edit text view
     */
    private void clear_edit_text(){
        device.getText().clear();
        type.getText().clear();
        company.getText().clear();
        detailFault.getText().clear();

    }

    private boolean check_if_entered_details(String detailFaultS){

        if(detailFaultS.isEmpty()){
            detailFault.setError("Please enter report reason!!");
            return false;
        }
        return true;

    }


    private boolean check_if_entered_details(String modelS , String typeS , String companyS, String detailFaultS){

        if(modelS.isEmpty()){
            device.setError("Please enter th device!!");
            return false;
        }
        if(typeS.isEmpty()){
            type.setError("Please enter the type!!");
            return false;
        }
        if(companyS.isEmpty()){
            company.setError("Please enter the company!!");
            return false;
        }
        if(detailFaultS.isEmpty()){
            detailFault.setError("Please enter report reason!!");
            return false;
        }
        return true;

    }


}