package com.example.tecknet.view.report_malfunction_maintenance_man;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

                //extract the report details from the user
                String typeS = type.getText().toString();
                String modelS = model.getText().toString();
                String companyS = company.getText().toString();
                String detailFaultS = detailFault.getText().toString();

                //enter to InstitutionDetails object to insert to firebase
                InstitutionDetails ins = requireActivity().getIntent().getParcelableExtra("institution");
                //call to new_malfunction function from the controller
                com.example.tecknet.model.Controller.new_malfunction(ins.getInstitution_id(), modelS, companyS,
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