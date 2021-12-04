package com.smiley.yo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent HomeIntent = getIntent();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigatin_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //Setup navigation
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        //Linking fragment labels
        NavigationUI.setupActionBarWithNavController(this ,navController, appBarConfiguration);
    }
}