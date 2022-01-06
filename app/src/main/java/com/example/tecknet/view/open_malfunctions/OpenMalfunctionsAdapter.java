package com.example.tecknet.view.open_malfunctions;

import static com.example.tecknet.controller.SharedController.loadMalImage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.tecknet.R;
import com.example.tecknet.controller.TechnicianController;
import com.example.tecknet.model.InstitutionDetailsInt;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.MalfunctionView;
import com.example.tecknet.model.ProductDetailsInt;
import com.example.tecknet.model.UserInt;

import java.util.ArrayList;

public class OpenMalfunctionsAdapter extends ArrayAdapter<MalfunctionView> {


    private Context mContext;
    private int mResource;
    private ArrayList<MalfunctionView> arrMals;


    public OpenMalfunctionsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MalfunctionView> obj) {
        super(context, resource, obj);
        mContext = context;
        mResource = resource;
        arrMals = obj;

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
        ImageView imageView = convertView.findViewById(R.id.malfunction_image);

        MalfunctionDetailsInt mal = getItem(position).getMal();
        ProductDetailsInt product = getItem(position).getProduct();
        InstitutionDetailsInt ins = getItem(position).getIns();
        UserInt user = getItem(position).getUser();

        tvName.setText(ins.getName());
        tvCity.setText(ins.getCity());
        tvDevice.setText(product.getDevice());

        Button button = convertView.findViewById(R.id.button);
        View finalConvertView = convertView;
        if (mal.get_malPicId() != null) {
            loadMalImage(mal.get_malPicId(), mContext, imageView);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                String message =
                        mContext.getString(R.string.school_name) + ins.getName() + "\n" +
                                mContext.getString(R.string.address_header) + ins.getAddress() + " " + ins.getCity() + "\n" +
                                mContext.getString(R.string.product_type_txt) + product.getDevice() + "\n" +
                                mContext.getString(R.string.company_txt) + product.getCompany() + "\n" +
                                mContext.getString(R.string.device_txt) + product.getType() + "\n" +
                                mContext.getString(R.string.malfunction_details_txt) + mal.getExplanation();

                builder.setMessage(message).setCancelable(false)
                        .setPositiveButton(R.string.to_handle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TechnicianController.take_malfunction(user.getPhone(), mal.getMal_id());
                                ListView list = (ListView) ((AppCompatActivity) mContext).findViewById(R.id.listview);
                                list.removeFooterView(finalConvertView);
                                //dialog.cancel();
                            }
                        })
                        .setNegativeButton(R.string.go_back, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.create().show();

            }

        });

        return convertView;
    }
}