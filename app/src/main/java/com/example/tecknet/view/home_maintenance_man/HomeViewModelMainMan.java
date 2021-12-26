package com.example.tecknet.view.home_maintenance_man;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModelMainMan extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModelMainMan() {
        mText = new MutableLiveData<>();
        mText.setValue("ניסויייי");
    }

    public LiveData<String> getText() {
        return mText;
    }
}