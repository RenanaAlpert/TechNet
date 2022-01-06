package com.example.tecknet.view.maintenance_man_malfunctions;

//import static com.example.tecknet.model.Controller.add_adapter_malfunction_list;
//import static com.example.tecknet.model.Controller.get_malfunction_list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.R;
import com.example.tecknet.controller.maintenance_controller;
import com.example.tecknet.databinding.FragmentManaintanceManMalfunctionsBinding;
import com.example.tecknet.model.ProductExplanationUser;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;

import java.util.ArrayList;


public class MainManMalfunctionsFragment extends Fragment {

    private FragmentManaintanceManMalfunctionsBinding binding;
    private UserViewModel uViewModel;
    private UserInt user;
    private View root;
    private TextView textView;
    private Button delBut;
    private ListView malfunctionsList;
    private ArrayList<ProductExplanationUser> peuModalArrayList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentManaintanceManMalfunctionsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //get user of this app
        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        user = uViewModel.getItem().getValue();

        // below line is use to initialize our variables
        textView = binding.malfunctionsDontExist;
        delBut = binding.btnDelete;
        malfunctionsList = root.findViewById(R.id.listviewMainManMalfunctions);
        peuModalArrayList = new ArrayList<>();

        // here we are calling a method
        // to load data in our list view from data base.
        maintenance_controller.loadDataInListview(user, peuModalArrayList, malfunctionsList, R.layout.single_malfunction_maintenace_display, this.getContext());

        //If the list is empty ( don't enter products to his inventory)  show msg
        malfunctionsList.setEmptyView(textView);

        //Click on delete button for delete mal
        delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "לחץ ארוך על תקלה למחיקה", Toast.LENGTH_LONG).show();
                //If you click ling on some product appears dialog msg
                malfunctionsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        final int itemPos = position; //get the position of the chosen product
                        //Dialog object
                        new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_delete)
                                .setTitle("האם אתה בטוח?").setMessage("האם את/ה רוצה לבטל תקלה זו?")
                                .setPositiveButton(" הסרת תקלה", new DialogInterface.OnClickListener() {
                                    @Override //If you pressed to delete
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Convert to product obj
                                        ProductExplanationUser peu = (ProductExplanationUser) parent.getAdapter().getItem(position);
                                        //Delete this malfunction from his database
                                        maintenance_controller.delete_malfunction(peu, user);
                                        //extract the adapter of the list view
                                        ArrayAdapter<ProductExplanationUser> adapter = (ArrayAdapter<ProductExplanationUser>) parent.getAdapter();
                                        peuModalArrayList.remove(itemPos); //Remove the item

                                        adapter.notifyDataSetChanged();


                                    }
                                }).setNegativeButton("ביטול", null).show();

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


