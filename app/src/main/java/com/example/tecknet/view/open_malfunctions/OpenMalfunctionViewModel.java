package com.example.tecknet.view.open_malfunctions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OpenMalfunctionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OpenMalfunctionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is open malfunction technician fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}