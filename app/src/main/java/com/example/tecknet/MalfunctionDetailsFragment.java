package com.example.tecknet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MalfunctionDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MalfunctionDetailsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String institution_name = "name";
    private static final String area = "area";
    private static final String address = "address";
    private static final String device = "device";
    private static final String company = "company";
    private static final String type = "type";
    private static final String explain = "explain";

    public MalfunctionDetailsFragment() {
        // Required empty public constructor
    }

    public static MalfunctionDetailsFragment newInstance(
            String institution_name, String area, String address,
            String device, String company, String type,
            String explain) {
        MalfunctionDetailsFragment fragment = new MalfunctionDetailsFragment();
        Bundle args = new Bundle();
        args.putString(institution_name, institution_name);
        args.putString(area, area);
        args.putString(address, address);
        args.putString(device, device);
        args.putString(company, company);
        args.putString(type, type);
        args.putString(explain, explain);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_malfunction_details, container, false);
    }
}