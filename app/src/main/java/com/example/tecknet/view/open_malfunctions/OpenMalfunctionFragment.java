package com.example.tecknet.view.open_malfunctions;

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

import com.example.tecknet.databinding.FragmentOpenMalfunctionsBinding;

public class OpenMalfunctionFragment extends Fragment {

    private OpenMalfunctionViewModel openMalfunctionViewModel;
    private FragmentOpenMalfunctionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        openMalfunctionViewModel =
                new ViewModelProvider(this).get(OpenMalfunctionViewModel.class);

        binding = FragmentOpenMalfunctionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textReportMalfunction;
        openMalfunctionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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