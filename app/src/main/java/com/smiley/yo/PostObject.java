package com.smiley.yo;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RealmClass
public class PostObject extends RealmObject {
    @Required
    @PrimaryKey
    private int idPost;
    @Required
    private String postTitle;
    private String postDesc;
    @Required
    @Ignore
    private String postOwner;
    private boolean postStatus;
    private Date creationDate;
    //boolean result = Boolean.TRUE;
    //private RealmList<String> postsList;

    //private PostObject client;   //Many to one
    //private RealmList<PostObject> client; //Many to many

    public PostObject(int idPost, String postTitle, String postDesc, String postOwner, boolean postStatus, Date creationDate) {
        this.idPost = idPost;
        this.postTitle = postTitle;
        this.postDesc = postDesc;
        this.postOwner = postOwner;
        this.postStatus = postStatus;
        this.creationDate = creationDate;
    }

    public PostObject() {
    }
}
