package com.example.tecknet.view.open_malfunctions;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tecknet.R;
import com.example.tecknet.controller.technician_controller;
import com.example.tecknet.model.InstitutionDetailsInt;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetailsInt;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.model.MalfunctionView;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class OpenMalfunctionsAdapter extends ArrayAdapter<MalfunctionView> {


    private final Context mContext;
    private final int mResource;
    private MalfunctionDetailsInt mal;
    private ProductDetailsInt product;
    private InstitutionDetailsInt ins;
    private UserInt user;

    public OpenMalfunctionsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MalfunctionView> obj) {
        super(context, resource, obj);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater linf = LayoutInflater.from(mContext);
        convertView = linf.inflate(mResource, parent, false);
        TextView tvName = convertView.findViewById(R.id.txtArea);
        TextView tvCity = convertView.findViewById(R.id.txtCity);
        TextView tvDevice = convertView.findViewById(R.id.txtDevice);

        mal = getItem(position).getMal();
        product = getItem(position).getProduct();
        ins = getItem(position).getIns();
        user = getItem(position).getUser();

//        if(mal.get_malPicId() != null) {
//            // Reference to an image file in Cloud Storage
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("/mals_images/" + mal.get_malPicId());
//            ImageView image = convertView.findViewById(R.id.imageView);
//
//            // Load the image using Glide
//            Glide.with(mContext)
//                    .load(storageReference)
//                    .into(image);
//        }

        tvName.setText(ins.getName());
        tvCity.setText(ins.getCity());
        tvDevice.setText(product.getDevice());

        Button button = convertView.findViewById(R.id.button);
        View finalConvertView = convertView;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog(finalConvertView, v.getContext());
            }
        });
        return convertView;
    }


    private void dialog(View finalConvertView, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String message = message_of_dialog();

        builder.setMessage(message).setCancelable(false)
                .setPositiveButton("לטיפול", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        technician_controller.take_malfunction(user.getPhone(), mal.getMal_id());
                        ListView list = (ListView) ((AppCompatActivity)mContext).findViewById(R.id.listview);
                        list.removeFooterView(finalConvertView);
                    }
                })
                .setNegativeButton("חזור", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }


    private String message_of_dialog(){
        return  "בית ספר: " + ins.getName() + "\n" +
                "כתובת: " + ins.getAddress() + " " + ins.getCity() + "\n" +
                "מכשיר: " + product.getDevice() + "\n" +
                "חברה: " + product.getCompany() + "\n" +
                "דגם: " + product.getType() + "\n" +
                "תיאור התקלה: " + mal.getExplanation();
    }
}