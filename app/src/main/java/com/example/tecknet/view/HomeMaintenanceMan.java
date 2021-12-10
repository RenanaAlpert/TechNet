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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeMaintenanceMan extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainMainManBinding binding;

    FirebaseDatabase rootNode;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootNode = FirebaseDatabase.getInstance();
        dataRef = rootNode.getReference("institution");

        binding = ActivityMainMainManBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        UserInt user= getIntent().getParcelableExtra("User");
//        //action to pass on institution symbol
        Bundle bundle = new Bundle();
//
//
////get from database the institution number / symbol
//        //todo move other screen
//        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                UserInt myUser;
//                if (dataSnapshot.child(user.getPhone()).exists()) {
//                    if (!user.getPhone().isEmpty()) {
//                        String s = dataSnapshot.child(user.getPhone()).getValue(InstitutionDetails.class).getInstitution_id();
                       String s="555" ;
        bundle.putString("institution_symbol", s);
//
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//        });
//
//
//
//



        //pass instatution forward
//        action.setInstitution

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_report_malfunction, R.id.nav_inventory,R.id.nav_add_product)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.pass_nav_report_malfunction,bundle);
//        navController.navigate(R.id.nav_home);

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