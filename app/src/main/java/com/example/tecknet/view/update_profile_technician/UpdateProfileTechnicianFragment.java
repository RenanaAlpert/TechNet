package com.example.tecknet.view.update_profile_technician;

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

import com.example.tecknet.databinding.FragmentUpdateProfileTechnicianBinding;

public class UpdateProfileTechnicianFragment extends Fragment {

    private UpdateProfileTechnicianViewModel updateProfileTechnicianViewModel;
    private FragmentUpdateProfileTechnicianBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        updateProfileTechnicianViewModel =
                new ViewModelProvider(this).get(UpdateProfileTechnicianViewModel.class);

        binding = FragmentUpdateProfileTechnicianBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textUpdateProfileTechnician;
        updateProfileTechnicianViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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