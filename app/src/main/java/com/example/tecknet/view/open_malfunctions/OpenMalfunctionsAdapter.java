package com.example.tecknet.view.open_malfunctions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

import com.example.tecknet.R;
import com.example.tecknet.model.InstitutionDetailsInt;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetailsInt;
import com.example.tecknet.model.malfunctionView;
import com.example.tecknet.view.HomeTechnician;
import com.google.firebase.database.ValueEventListener;

import java.net.InterfaceAddress;
import java.util.ArrayList;

public class OpenMalfunctionsAdapter extends ArrayAdapter<malfunctionView> {

    public interface onClickButton{
        public void passfragment(MalfunctionDetailsInt mal, ProductDetailsInt product, InstitutionDetailsInt ins);
    }


    private Context mContext;
    private  int mResource;
    private onClickButton listener;


    public OpenMalfunctionsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<malfunctionView> obj, onClickButton listener) {
        super(context, resource, obj);
        mContext = context;
        mResource = resource;
        this.listener = listener;
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

        tvName.setText(ins.getName());
        tvCity.setText(ins.getCity());
        tvDevice.setText(product.getDevice());

        Button button = convertView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.passfragment(mal, product, ins);

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

//                ((Activity) mContext).getFragmentManager().beginTransaction().replace(R.id.malfunction_detailes_container, details).commit();
            }
        });

        return convertView;
    }
}