package com.smiley.yo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

        Boolean isExpandable=List2.get(position).isExpandables();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return List2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView  title;
        TextView  price;
        TextView  desc;
        RelativeLayout linear_Layout;
        RelativeLayout expandableLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.price);
            desc=itemView.findViewById(R.id.desc);
            linear_Layout=itemView.findViewById(R.id.linearLayout);
            expandableLayout=itemView.findViewById(R.id.expandable_layout);

            linear_Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Service service = List2.get(getAdapterPosition());
                    service.setExpandables(!service.isExpandables());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
