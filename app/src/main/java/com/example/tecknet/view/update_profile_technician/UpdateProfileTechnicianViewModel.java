package com.example.tecknet.view.update_profile_technician;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UpdateProfileTechnicianViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UpdateProfileTechnicianViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is update main technician fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}