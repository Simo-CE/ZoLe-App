package com.smiley.yo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private List<Service> List2;
    private Context context;
    private FirebaseFirestore db;
    private static final String TAG = "ServiceAdapter";

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

        //Join
        FirebaseFirestore.getInstance().collection("Users")
                .document(service.getUserid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        holder.userEmail.setText(documentSnapshot.getString("Email"));
                        holder.userPhone.setText(documentSnapshot.getString("Phone"));
                        holder.userLocation.setText(documentSnapshot.getString("Location"));
                        holder.userName.setText(documentSnapshot.getString("FullName"));

                    }
                });
        //Services


    }

    @Override
    public int getItemCount() {
        return List2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView  title;
        TextView  price;
        TextView  desc;
        TextView  userEmail;
        TextView userLocation;
        TextView userPhone;
        TextView userName;
        RelativeLayout linear_Layout;
        RelativeLayout expandableLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.price);
            desc=itemView.findViewById(R.id.desc);
            userEmail=itemView.findViewById(R.id.userEmail);
            userLocation=itemView.findViewById(R.id.userLocation);
            userName=itemView.findViewById(R.id.userName);
            userPhone=itemView.findViewById(R.id.userPhone);
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
