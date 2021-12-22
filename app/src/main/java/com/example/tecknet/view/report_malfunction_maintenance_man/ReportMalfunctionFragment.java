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
    Button reportBut;
    Button notInInv;

    Spinner prodSpinner;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        //connect to edit_text user enters
        binding = FragmentReportMalfunctionMaintenanceManBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        detailFault = binding.elsee;
        reportBut = binding.button ;
        prodSpinner = binding.products;

        notInInv =binding.notInInventory;

        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user=uViewModel.getItem().getValue();

        //not working yet
       /* notInInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReportNotInStockFragment fragment = new ReportNotInStockFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.report_malfunction_fragment, fragment).commit();

            }
        });*/
        //show the inventory of this user in spinner
        Controller.what_insNum_show_spinner_products(prodSpinner, user.getPhone(), root);

        //if user select item
        prodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //user click on report button
                reportBut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //extract the report details from the User
                        String detailFaultS = detailFault.getText().toString();
                        //check if the user entered details
                        if(check_if_entered_details(detailFaultS)) {
                            //extract the product the user chose
                            ProductDetails prod = (ProductDetails) parent.getSelectedItem();
                            //add this mal to database
                            Controller.add_malfunction_with_exist_prod(prod, detailFaultS,user.getPhone());

                            clear_edit_text();
                            Toast.makeText(getActivity(), "Report success", Toast.LENGTH_LONG).show();
                        }
                        DatabaseReference r = FirebaseDatabase.getInstance().getReference("Technician");
                        r.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot d: dataSnapshot.getChildren()){
//                                    final Notification ngr =
//                                            (NotificationManager) ReportMalfunctionFragment.this.getSystemService(Context.NOTIFICATION_SERVICE);
//                                    Notification note = new Notification(R.drawable.ic_launcher,)
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });

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

        detailFault.getText().clear();

    }

    private boolean check_if_entered_details(String detailFaultS){

        if(detailFaultS.isEmpty()){
            detailFault.setError("Please enter report reason!!");
            return false;
        }
        return true;

    }

}