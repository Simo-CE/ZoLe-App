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
    String title, description, fullname, email, phone, location;
    String documentId;
    //private String status;

    public Post() {
    }

    public Post(String title, String description, String fullname, String email, String phone, String location) {
        this.title = title;
        this.description = description;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.location = location;
        //this.status = status;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /*public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }*/

}
