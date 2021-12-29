package com.smiley.yo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent HomeIntent = getIntent();


        //Top app bar
        /*// Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.topAppBar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);*/

        //Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigatin_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //Setup navigation
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        //Linking fragment labels
        NavigationUI.setupActionBarWithNavController(this ,navController, appBarConfiguration);
        //End Bottom navigation
    }

}