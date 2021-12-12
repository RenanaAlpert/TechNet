package com.example.tecknet.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tecknet.model.UserInt;

/**
 * this is a class used to share the current user object with all fragment of the menu
 */
public class UserViewModel extends ViewModel {
    private final MutableLiveData<UserInt> selectedItem = new MutableLiveData<UserInt>();
    public void setItem(UserInt user) {
        selectedItem.setValue(user);
    }
    public LiveData<UserInt> getItem() {
        return selectedItem;
    }
}
