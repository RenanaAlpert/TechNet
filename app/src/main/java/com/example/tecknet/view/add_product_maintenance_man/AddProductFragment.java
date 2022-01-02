package com.example.tecknet.view.add_product_maintenance_man;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.controller.maintanance_controller;
import com.example.tecknet.databinding.FragmentAddProductMaintenanceManBinding;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.ProductDetails;
import com.example.tecknet.model.ProductDetailsInt;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;

import java.util.Calendar;

public class AddProductFragment extends Fragment {

    private UserViewModel uViewModel;
    private FragmentAddProductMaintenanceManBinding binding;
    private EditText device, type  ,company   , yearOfProduction ,dateOfResponsibility;
    private Button addProdBut;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddProductMaintenanceManBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //get user of this app
        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user=uViewModel.getItem().getValue();

        device = binding.device; // PHONE/ computer ....
        type = binding.type;
        company = binding.company;
        yearOfProduction = binding.yearOfProduction;
        dateOfResponsibility = binding.dateOfResponsibility;
        addProdBut = binding.button ;

        //show date type in screen like - DD/MM/YYYY
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dateOfResponsibility.setText(current);
                    dateOfResponsibility.setSelection(sel < current.length() ? sel : current.length());
                }

            }

            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
        };
        dateOfResponsibility.addTextChangedListener(tw);

        //click on the add button
        addProdBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //extract the product details from the User
                String deviceS = device.getText().toString();
                String typeS = type.getText().toString();
                String companyS = company.getText().toString();
                String yearOfProductionS = yearOfProduction.getText().toString();
                String dateOfResponsibilityS = dateOfResponsibility.getText().toString();
                //create product obj
                ProductDetailsInt pd =new ProductDetails(deviceS, companyS, typeS, yearOfProductionS,dateOfResponsibilityS);
                //add the product details to DB
                maintanance_controller.add_product_inventory(user.getPhone() , pd);
                //clear the edit text
                clear_product_editext();
                Toast.makeText(root.getContext(), "הוספת מוצר עבר בהצלחה" , Toast.LENGTH_LONG).show();

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void clear_product_editext(){
        type.getText().clear();
        company.getText().clear();
        device.getText().clear();
        yearOfProduction.getText().clear();
        dateOfResponsibility.getText().clear();

    }
}