package com.example.tecknet.view.my_malfunctions;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.tecknet.R;
import com.example.tecknet.model.Controller;
import com.example.tecknet.model.InstitutionDetailsInt;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetailsInt;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.model.malfunctionView;

import java.util.ArrayList;

public class MyMalfunctionsAdapter extends ArrayAdapter<malfunctionView> {


    private Context mContext;
    private  int mResource;
    private ArrayList<malfunctionView> arrMals;



    public MyMalfunctionsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<malfunctionView> obj) {
        super(context, resource, obj);
        mContext = context;
        mResource = resource;
        arrMals = obj;

    }
    static class ViewHolder {
        TextView text;
        Button btn;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater linf = LayoutInflater.from(mContext);
        convertView = linf.inflate(R.layout.fragment_my_malfunctions_row, parent, false);
        TextView tvName = convertView.findViewById(R.id.School);
        TextView tvAddress = convertView.findViewById(R.id.Address);
        TextView tvDevice = convertView.findViewById(R.id.BreakDevice);
        TextView tvType= convertView.findViewById(R.id.type);
        TextView tvExplain = convertView.findViewById(R.id.FaultDescription);
        TextView tvContact = convertView.findViewById(R.id.Contact);
        TextView tvStatus = convertView.findViewById(R.id.Status);

        MalfunctionDetailsInt mal = getItem(position).getMal();
        ProductDetailsInt product = getItem(position).getProduct();
        InstitutionDetailsInt ins = getItem(position).getIns();
        UserInt userTech = getItem(position).getUser();

        tvName.setText(ins.getName());
        tvAddress.setText(ins.getAddress() + " " + ins.getCity());
        tvDevice.setText(product.getDevice());
        tvType.setText(product.getType());
        tvExplain.setText(mal.getExplanation());
        tvContact.setText(ins.getContact());
        tvStatus.setText(mal.getStatus());

        Button button = convertView.findViewById(R.id.button);
        if(mal.getStatus().equals("נלקח לטיפול")){
            button.setText("התחל טיפול");
        }
        else if(mal.getStatus().equals("בטיפול")){
            button.setText("לסיום");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.getText().equals("התחל טיפול")){
                    Controller.set_status_nalfunction(mal.getMal_id(), "בטיפול");
                    button.setText("לסיום");
                    arrMals.clear(); /// yuval added this line to refresh the list view
                }
                else if(button.getText().equals("לסיום")){
                    Controller.set_status_nalfunction(mal.getMal_id(), "מחכה לתשלום");

                    arrMals.clear(); /// yuval added this line to refresh the list view
                }
               // Controller.set_status_nalfunction(mal.getMal_id(), "בטיפול");
            }
        });

        return convertView;
    }
}