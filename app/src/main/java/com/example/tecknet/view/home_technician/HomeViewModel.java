package com.example.tecknet.view.home_technician;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tecknet.R;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetailsInt;

import java.util.Collection;


public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is technician home fragment");
    }

    public LiveData<String> getText() {
//        Collection<MalfunctionDetailsInt> open_mals = Controller.open_malfunction();
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1);
//        for (MalfunctionDetailsInt mal: open_mals){
//            ProductDetailsInt product = Controller.get_product(mal.getProduct_id());
//            adapter.add(product.getDevice());
//            adapter.add(product.getCompany());
//        }
//        ListView list = (ListView) root.findViewById(R.id.listview);
        return mText;
    }
}