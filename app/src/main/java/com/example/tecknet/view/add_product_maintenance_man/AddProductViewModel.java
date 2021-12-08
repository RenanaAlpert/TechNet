package com.example.tecknet.view.add_product_maintenance_man;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddProductViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddProductViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is new product fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}