package com.smiley.yo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private List<Service> List2;
    private Context context;

    public ServiceAdapter(List<Service> List2, Context context){
        this.List2 =List2;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return  new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = List2.get(position);
        holder.price.setText(service.getPrice());
        holder.title.setText(service.getTitle());
        holder.desc.setText(service.getDesc());
    }

    @Override
    public int getItemCount() {
        return List2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView  title;
        TextView  price;
        TextView  desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.price);
            desc=itemView.findViewById(R.id.desc);
        }
    }
}
