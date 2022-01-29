package com.smiley.yo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfilePostAdapter extends RecyclerView.Adapter<ProfilePostAdapter.PostViewHolder>{

    Context context;
    ArrayList<Post> postArrayList;

    public ProfilePostAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public ProfilePostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.profile_post_item, parent, false);

        return new ProfilePostAdapter.PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilePostAdapter.PostViewHolder holder, int position) {

        Post post = postArrayList.get(position);

        holder.title.setText(post.title);
        holder.description.setText(post.description);


    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        TextView title, description;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titlePost);
            description = itemView.findViewById(R.id.descriptionPost);


        }
    }



}
