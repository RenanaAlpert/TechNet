package com.example.tecknet.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tecknet.R;
import com.example.tecknet.databinding.ActivityMainTechnicianBinding;
import com.example.tecknet.model.InstitutionDetailsInt;
import com.example.tecknet.model.MalfunctionDetailsInt;
import com.example.tecknet.model.ProductDetailsInt;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.model.malfunctionView;
import com.example.tecknet.view.open_malfunctions.MalfunctionDetailsFragment;
import com.example.tecknet.view.open_malfunctions.OpenMalfunctionFragment;
import com.example.tecknet.view.open_malfunctions.OpenMalfunctionsAdapter;
import com.example.tecknet.view.update_profile_main_man.UpdateProfileMainManFragment;
import com.example.tecknet.view.update_profile_technician.UpdateProfileTechnicianFragment;
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
                R.id.nav_home_technician, R.id.nav_open_malfunction, R.id.nav_my_malfunctions)
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
     * add 3 doted menu with sign out and update profile
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
                        }).setNegativeButton("השאר" , null).show();
                return true;
            case R.id.action_settings_technician:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.home_fragment_technician, new UpdateProfileTechnicianFragment());
                fragmentTransaction.addToBackStack(null);//add the transaction to the back stack so the user can navigate back
//                // Commit the transaction
                fragmentTransaction.commit();//to do
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

//    public void passFregment(String[] args){
//        Bundle bundle = new Bundle();
//        bundle.putString("name", args[0]);
//        bundle.putString("area", args[1]);
//        bundle.putString("address", args[2]);
//        bundle.putString("device", args[3]);
//        bundle.putString("company", args[4]);
//        bundle.putString("type", args[5]);
//        bundle.putString("explain", args[6]);
//
//        MalfunctionDetailsFragment details = new MalfunctionDetailsFragment();
//        details.setArguments(bundle);
//
//        this.getFragmentManager().beginTransaction().replace(R.id.button, details).commit();
//    }

}