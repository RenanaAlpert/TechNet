package com.example.tecknet.view.report_malfunction_maintenance_man;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.databinding.FragmentReportMalfunctionMaintenanceManBinding;
import com.example.tecknet.model.InstitutionDetails;

import com.example.tecknet.model.MaintenanceMan;
import com.example.tecknet.model.MaintenanceManInt;
import com.example.tecknet.model.User;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ReportMalfunctionFragment extends Fragment {

    private UserViewModel uViewModel;
    private FragmentReportMalfunctionMaintenanceManBinding binding;
    EditText type , model ,company , detailFault;
    Button reportBut;

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

                //get user of this app
                uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
                UserInt user=uViewModel.getItem().getValue();

                //extract the report details from the User
                String typeS = type.getText().toString();
                String modelS = model.getText().toString();
                String companyS = company.getText().toString();
                String detailFaultS = detailFault.getText().toString();

                if(check_if_entered_details(typeS ,modelS,companyS, detailFaultS)) {
                    //call to this function from the controller how found the
                    //institution id and call new_malfunction how add this mal to DB
                    com.example.tecknet.model.Controller.add_mal_and_extricate_istituId(user.getPhone()
                        ,modelS,companyS,typeS ,detailFaultS);

                    clear_edit_text(); //clear from edit text

                    //show msg to the screen
                    Toast.makeText(getActivity(), "Report success", Toast.LENGTH_LONG).show();
                }
            }
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
        type.getText().clear();
        model.getText().clear();
        company.getText().clear();
        detailFault.getText().clear();

    }

    private boolean check_if_entered_details(String typeS ,String modelS, String companyS, String detailFaultS){
        if(typeS.isEmpty()){
            type.setError("Please enter the device!!");
            return false;
        }
        if(modelS.isEmpty()){
            model.setError("Please enter th model!!");
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