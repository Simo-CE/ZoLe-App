package com.smiley.yo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView titleTextView;
        public TextView statusTextView;
        //public Button messageButton;

        // create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.post_title);
            statusTextView = (TextView) itemView.findViewById(R.id.post_status);
            //messageButton = (Button) itemView.findViewById(R.id.message_button);
        }
    }

    // Store a member variable for the posts
    private List<Post> mPost;

    // Pass in the post array into the constructor
    public PostsAdapter(List<Post> posts) {
        mPost = posts;
    }


    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View postView = inflater.inflate(R.layout.item_post, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(PostsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Post post = mPost.get(position);

        // Set item views based on your views and data model
        TextView titleView = holder.titleTextView;
        titleView.setText(post.getTitle());
        TextView statusView = holder.statusTextView;
        statusView.setText(post.getStatus() ? "Available" : "Expired");
        statusView.setEnabled(post.getStatus());

        /*Button button = holder.messageButton;
        button.setText(contact.isOnline() ? "Message" : "Offline");
        button.setEnabled(contact.isOnline());*/
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mPost.size();
    }

}
