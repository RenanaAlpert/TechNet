package com.example.tecknet.view.home_maintenance_man;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.databinding.FragmentHomeMaintenanceManBinding;
import com.example.tecknet.model.Controller;
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
        //get user of this app
        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user = uViewModel.getItem().getValue();
        UserInt user2 = getActivity().getIntent().getParcelableExtra("User");


        TextView textView = binding.helloMainManText;
        textView.setText( "שלום "+user2.getFirstName() +" "+ user2.getLastName() +"!");

        //Text view to see sentence how say how mach from my mal is open in home page
        TextView myMals = binding.countMyOpenMal;
        Controller.mainMan_homePage_see_sumMyJobs(myMals, user2.getPhone());


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}