package com.example.tecknet.view.my_malfunctions;

import static com.example.tecknet.controller.SharedController.set_status_malfunction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import androidx.media.app.NotificationCompat;

import com.example.tecknet.R;
import com.example.tecknet.controller.TechnicianController;
import com.example.tecknet.model.InstitutionDetailsInt;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetailsInt;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.model.MalfunctionView;
import com.example.tecknet.view.HomeTechnician;

import java.util.ArrayList;

public class MyMalfunctionsAdapter extends ArrayAdapter<MalfunctionView> {

    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static Context mContext;
    private int mResource;
    private ArrayList<MalfunctionView> arrMals;
    private MalfunctionDetailsInt mal;
    private ProductDetailsInt product;
    private InstitutionDetailsInt ins;
    protected static String phoneNo;
    protected static String message;


    public MyMalfunctionsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MalfunctionView> obj) {
        super(context, resource, obj);
        mContext = context;
        mResource = resource;
        arrMals = obj;

    }

//    static class ViewHolder {
//        TextView text;
//        Button btn;
//    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater linf = LayoutInflater.from(mContext);
        convertView = linf.inflate(R.layout.fragment_my_malfunctions_row, parent, false);
        TextView tvName = convertView.findViewById(R.id.School);
        TextView tvAddress = convertView.findViewById(R.id.Address);
        TextView tvDevice = convertView.findViewById(R.id.BreakDevice);
        TextView tvType = convertView.findViewById(R.id.type);
        TextView tvExplain = convertView.findViewById(R.id.FaultDescription);
        TextView tvContact = convertView.findViewById(R.id.Contact);
        TextView tvStatus = convertView.findViewById(R.id.Status);

        mal = getItem(position).getMal();
        product = getItem(position).getProduct();
        ins = getItem(position).getIns();
        UserInt maintenance = getItem(position).getUser();

        tvName.setText(ins.getName());
        tvAddress.setText(ins.getAddress() + " " + ins.getCity());
        tvDevice.setText(product.getDevice());
        tvType.setText(product.getType());
        tvExplain.setText(mal.getExplanation());
        tvContact.setText(maintenance.getFirstName() + " " + maintenance.getLastName() + " - " + ins.getContact());
        tvStatus.setText(mal.getStatus());

        Button button = convertView.findViewById(R.id.button);
        if (mal.getStatus().equals("נלקח לטיפול")) {
            button.setText("התחל טיפול");
        } else if (mal.getStatus().equals("בטיפול")) {
            button.setText("לסיום");
        } else if (mal.getStatus().equals("מחכה לתשלום")) {
            button.setEnabled(false);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrMals.clear();

                if (button.getText().equals("התחל טיפול")) {
                    set_status_malfunction(mal.getMal_id(), "בטיפול");
                    button.setText("לסיום");
                    arrMals.clear(); /// yuval added this line to refresh the list view
                } else if (button.getText().equals("לסיום")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    final EditText edittext = new EditText(v.getContext());
                    alert.setMessage("\nעלות הטיפול:").setCancelable(false);
                    alert.setTitle("בקשת תשלום");
                    alert.setView(edittext);
                    arrMals.clear();
                    alert.setPositiveButton("שלח", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (false) {
                                Toast.makeText(mContext, "לא הוכנס מחיר", Toast.LENGTH_LONG).show();
                            } else {
                                String pay = edittext.getText().toString();
                                double payment = Double.parseDouble(pay);
                                phoneNo = ins.getContact();
                                System.out.println("hello there " + mal.getMal_id() + " " + payment);
                                createSMS(payment);
                                TechnicianController.set_payment_nalfunction(mal.getMal_id(), payment);
                                TechnicianController.set_status_malfunction(mal.getMal_id(), "מחכה לתשלום");
                                arrMals.clear(); /// yuval added this line to refresh the list view
                            }
                        }
                    }).setNegativeButton("חזור", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                            arrMals.clear(); /// yuval added this line to refresh the list view
                        }
                    });

                    alert.show();
                }
            }
        });

        return convertView;
    }

    /**
     * send messege after been called from check permission in activity
     */
    public static void sendSMS() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
    }

    /**
     * create messege and then sent to check permissiones in activity
     * @param pay
     */
    private void createSMS(double pay) {
        message = " התקלה במכשיר " + product.getType() + " - " + mal.getExplanation()
                + " תוקנה בהצלחה. מחיר הטיפול הינו " + pay + " ש\"ח\n";
        ((HomeTechnician) mContext).permissionsSMS();

    }


}


