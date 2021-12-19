package com.example.tecknet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tecknet.R;
import com.example.tecknet.model.UserInt;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
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
        //add utser to shared view model so fregment can see it
        passOnUViewModel = new ViewModelProvider(HomeMaintenanceMan.this).get(UserViewModel.class);
        passOnUViewModel.setItem(user);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_main_man, R.id.nav_report_malfunction,R.id.nav_report_malfunction_new, R.id.nav_inventory, R.id.nav_add_product)
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
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_disconnect_main_man: //move to enter page
                nullify();//empty all.
                intent = new Intent(HomeMaintenanceMan.this, MainActivity.class);
                startActivity(intent);
                return true;

            //todo add transition to add profile
//            case R.id.action_move_to_update_profile_main_man: //move to update profile fragment
//                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.content, new UpdateProfileMainManFragment());
//                fragmentTransaction.addToBackStack(null);//add the transaction to the back stack so the user can navigate back
//                // Commit the transaction
//                fragmentTransaction.commit();//to do
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}