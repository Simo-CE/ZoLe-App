package com.smiley.yo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    Context context;
    ArrayList<Post> postArrayList;

    public PostAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_post_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {

        Post post = postArrayList.get(position);

        holder.title.setText(post.title);
        holder.description.setText(post.description);
        holder.fullname.setText(post.fullname);
        holder.email.setText(post.email);
        holder.location.setText(post.location);
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, fullname, email, location;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.home_post_title);
            description = itemView.findViewById(R.id.home_post_description);
            fullname = itemView.findViewById(R.id.home_post_name);
            email = itemView.findViewById(R.id.home_post_email);
            location = itemView.findViewById(R.id.home_post_location);
        }
    }


}