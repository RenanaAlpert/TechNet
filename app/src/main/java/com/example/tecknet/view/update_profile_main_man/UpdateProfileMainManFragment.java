package com.example.tecknet.view.update_profile_main_man;

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

import com.example.tecknet.databinding.FragmentUpdateProfileMainManBinding;

public class UpdateProfileMainManFragment extends Fragment {

    private UpdateProfileMainManViewModel updateProfileMainManViewModel;
    private FragmentUpdateProfileMainManBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        updateProfileMainManViewModel =
                new ViewModelProvider(this).get(UpdateProfileMainManViewModel.class);

        binding = FragmentUpdateProfileMainManBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textUpdateProfileMainMan;
        updateProfileMainManViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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