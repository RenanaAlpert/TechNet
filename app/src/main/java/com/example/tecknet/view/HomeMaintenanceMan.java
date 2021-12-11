package com.example.tecknet.view;

import android.os.Bundle;
import android.view.Menu;

import com.example.tecknet.R;
import com.example.tecknet.model.InstitutionDetails;
import com.example.tecknet.model.User;
import com.example.tecknet.model.UserInt;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tecknet.databinding.ActivityMainMainManBinding;;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeMaintenanceMan extends AppCompatActivity {
    private UserViewModel passOnUViewModel;

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

        //get user from login\signup
        UserInt user= getIntent().getParcelableExtra("User");
        //add user to shared view model so fregment can see it
        passOnUViewModel = new ViewModelProvider(HomeMaintenanceMan.this).get(UserViewModel.class);
        passOnUViewModel.setItem(user);




        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_main_man, R.id.nav_report_malfunction, R.id.nav_inventory,R.id.nav_add_product)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_main_man);
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_main_man);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}