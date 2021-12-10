package com.example.tecknet.view.report_malfunction_maintenance_man;

import android.graphics.Color;
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

import com.example.tecknet.databinding.FragmentReportMalfunctionMaintenanceManBinding;
import com.example.tecknet.model.InstitutionDetails;



public class ReportMalfunctionFragment extends Fragment {

    private FragmentReportMalfunctionMaintenanceManBinding binding;
    EditText type , model ,company , detailFault;
    Button reportBut;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


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

                //extract the report details from the User
                String typeS = type.getText().toString();
                String modelS = model.getText().toString();
                String companyS = company.getText().toString();
                String detailFaultS = detailFault.getText().toString();

                //enter to ins_id to insert to firebase
                String ins_id="444";
                ins_id+= getArguments().getString("institution_symbol");

                if(check_if_entered_details(typeS ,modelS,companyS, detailFaultS)) {
                    //call to new_malfunction function from the controller
                    com.example.tecknet.model.Controller.new_malfunction(ins_id, modelS, companyS,
                            typeS, detailFaultS);

                    clear_edit_text(); //clear from edit text

                    //show msg to the screen
                    Toast.makeText(getActivity(), "Report success", Toast.LENGTH_LONG).show();
                    //todo move to the needed screen
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