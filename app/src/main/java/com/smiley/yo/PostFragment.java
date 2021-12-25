package com.smiley.yo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputEditText postTitle, postDesc;
    private ExtendedFloatingActionButton addPostBtn;
    private FirebaseFirestore db;
    private static final String TAG = "PostsFragment LOG";

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_post, container, false);

        //Initializing cloud firestore
        db = FirebaseFirestore.getInstance();
        initializeUI(v);
        addPostBtn.setOnClickListener(view -> addPost());
        return v;
    }

    private void initializeUI(View v) {
        postTitle = v.findViewById(R.id.postTitleFragment);
        postDesc = v.findViewById(R.id.postDescriptionFragment);
        addPostBtn = v.findViewById(R.id.addPostBtn);
    }

    //Add post with an auto-generate an ID
    public void addPost() {
        String ptitle, pdescription;
        ptitle = postTitle.getText().toString();
        pdescription = postDesc.getText().toString();

        if (TextUtils.isEmpty(ptitle)) {
            Toast.makeText(getContext(), "Please enter a valid title", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pdescription)) {
            Toast.makeText(getContext(), "Provide post descritption", Toast.LENGTH_LONG).show();
            return;
        }

        //Setting the post document
        Map<String, Object> data = new HashMap<>();
        data.put("title", ptitle);
        data.put("descrition", pdescription);

        db.collection("posts")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    Log.d(TAG, "Document created succesfully ");
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    /*public void addPost() {
        // Create a new user with a first, middle, and last name
        Map<String, Object> data = new HashMap<>();
        data.put("first", "Alan");
        data.put("middle", "Mathison");
        data.put("last", "Turing");
        data.put("born", 1912);

        // Add a new document with a generated ID
        db.collection("posts")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }*/

    /*//Setting posts document
    public void setDocument() {
        //Setting the post document
        Map<String, Object> designPost = new HashMap<>();
        designPost.put("title", "Design");
        designPost.put("descrition", "Looking for a well educated tutor.");
        designPost.put("status", "Available");

        db.collection("posts").document("post")
                .set(designPost)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }*/

    /*public final class PostsDB {
        // Root collection name
        public static final String COLLECTION_NAME = "posts";

        // Document ID
        public static final String DOCUMENT_ID = "post";

        // Document field names
        public static final String FIELD_TITLE = "title";
        public static final String FIELD_DESCRIPTION = "description";
        //public static final String FIELD_STATE = "state";

        //To prevent someone from accidentally instantiating the contract class, make the constructor private
        private PostsDB() {
        }
    }*/
}