package com.example.tecknet.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tecknet.R;
import com.example.tecknet.model.UserInt;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tecknet.databinding.ActivityMainMainManBinding;

/**
 * this is the activity that manage all maintenance man screens after login or sign up
 */
public class HomeMaintenanceMan extends AppCompatActivity {
    private UserViewModel passOnUViewModel;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainMainManBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMainManBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbarMainMan);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        //get user from login\signup
        UserInt user = getIntent().getParcelableExtra("User");
        //add user to shared view model so fragment can see it
        passOnUViewModel = new ViewModelProvider(HomeMaintenanceMan.this).get(UserViewModel.class);
        passOnUViewModel.setItem(user);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_main_man, R.id.nav_report_malfunction, R.id.nav_inventory, R.id.nav_add_product, R.id.nav_waiting_for_payment, R.id.nav_update_details)
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
//        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_disconnect_main_man: //move to enter page
                new AlertDialog.Builder(binding.getRoot().getContext()).setIcon(android.R.drawable.btn_dialog)
                        .setTitle("האם אתה בטוח שאתה רוצה להתנתק?")
                        .setPositiveButton("צא", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nullify();//empty all.
                                Intent intent = new Intent(HomeMaintenanceMan.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("השאר", null).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}