package com.smiley.yo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends ArrayAdapter<Service> {

    public ServicesAdapter(Context context, ArrayList<Service> servicesList) {
        //super(context, R.layout.service_item, servicesList);
        super(context, 0, servicesList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;

        if (currentView == null) {
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.service_item, parent, false);
        }

        Service service = getItem(position);

        assert service != null;

        TextView title = currentView.findViewById(R.id.title);
        TextView price = currentView.findViewById(R.id.price);

        title.setText(service.getServiceTitle());
        price.setText(String.valueOf(service.getServicePrice()));
        //price.setText(service.service_price);

        //return super.getView(position, convertView, parent);
        return currentView;
    }

}