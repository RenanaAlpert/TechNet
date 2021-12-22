package com.example.tecknet.view.my_malfunctions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyMalfunctionsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyMalfunctionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("אין תקלות לטיפול");
    }

    public LiveData<String> getText() {
        return mText;
    }
}