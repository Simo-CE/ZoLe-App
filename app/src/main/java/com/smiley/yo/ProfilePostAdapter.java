package com.smiley.yo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

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
        //holder.description.setText(post.description);

        holder.removePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPosition = holder.getAdapterPosition();
                FirebaseFirestore.getInstance().collection("posts")
                        .document(postArrayList.get(newPosition).documentId)
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    postArrayList.remove(newPosition);
                                    notifyDataSetChanged();
                                    Toast.makeText(context.getApplicationContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        TextView title, description;
        ImageView editPost,removePost;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titlePost);
            editPost=itemView.findViewById(R.id.edit_post);
            removePost=itemView.findViewById(R.id.remove_post);
            //description = itemView.findViewById(R.id.descriptionPost);


        }
    }



}
