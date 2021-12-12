package com.example.tecknet.view.update_profile_main_man;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UpdateProfileMainManViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UpdateProfileMainManViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is update main man fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}