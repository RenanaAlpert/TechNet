package com.example.tecknet.view;

import android.os.Bundle;
import android.view.Menu;

import com.example.tecknet.R;
import com.example.tecknet.model.InstitutionDetails;
import com.example.tecknet.model.User;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.home_maintenance_man.HomeFragmentDirections;
import com.example.tecknet.view.report_malfunction_maintenance_man.ReportMalfunctionFragmentArgs;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tecknet.databinding.ActivityMainMainManBinding;

public class HomeMaintenanceMan extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainMainManBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMainManBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        UserInt user= getIntent().getParcelableExtra("User");

        //InstitutionDetails ins= todo get ins from data base
        //pass user forward
        HomeFragmentDirections.PassUserInstNavReportMalfunction action= HomeFragmentDirections.passUserInstNavReportMalfunction();
        action.setUser(user);
        //pass instatution forward
//        action.setInstitution()
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_report_malfunction, R.id.nav_inventory,R.id.nav_add_product)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_maintenance_man, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}