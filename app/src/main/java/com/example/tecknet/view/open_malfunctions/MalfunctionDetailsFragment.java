package com.example.tecknet.view.open_malfunctions;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tecknet.R;
import com.example.tecknet.databinding.FragmentOpenMalfunctionsBinding;


public class MalfunctionDetailsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String institution_name;
    //private String area = "area";
    private String address;
    private String device;
    private String company;
    private String type;
    private String explain;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.institution_name = getArguments().getString("name");
            this.address = getArguments().getString("address");
            this.device = getArguments().getString("device");
            this.company = getArguments().getString("company");
            this.type = getArguments().getString("type");
            this.explain = getArguments().getString("explain");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        binding = FregmentMalfunctionDetails.inflate(inflater, container, false);
//        View root = binding.getRoot();
        View root = inflater.inflate(R.layout.fragment_malfunction_details, container, false);

        TextView school_name = root.findViewById(R.id.txtSchool);
        TextView school_address = root.findViewById(R.id.txtAddress);
//        TextView product_device = root.findViewById(R.id.txtDevice);
        TextView product_company = root.findViewById(R.id.txtCompany);
        TextView product_type = root.findViewById(R.id.txtType);
        TextView mal_explain = root.findViewById(R.id.txtExplain);

        school_name.setText(institution_name);
        school_address.setText(address);
//        product_device.setText(device);
        product_company.setText(company);
        product_type.setText(type);
        mal_explain.setText(explain);

        return root;
    }


}