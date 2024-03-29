package com.example.tecknet.view.inventory_maintenance_man;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.controller.MaintenanceController;
import com.example.tecknet.databinding.FragmentInventoryMaintenanceManBinding;
import com.example.tecknet.model.ProductDetails;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;

import java.util.ArrayList;

public class InventoryFragment extends Fragment {

    private UserViewModel uViewModel;
    private FragmentInventoryMaintenanceManBinding binding;
    private Button delBut;
    private EditText search;
//    FloatingActionButton delBut;
    private ListView list;
    private  TextView textView;

//    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInventoryMaintenanceManBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //get the user details
        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user=uViewModel.getItem().getValue();


        //define arraylist of products
        ArrayList<ProductDetails> arrProd = new ArrayList<>();
        String phone = user.getPhone(); //user phone number

        //initialize the buttons
        list = binding.listview;
        textView = binding.empty;
        delBut = binding.btnDelete;
        search = binding.searchButton;
        //call the function in controller to show the user inventory
        MaintenanceController.show_inventory(phone ,  arrProd , list , root , search);
//        If the list is empty ( don't enter products to his inventory)  show msg
        list.setEmptyView(textView);


        //Click on delete button for delete product in inventory
        delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity() , "לחץ ארוך על מוצר למחיקה" , Toast.LENGTH_LONG).show();
                //If you click ling on some product appears dialog msg
                list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        final int wichItem = position; //get the position of the chosen product
                        //Dialog object
                        new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_delete)
                                .setTitle("האם אתה בטוח?").setMessage("האם את/ה רוצה למחוק מוצר זה מהמלאי?")
                                .setPositiveButton("מחק", new DialogInterface.OnClickListener() {
                                    @Override //If you pressed to delete
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Convert to product obj
                                        ProductDetails prod = (ProductDetails) parent.getAdapter().getItem(position);
                                        //Delete this product from his database
                                        MaintenanceController.delete_product_from_inventory(prod, phone);
                                        //extract the adapter of the list view
                                        ArrayAdapter<ProductDetails> adapter = (ArrayAdapter<ProductDetails>)parent.getAdapter();
                                        arrProd.remove(wichItem); //Remove the item
                                        //Stackoverflow code to update the list view
                                        arrProd.clear();
                                        arrProd.addAll(arrProd);
                                        list.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                        //

                                    }
                                }).setNegativeButton("ביטול" , null).show();
                        return true;
                    }
                });
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}