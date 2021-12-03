package com.smiley.yo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ServicesActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        Intent intent = getIntent();

        ListView services_list = (ListView) findViewById(android.R.id.list);

        final ArrayList<Service> services_array = new ArrayList<>();
        Resources resources = getResources();
        final String[] title = resources.getStringArray(R.array.service_title);
        final int[] price = resources.getIntArray(R.array.service_price);
        for (int i = 0; i < title.length; ++i) {
            services_array.add(new Service(title[i], price[i]));
        }

        ServicesAdapter itemsAdapter = new ServicesAdapter(this, services_array);
        services_list.setAdapter(itemsAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav, menu);
        return true;
    }

    public void onGroupItemClick(MenuItem item) {
        // One of the group items (using the onClick attribute) was clicked
        // The item parameter passed here indicates which item it is

        // All other bottom_nav item clicks are handled by
        // <code><a href="/reference/android/app/Activity.html#onOptionsItemSelected
        // (android.view.MenuItem)">onOptionsItemSelected()</a></code>
    }

}