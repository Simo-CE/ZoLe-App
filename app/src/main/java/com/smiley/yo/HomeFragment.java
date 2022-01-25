package com.smiley.yo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smiley.yo.databinding.ActivityMainBinding;
import com.smiley.yo.databinding.FragmentServiceItemBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements PostAdapter.OnPostListener {

    ImageButton sendMail;
    TextView emailAddress, emailSubject;
    RecyclerView recyclerView;
    ArrayList<Post> postArrayList;
    PostAdapter postAdapter;
    FirebaseFirestore db;
    private DatabaseReference dbRef;
    private static final String TAG = "Retrieving posts";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.home_post_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseFirestore.getInstance();
        postArrayList = new ArrayList<Post>();
        postAdapter = new PostAdapter(getContext(), postArrayList, this);

        recyclerView.setAdapter(postAdapter);

        PostsListener();

        //dbRef = FirebaseFirestore.getInstance().getRefer
        initializeUI(view);
        return view;
    }

    private void initializeUI(View view) {
        sendMail = view.findViewById(R.id.home_post_send);
        emailAddress = view.findViewById(R.id.home_post_email);
        emailSubject = view.findViewById(R.id.home_post_title);
    }

    private void PostsListener() {
        //Original
        db.collection("posts")
                .addSnapshotListener((value, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    //List<String> posts = new ArrayList<>();
                    for (DocumentChange doc : value.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            postArrayList.add(doc.getDocument().toObject(Post.class));
                        }
                    }
                    postAdapter.notifyDataSetChanged();
                });
    }

    @Override
    public void onPostClick(int position) {
        Log.d(TAG, "onPostClick: Post Clicked " + position);

        sendMail.setOnClickListener(view -> {

            Intent mail = new Intent(Intent.ACTION_SENDTO);
            mail.setData(Uri.parse("mailTo: "));

            if (mail.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(mail);
            }
            //else Toast.makeText(HomeFragment.this, "No suitable mail app", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(getContext(), "No suitable mail app", Toast.LENGTH_SHORT).show();
            }

        });
    }

    /*sendMail.setOnClickListener(view -> {
            String email = emailAddress.getText().toString();
            String subject = emailSubject.getText().toString();

            Intent mail = new Intent(Intent.ACTION_SENDTO);
            mail.setData(Uri.parse("mailTo: "));
            mail.putExtra(Intent.EXTRA_EMAIL, email);
            mail.putExtra(Intent.EXTRA_SUBJECT, subject);

            if (mail.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(mail);
            }
            //else Toast.makeText(HomeFragment.this, "No suitable mail app", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(getContext(), "No suitable mail app", Toast.LENGTH_SHORT).show();
            }

        });*/
}