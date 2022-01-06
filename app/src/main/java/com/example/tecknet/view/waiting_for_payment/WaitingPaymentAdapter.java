package com.example.tecknet.view.waiting_for_payment;

import static com.example.tecknet.controller.shared_controller.set_status_malfunction;

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

import com.example.tecknet.R;
import com.example.tecknet.controller.maintenance_controller;
import com.example.tecknet.model.ProductExplanationUser;

import java.util.ArrayList;

public class WaitingPaymentAdapter extends ArrayAdapter<ProductExplanationUser> {
    private Context mContext;


    // constructor for our list view adapter.
    public WaitingPaymentAdapter(@NonNull Context context, int resource, ArrayList<ProductExplanationUser> dataModalArrayList) {
        super(context, resource, dataModalArrayList);
        mContext = context;

    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        LayoutInflater linf = LayoutInflater.from(mContext);
        convertView = linf.inflate(R.layout.singal_main_man_waiting_for_payment, parent, false);


        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        ProductExplanationUser peu = getItem(position);

        // initializing our UI components of list view item.


        TextView malfunctionInfoTV = convertView.findViewById(R.id.malfunction_info_maintenance_payment);
        TextView techInfoTV = convertView.findViewById(R.id.tech_info_maintenance_payment);
        TextView paymentTV = convertView.findViewById(R.id.payment);
        Button forPaymentBtn = convertView.findViewById(R.id.btn_payment);


        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        malfunctionInfoTV.setText(peu.getMal().getExplanation());

        techInfoTV.setText(maintenance_controller.techString(peu.getUser()));

        paymentTV.setText(Double.toString(peu.getMal().getPayment()));
        View finalConvertView = convertView;
        forPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                String message = "מאשר תשלום בסך " + peu.getMal().getPayment() + "\n";

                builder.setMessage(message).setCancelable(false)
                        .setPositiveButton("לתשלום", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                set_status_malfunction(peu.getMal().getMal_id(),"שולם");
//                                        ListView list = (ListView) ((AppCompatActivity)mContext).findViewById(R.id.listview);
//                                        list.removeFooterView(finalConvertView);
//                                        //dialog.cancel();
                            }
                        })
                        .setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
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

