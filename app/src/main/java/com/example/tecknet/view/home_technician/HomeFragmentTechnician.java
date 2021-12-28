package com.example.tecknet.view.home_technician;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.R;
import com.example.tecknet.databinding.FragmentHomeTechnicianBinding;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.InstitutionDetails;
import com.example.tecknet.model.MalfunctionDetails;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetails;
import com.example.tecknet.model.ProductDetailsInt;
import com.example.tecknet.model.Technician;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;
import com.example.tecknet.view.home_maintenance_man.HomeViewModelMainMan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;

public class HomeFragmentTechnician extends Fragment {
    private UserViewModel uViewModel;
    private HomeViewModel homeViewModel;
    private HomeViewModel homeViewModelMainMan;

    private FragmentHomeTechnicianBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        //get user of this app
        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user=uViewModel.getItem().getValue();
        UserInt user2 = getActivity().getIntent().getParcelableExtra("User");


        binding = FragmentHomeTechnicianBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("יציאה");
                builder.setMessage("האם אתה בטוח רוצה לצאת?");
                builder.setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finishAffinity();
                    }
                });
                builder.setNegativeButton("השאר",null);
                builder.show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        //Print to the screen hello userName
        TextView textView = binding.textHomeTechnician;
        textView.setText( "שלום "+user2.getFirstName() +" " +user2.getLastName() +"!");

        //This text view is for print to the screen the sum of the open current jobs in my area
        TextView textJobs = binding.countWorkInMyArea;
        Controller.tech_homePage_see_sumJobs(textJobs, user2.getPhone()); // in controller is make the count

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}