package com.example.tecknet.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tecknet.model.UserInt;

public class ItemViewModel extends ViewModel {
    private final MutableLiveData<UserInt> selectedItem = new MutableLiveData<UserInt>();
    public void selectItem(UserInt user) {
        selectedItem.setValue(user);
    }
    public LiveData<UserInt> getSelectedItem() {
        return selectedItem;
    }
}
