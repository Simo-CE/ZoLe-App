package com.smiley.yo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPostFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private TextInputEditText postTitle, postDesc, postLocation;
    private MaterialButton addPostBtn;
    private FirebaseFirestore db;
    String[] level = {"Beginner", "Intermediate", "Expert"};
    private String requiredLevel;
    private static final String TAG = "PostsFragment LOG";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPostFragment newInstance(String param1, String param2) {
        AddPostFragment fragment = new AddPostFragment();
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
        View v = inflater.inflate(R.layout.fragment_add_post, container, false);

        //spinner
        Spinner postSpinner = v.findViewById(R.id.postLevelSpinner);
        postSpinner.setOnItemSelectedListener(this);
        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, level);
        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        postSpinner.setAdapter(ad);

        //Initializing cloud firestore
        db = FirebaseFirestore.getInstance();
        initializeUI(v);
        addPostBtn.setOnClickListener(view -> addPost());
        return v;
    }

    private void initializeUI(View v) {
        postTitle = v.findViewById(R.id.postTitleFragment);
        postDesc = v.findViewById(R.id.postDescriptionFragment);
        postLocation = v.findViewById(R.id.postLocation);
        addPostBtn = v.findViewById(R.id.addPostBtn);
    }

    //Add post with an auto-generate an ID
    public void addPost() {
        String title, description, location, requiredLevel;
        title = postTitle.getText().toString();
        description = postDesc.getText().toString();
        location = postLocation.getText().toString();
        requiredLevel = this.requiredLevel;

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(getContext(), "Please enter a valid title", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            Toast.makeText(getContext(), "Provide post descritption", Toast.LENGTH_LONG).show();
            return;
        }

        //Setting the post document
        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        data.put("description", description);
        data.put("location", location);
        data.put("required level", requiredLevel);
        data.put("timestamp", new Timestamp(new Date()));

        db.collection("posts")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    Log.d(TAG, "Document created succesfully ");
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding document", e);
                    Toast.makeText(getContext(), "DB error", Toast.LENGTH_LONG).show();
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        //Toast.makeText(getContext(), level[position], Toast.LENGTH_LONG).show();
        this.requiredLevel = level[position];
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}