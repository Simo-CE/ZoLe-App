package com.smiley.yo;
/*
Using a RecyclerView has the following key steps:
    Define a model class to use as the data source
    Add a RecyclerView to your activity to display the items
    Create a custom row layout XML file to visualize the item
    Create a RecyclerView.Adapter and ViewHolder to render the item
    Bind the adapter to the data source to populate the RecyclerView
*/
public class Post {
    private String title;
    private boolean status;

    public Post(String title, boolean status) {
        this.title = title;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public boolean getStatus() {
        return status;
    }

    /*private static int lastPostId = 0;

    public static ArrayList<Post> createPostsList(int numPosts) {
        ArrayList<Post> posts = new ArrayList<Post>();
        for (int i = 1; i <= numPosts; i++) {
            posts.add(new Post("Title " + ++lastPostId, i <= numPosts / 2));
        }
        return posts;
    }*/

}
