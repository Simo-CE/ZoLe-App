package com.smiley.yo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements PostAdapter.OnPostListener {

    ImageButton sendMail;
    TextView emailAddress, emailSubject;
    CardView searchField;
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

        SearchView searchView = view.findViewById(R.id.search_post);
        recyclerView = view.findViewById(R.id.home_post_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });

        /* mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                firebaseUserSearch(searchText);

            }
        });*/


        db = FirebaseFirestore.getInstance();
        postArrayList = new ArrayList<Post>();
        postAdapter = new PostAdapter(getContext(), postArrayList, this);

        recyclerView.setAdapter(postAdapter);

        PostsListener();

        //dbRef = FirebaseFirestore.getInstance().getRefer
        initializeUI(view);

        //Scrolling behaviour
        final int[] state = new int[1];
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                state[0] = newState;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && (state[0] == 0 || state[0] == 2)) {
                    searchField.animate().alpha(0.0f).setDuration(2000);
                    searchField.setVisibility(View.GONE);
                } else if (dy < -10) {
                    searchField.animate().alpha(1.0f).setDuration(2000);
                    searchField.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    private void initializeUI(View view) {
        sendMail = view.findViewById(R.id.home_post_send);
        emailAddress = view.findViewById(R.id.home_post_email);
        emailSubject = view.findViewById(R.id.home_post_title);

        searchField = view.findViewById(R.id.searchField);
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
    }*/

    public void processSearch(String s) {
        ArrayList<Post> listPost = new ArrayList<>();

        for (Post p : postArrayList) {
            if (p.getTitle().toLowerCase().contains(s.toLowerCase())) {
                listPost.add(p);
            }
        }

        PostAdapter adapterpost = new PostAdapter(getContext(), listPost, this);
        recyclerView.setAdapter(adapterpost);
    }
}