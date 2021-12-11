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
        //get user of this app
        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user=uViewModel.getItem().getValue();
        System.out.println("my activity: "+requireActivity());
        System.out.println(user==null);

//        String fullName= user.getFirstName()+" "+user.getLastName();
//        System.out.println(fullName);

        binding = FragmentHomeMaintenanceManBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModelMainMan.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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