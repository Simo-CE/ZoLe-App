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
        //Services
        ArrayList<String> ListServ=new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        db.collection("services")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document:task.getResult()){
                                ListServ.add(document.getString("userid"));
                                Log.d(TAG, String.valueOf(ListServ));
                            }
                        }
                    }

                });
        //Users
     /*   Map<String,String> ListUser=new HashMap<String,String>();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document:task.getResult()){
                              //  ListUser.put(document.getId(), (String) document.get("userid"));
                                //  Log.d(TAG, String.valueOf(ListUser));
                               // ListUser.put(document.getId(), (String) document.get("email"));
                               // Log.d(TAG, String.valueOf(document.getData()));

                                ArrayList<Users> userList=new ArrayList<>();


                                document.getString("email");

                                //userList.add(document.getString("email"));
                               if(document.getId().equals("cc")){
                                   Log.d(TAG, String.valueOf(document.getData()));
                               }else{
                                   db.collection("users").document("cc");
                                   Log.d(TAG, "non existe");
                               }



                            }
                        }
                    }

                }); */

        /*for(String l:ListServ){
            db.collection("users").document(l)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                               @Override
                                               public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                   if(task.isSuccessful()){
                                                   for (QueryDocumentSnapshot document:task.getResult()){
                                                       holder.userEmail.setText(document.getString("email"));
                                                   }
                                               }}
                                           });




            db.addSnapshotsInSyncListener(new Runnable() {
                @Override
                public void run() {
                    holder.userEmail.setText(document.getString("email"));

                }
            });

        }*/
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
        RelativeLayout linear_Layout;
        RelativeLayout expandableLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.price);
            desc=itemView.findViewById(R.id.desc);
            userEmail=itemView.findViewById(R.id.userEmail);
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
