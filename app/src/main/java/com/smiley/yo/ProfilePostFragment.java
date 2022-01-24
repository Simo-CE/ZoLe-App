package com.smiley.yo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilePostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilePostFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProfilePostFragment() {
        // Required empty public constructor
    }

    public static ProfilePostFragment newInstance(String param1, String param2) {
        ProfilePostFragment fragment = new ProfilePostFragment();
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

    private RecyclerView recyclerPost;
    private ArrayList<Post> Listpost;
    private ProfilePostAdapter RVAdapter;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profilepost, container, false);
        recyclerPost = v.findViewById(R.id.recyclerpost);
        db = FirebaseFirestore.getInstance();
        // creating our new array list
        Listpost = new ArrayList<>();
        recyclerPost.setHasFixedSize(true);
        recyclerPost.setLayoutManager(new LinearLayoutManager(getContext()));
        RVAdapter = new ProfilePostAdapter(getContext(),Listpost);
        recyclerPost.setAdapter(RVAdapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("posts")
                .whereEqualTo("userId",user.getUid())
                .addSnapshotListener((value, error) -> {
                    if(error != null){
                        Log.e("Firestore error",error.getMessage());
                        return;
                    }
                    for(DocumentChange dc:value.getDocumentChanges()){
                        if(dc.getType()== DocumentChange.Type.ADDED){
                            Listpost.add(dc.getDocument().toObject(Post.class));
                        }
                        RVAdapter.notifyDataSetChanged();
                    }
                });
        return v;
    }
}