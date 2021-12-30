package com.example.tecknet.view.update_profile_main_man;

import static android.content.ContentValues.TAG;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.R;
import com.example.tecknet.databinding.FragmentUpdateProfileMainManBinding;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

public class UpdateProfileMainManFragment extends Fragment {

    private UpdateProfileMainManViewModel updateProfileMainManViewModel;
    private UserViewModel uViewModel;
    private FragmentUpdateProfileMainManBinding binding;
    private EditText fName , lName, changePass , changePass2 , currPass, city, adrr;
    private Spinner area;
    private Button updateBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        updateProfileMainManViewModel =
                new ViewModelProvider(this).get(UpdateProfileMainManViewModel.class);

        binding = FragmentUpdateProfileMainManBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user=uViewModel.getItem().getValue();
        ConstraintLayout d = binding.updateMainManFragment;
        fName = binding.changeFName;
        lName = binding.changeLName;
        changePass = binding.newPass;
        changePass2 = binding.newPassConfirm;
        currPass = binding.currPass;
        city = binding.changeInsCity;
        adrr = binding.changeInsAdrr;
        area = binding.changeInsArea;
        updateBtn = binding.button ;

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_valid_chang_pass(user , changePass.getText().toString(),changePass2.getText().toString()
                                            , currPass.getText().toString())) {
                    //controller.update_user_details
                    Controller.update_user_details(user.getPhone(), fName.getText().toString(), lName.getText().toString());

                    //controller.update_ins_adrr
                    Controller.update_institution_adrr(user.getPhone(), city, adrr, area);
                    clear_edit_text();
                    Toast.makeText(getActivity() , "עדכון פרופיל הצליח" , Toast.LENGTH_LONG).show();
                }
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean check_valid_chang_pass(UserInt user , String pass1 , String pass2 ,String curr) {
        boolean temp = false;

        if (!pass1.equals("") && !pass1.equals(" ") && !pass1.equals("\n")) {
            temp = true;
        }
        if(!pass2.equals("") && !pass2.equals(" ") && !pass2.equals("\n")) {
            temp = true;
        }
        if(!curr.equals("") && !curr.equals(" ") && !curr.equals("\n")) {
            temp = true;
        }
        if(temp){
            if (user.getPass().equals(curr)) {
                if (pass1.equals(pass2)) {
                    //call the controller
                    Controller.update_user_pass(user.getPhone(), changePass);
                    /////////////////
                    Controller.change_user_pass(user.getEmail(), pass1);
                    ////////////////
                    user.setPass(pass1);

                    return  true;
                }
                else {
                    changePass.setError("הסיסמאות לא תואמים");
                    changePass2.setError("הסיסמאות לא תואמים");
                    return  false;
                }
            }
            else {
                currPass.setError("סיסמה שגויה");
                return  false;
            }
        }
        return true;
    }
    private void clear_edit_text(){
        fName.getText().clear();
        lName.getText().clear();
        changePass.getText().clear();
        changePass2.getText().clear();
        currPass.getText().clear();
        city.getText().clear();
        adrr.getText().clear();
    }
}