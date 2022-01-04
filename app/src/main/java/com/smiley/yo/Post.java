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
    String title, description;
    //private boolean status;

    public Post() {
    }

    public Post(String title, String description/*, boolean status*/) {
        this.title = title;
        this.description = description;
        //this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }*/

    /*private static int lastPostId = 0;

    public static ArrayList<Post> createPostsList(int numPosts) {
        ArrayList<Post> posts = new ArrayList<Post>();
        for (int i = 1; i <= numPosts; i++) {
            posts.add(new Post("Title " + ++lastPostId, i <= numPosts / 2));
        }
        return posts;
    }*/

}
