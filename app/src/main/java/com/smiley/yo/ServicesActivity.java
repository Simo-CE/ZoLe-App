package com.smiley.yo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

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
}