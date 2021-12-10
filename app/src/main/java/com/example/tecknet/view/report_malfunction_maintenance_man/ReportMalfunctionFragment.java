package com.example.tecknet.view.report_malfunction_maintenance_man;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.databinding.FragmentReportMalfunctionMaintenanceManBinding;
import com.example.tecknet.model.InstitutionDetails;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;
import com.example.tecknet.view.home_maintenance_man.HomeFragmentArgs;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ReportMalfunctionFragment extends Fragment {
    private String insSymbol;

    private UserViewModel uViewModel;
    private FirebaseDatabase rootNode;//todo move
    private DatabaseReference dataRef;
    private FragmentReportMalfunctionMaintenanceManBinding binding;
    private EditText type , model ,company , detailFault;
    private Button reportBut;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        //connect to edit_text user enters
        binding = FragmentReportMalfunctionMaintenanceManBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        type = binding.phoneType;
        model = binding.model;
        company = binding.company;
        detailFault = binding.elsee;
        reportBut = binding.button ;

        reportBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //for connection to data base todo move
                rootNode = FirebaseDatabase.getInstance();
                dataRef = rootNode.getReference("institution");

                //get user of this app
                uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
                UserInt user=uViewModel.getItem().getValue();
                assert user != null;
                System.out.println("name: "+user.getFirstName());

                //get from database the institution number / symbol
                dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getPhone()).exists()) {
                            if (!(user.getPhone().isEmpty())) {
                                insSymbol = dataSnapshot.child(user.getPhone()).getValue(InstitutionDetails.class).getInstitution_id();
                                System.out.println("symbol: "+insSymbol);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });


                //extract the report details from the User
                String typeS = type.getText().toString();
                String modelS = model.getText().toString();
                String companyS = company.getText().toString();
                String detailFaultS = detailFault.getText().toString();



                //call to new_malfunction function from the controller
                com.example.tecknet.model.Controller.new_malfunction(insSymbol, modelS, companyS,
                        typeS, detailFaultS);

                //show msg to the screen
                Toast.makeText(getActivity(), "Report success", Toast.LENGTH_LONG).show();
                //todo move to the needed screen
//                startActivity(new Intent(MalfunctionReport.this , MainActivity.class))
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