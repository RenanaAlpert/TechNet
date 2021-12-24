

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
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.databinding.FragmentReportNotInStockBinding;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;

public class ReportNotInStockFragment extends Fragment {
     private UserViewModel uViewModel;
     private FragmentReportNotInStockBinding binding;
     EditText type , model ,company , detailFault;
     Button reportBut;

     public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {


         //connect to edit_text user enters
         binding = FragmentReportNotInStockBinding.inflate(inflater, container, false);
         View root = binding.getRoot();

         model = binding.model; //phone/computer /....
         type = binding.phoneType;
         company = binding.company;
         detailFault = binding.elsee;
         reportBut = binding.button ;
            //get user of this app

         uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
         UserInt user=uViewModel.getItem().getValue();


         reportBut.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 //extract the report details from the User
                 String modelS = model.getText().toString();
                 String typeS = type.getText().toString(); //*//
                 String companyS = company.getText().toString();
                 String detailFaultS = detailFault.getText().toString();


                if(check_if_entered_details(modelS , typeS,companyS, detailFaultS)) {
                    //call to this function from the controller how found the
                    //institution id and call new_malfunction how add this mal to DB
                    Controller.add_mal_and_extract_istituId(user.getPhone(),modelS,companyS,typeS ,detailFaultS);

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
            model.getText().clear();
            type.getText().clear();
            company.getText().clear();
            detailFault.getText().clear();

        }

        private boolean check_if_entered_details(String modelS , String typeS , String companyS, String detailFaultS){

            if(modelS.isEmpty()){
                model.setError("Please enter th model!!");
                return false;
            }
            if(typeS.isEmpty()){
                type.setError("Please enter the device!!");
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