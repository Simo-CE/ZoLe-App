package com.smiley.yo;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent HomeIntent = getIntent();

        //Top app bar
        //Toolbar toolbar = findViewById(R.id.topAppBar);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);


        //Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigatin_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);

        //NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_fragment);
        //NavController navController = navHostFragment.getNavController();

        //Setup navigationUI
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        //Linking fragment labels
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //End Bottom navigation
    }

    // Menu icons are inflated just as they were with actionbar

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notificationMenu:

                return true;

            case R.id.searchMenu:
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        processSearch(s);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        processSearch(s);
                        return false;
                    }
                });
                return true;

            case R.id.logoutMenu:
                //sing out user
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;

            default:
                // action was not recognized, Invoking the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void processSearch(String s) {
    }




}