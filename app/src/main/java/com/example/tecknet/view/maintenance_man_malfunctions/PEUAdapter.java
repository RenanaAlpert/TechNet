package com.example.tecknet.view.maintenance_man_malfunctions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tecknet.R;
import com.example.tecknet.controller.maintenance_controller;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.ProductExplanationUser;

import java.util.ArrayList;

public class PEUAdapter extends ArrayAdapter<ProductExplanationUser> {
    private Context mContext;
    private  int mResource;
    ArrayList<ProductExplanationUser> datalist;

    // constructor for our list view adapter.
    public PEUAdapter(@NonNull Context context,  int resource,ArrayList<ProductExplanationUser> dataModalArrayList) {
        super(context, resource, dataModalArrayList);
        mContext = context;
        mResource = resource;
        datalist = dataModalArrayList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        LayoutInflater linf = LayoutInflater.from(mContext);
        convertView = linf.inflate(R.layout.single_malfunction_maintenace_display, parent, false);
        System.out.println("PEUAdapter getView");
//        if (listitemView == null) {
//            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.single_malfunction_maintenace_display, parent, false);
//        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        ProductExplanationUser peu = getItem(position);

        // initializing our UI components of list view item.

//        TextView fullTechNameTV = listitemView.findViewById(R.id.idTVtext);
//        TextView techPhoneTV = listitemView.findViewById(R.id.idTVtext);
//        TextView techEmailTV = listitemView.findViewById(R.id.idTVtext);
        TextView productTypeTV = convertView.findViewById(R.id.product_maintenance);
        TextView productTV = convertView.findViewById(R.id.device_maintenance);
        TextView companyTV = convertView.findViewById(R.id.company_maintenance);
        TextView malfunctionInfoTV = convertView.findViewById(R.id.malfunction_info_maintenance);
        TextView techInfoTV = convertView.findViewById(R.id.tech_info_maintenance);
        TextView statusTV=convertView.findViewById(R.id.status_maintenance);

//        ImageView courseIV = listitemView.findViewById(R.id.idIVimage);//TODO add picture

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        productTypeTV.setText(peu.getProd().getType());
        productTV.setText(peu.getProd().getDevice());
        companyTV.setText(peu.getProd().getCompany());
        malfunctionInfoTV.setText(peu.getMal().getExplanation());
        techInfoTV.setText(maintenance_controller.techString(peu.getUser()));
        statusTV.setText(peu.getMal().getStatus());

        this.notifyDataSetChanged();
        // in below line we are using Picasso to
        // load image from URL in our Image VIew.
//        Picasso.get().load(dataModal.getImgUrl()).into(courseIV);

        // below line is use to add item click listener
        // for our item of list view.
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // on the item click on our list view.
//                // we are displaying a toast message.
//                Toast.makeText(getContext(), "Item clicked is : " + peu.getMalfunctionExplanation(), Toast.LENGTH_SHORT).show();
//            }
//        });
        return convertView;
    }
}

