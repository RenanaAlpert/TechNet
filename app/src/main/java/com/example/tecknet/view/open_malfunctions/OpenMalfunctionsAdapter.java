package com.example.tecknet.view.open_malfunctions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.R;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.InstitutionDetailsInt;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetailsInt;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.model.malfunctionView;
import com.example.tecknet.view.HomeTechnician;
import com.example.tecknet.view.UserViewModel;
import com.google.firebase.database.ValueEventListener;

import java.net.InterfaceAddress;
import java.util.ArrayList;

public class OpenMalfunctionsAdapter extends ArrayAdapter<malfunctionView> {


    private Context mContext;
    private  int mResource;


    public OpenMalfunctionsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<malfunctionView> obj) {
        super(context, resource, obj);
        mContext = context;
        mResource = resource;

    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater linf = LayoutInflater.from(mContext);
        convertView = linf.inflate(R.layout.fragment_open_malfunctions_row, parent, false);
        TextView tvName = convertView.findViewById(R.id.txtArea);
        TextView tvCity = convertView.findViewById(R.id.txtCity);
        TextView tvDevice = convertView.findViewById(R.id.txtDevice);

        MalfunctionDetailsInt mal = getItem(position).getMal();
        ProductDetailsInt product = getItem(position).getProduct();
        InstitutionDetailsInt ins = getItem(position).getIns();
        UserInt user = getItem(position).getUser();

        tvName.setText(ins.getName());
        tvCity.setText(ins.getCity());
        tvDevice.setText(product.getDevice());

        Button button = convertView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                String message =
                        "בית ספר: " + ins.getName() + "\n" +
                        "כתובת: " + ins.getAddress() + " " + ins.getCity() + "\n" +
                        "מכשיר: " + product.getDevice() + "\n" +
                        "חברה: " + product.getCompany() + "\n" +
                        "דגם: " + product.getType() + "\n" +
                        "תיאור התקלה: " + mal.getExplanation();

                builder.setMessage(message).setCancelable(false)
                        .setPositiveButton("לטיפול", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Controller.take_malfunction(user.getPhone(), mal.getMal_id());
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("חזור", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.create().show();

//                listener.passfragment(mal, product, ins);

//                Bundle bundle = new Bundle();
//                bundle.putString("name", ins.getName());
//                bundle.putString("area", ins.getArea());
//                bundle.putString("address", ins.getAddress() + " " + ins.getCity());
//                bundle.putString("device", product.getDevice());
//                bundle.putString("company", product.getCompany());
//                bundle.putString("type", product.getType());
//                bundle.putString("explain", mal.getExplanation());
//
//                Fragment details = MalfunctionDetailsFragment.newInstance();
//                details.setArguments(bundle);
//
//                ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.malfunction_detailes_container, details).commit();
            }
        });

        return convertView;
    }
}