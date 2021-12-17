package com.example.tecknet.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tecknet.model.MaintenanceManInt;
import com.example.tecknet.model.UserInt;

/**
 * this is a class used to share the current user object with all fragment of the menu
 */
public class MaintenanceManViewModel extends ViewModel {
    private final MutableLiveData<MaintenanceManInt> selectedItem = new MutableLiveData<>();
    public void setItem(MaintenanceManInt mainMan) {
        selectedItem.setValue(mainMan);
    }
    public LiveData<MaintenanceManInt> getItem() {
        return selectedItem;
    }
}
