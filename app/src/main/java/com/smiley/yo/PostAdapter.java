package com.smiley.yo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    Context context;
    ArrayList<Post> postArrayList;
    private OnPostListener mOnPostListener;

    public PostAdapter(Context context, ArrayList<Post> postArrayList, OnPostListener onPostListener) {
        this.context = context;
        this.postArrayList = postArrayList;
        this.mOnPostListener = onPostListener;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_post_item, parent, false);
        return new PostViewHolder(view, mOnPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {

        Post post = postArrayList.get(position);

        holder.title.setText(post.title);
        holder.description.setText(post.description);
        holder.fullname.setText(post.fullname);
        //holder.email.setText(post.email);
       // holder.location.setText(post.location);
       // holder.email.setText(post.userId);

        FirebaseFirestore.getInstance().collection("Users")
                .document(post.userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                        holder.fullname.setText(documentSnapshot.getString("FullName"));
                        holder.email.setText(documentSnapshot.getString("Email"));
                        holder.location.setText(documentSnapshot.getString("Location"));
                    }
                });

    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, description, fullname, email, location;
        OnPostListener onPostListener;

        public PostViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            this.onPostListener = onPostListener;
            title = itemView.findViewById(R.id.home_post_title);
            description = itemView.findViewById(R.id.home_post_description);
            fullname = itemView.findViewById(R.id.home_post_name);
            email = itemView.findViewById(R.id.home_post_email);
            location = itemView.findViewById(R.id.home_post_location);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPostListener.onPostClick(getAdapterPosition());
        }
    }

    public interface OnPostListener{
        void onPostClick(int position);
    }


}