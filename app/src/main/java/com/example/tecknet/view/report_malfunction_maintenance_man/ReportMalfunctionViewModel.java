package com.example.tecknet.view.report_malfunction_maintenance_man;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReportMalfunctionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReportMalfunctionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is report malfunction fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}