package com.example.tecknet.view;

import static com.example.tecknet.view.MyMalfunctionsAdapter.MY_PERMISSIONS_REQUEST_SEND_SMS;
import static com.example.tecknet.view.MyMalfunctionsAdapter.sendSMS;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tecknet.R;
import com.example.tecknet.databinding.ActivityMainTechnicianBinding;
import com.example.tecknet.model.UserInt;
import com.google.android.material.navigation.NavigationView;

/**
 * this is the activity that manage all technicians screens after login or sign up
 */
public class HomeTechnician extends AppCompatActivity {
    private UserViewModel passOnUViewModel;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainTechnicianBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainTechnicianBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbarTechnician);


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        //get user from login\signup
        UserInt user = getIntent().getParcelableExtra("User");
        //add user to shared view model so fregment can see it
        passOnUViewModel = new ViewModelProvider(HomeTechnician.this).get(UserViewModel.class);
        passOnUViewModel.setItem(user);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_technician, R.id.nav_open_malfunction, R.id.nav_my_malfunctions, R.id.update_technician_fragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_technician);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_technician, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_technician);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void nullify() {
        this.passOnUViewModel = null;
        this.mAppBarConfiguration = null;
        this.binding = null;
    }

    /**
     * add 3 doted menu with sign out
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_disconnect_technician:
                new AlertDialog.Builder(binding.getRoot().getContext()).setIcon(android.R.drawable.btn_dialog)
                        .setTitle("האם אתה בטוח שאתה רוצה להתנתק?")
                        .setPositiveButton("צא", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nullify();
                                Intent intent = new Intent(HomeTechnician.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("השאר", null).show();
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Toast.makeText(getApplicationContext(), R.string.sms_permission_granted,
                        Toast.LENGTH_SHORT)
                        .show();
                sendSMS();
            } else {
                // Permission request was denied.
                Toast.makeText(getApplicationContext(), R.string.sms_permission_denied,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
    }

    public void permissionsSMS() {
        // Check if the SMS permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            Toast.makeText(getApplicationContext(), R.string.sms_permission_available,
                    Toast.LENGTH_LONG).show();

            sendSMS();
        } else {
            // Permission is missing and must be requested.
            requestSMSPermission();
        }
    }

    /**
     * Requests the {@link Manifest.permission} permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */
    private void requestSMSPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            Toast.makeText(getApplicationContext(), R.string.sms_access_required,
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.sms_unavailable, Toast.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }

//
}