package com.example.tecknet.view.home_maintenance_man;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.controller.MaintenanceController;
import com.example.tecknet.databinding.FragmentHomeMaintenanceManBinding;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;

public class HomeFragmentMainMan extends Fragment {
    private UserViewModel uViewModel;
    private HomeViewModelMainMan homeViewModelMainMan;
    private FragmentHomeMaintenanceManBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModelMainMan =
                new ViewModelProvider(this).get(HomeViewModelMainMan.class);

        binding = FragmentHomeMaintenanceManBinding.inflate(inflater, container, false);
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
        //get user of this app
        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user = uViewModel.getItem().getValue();
        UserInt user2 = getActivity().getIntent().getParcelableExtra("User");


        TextView textView = binding.helloMainManText;
        textView.setText( "שלום "+user2.getFirstName() +" "+ user2.getLastName());

        //Text view to see sentence how say how mach from my mal is open in home page
        TextView myMals = binding.countMyOpenMal;
        MaintenanceController.mainMan_homePage_see_sumMyJobs(myMals, user2.getPhone());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}