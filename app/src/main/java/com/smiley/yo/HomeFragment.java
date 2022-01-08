package com.smiley.yo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Post> postArrayList;
    PostAdapter postAdapter;
    FirebaseFirestore db;
    private static final String TAG = "Retrieving posts";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.app_bar,menu);
        super.onCreateOptionsMenu(menu,inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.searchMenu){
            Toast.makeText(getContext(), "Search", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.notificationMenu){
            Toast.makeText(getContext(), "Notification", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.logoutMenu){

            FirebaseAuth.getInstance().signOut();
            Intent SignIntent = new Intent(getContext(), MainActivity.class);
            startActivity(SignIntent);
            Toast.makeText(getContext(), "logout", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
    private Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
      /*  toolbar = (Toolbar)view.findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);*/

        recyclerView = view.findViewById(R.id.home_post_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseFirestore.getInstance();
        postArrayList = new ArrayList<Post>();
        postAdapter = new PostAdapter(getContext(), postArrayList);

        recyclerView.setAdapter(postAdapter);

        PostsListener();

        return view;
    }

    private void PostsListener() {
        //Original
        /*db.collection("posts")
                .addSnapshotListener((value, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    //List<String> posts = new ArrayList<>();
                    for (DocumentChange doc : value.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED){
                            postArrayList.add(doc.getDocument().toObject(Post.class));
                        }
                    }
                    postAdapter.notifyDataSetChanged();
                });*/

        //CollectionReference usersRef = db.collection("Users");
        //DocumentReference usersRef = db.collection("Users").document();

        //Users list
        /*db.collection("posts")
                .whereEqualTo("userId", String.valueOf(users.get(1)))
                .addSnapshotListener((value, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if (e == null) {
                        //Log.w("-------------------------userRef ID", String.valueOf(users.get(1)));
                        Log.w("-------------------------userRef ID", users.get(1));
                    }
                    for (DocumentChange doc : value.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            postArrayList.add(doc.getDocument().toObject(Post.class));
                        }
                    }
                    postAdapter.notifyDataSetChanged();
                });*/





        /*//List<String> users = new ArrayList<>();
        List<Map<String, Object>> users = new ArrayList<>();
        List<String> list = new ArrayList<>();
        //getting posts
        db.collection("posts")
                .addSnapshotListener((value, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    for (DocumentChange doc : value.getDocumentChanges()) {
                        //getting the users of the userId
                        db.collection("Users")
                                .whereEqualTo(FieldPath.documentId(), "userId")
                                //.whereEqualTo(FieldPath.documentId(), "userId")
                                .get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            Log.d("______UserId", document.getId());
                                            users.add(document.getData());
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                });
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            postArrayList.add(doc.getDocument().toObject(Post.class));
                        }
                    }
                    postAdapter.notifyDataSetChanged();
                });*/


        /*
        * Adding posts document under the user id
        * retrieve documents of posts with users.foreach
        * which would result in retrieving each users posts
        * so I need to figure out how to add documents of posts under the current user id
        * */
        /*
        * or I can continue with what I'm doing rn which getting the users in an array lists
        * and then retreive the posts of each user in the users array
        * so I need to get each document on its own and not all the documents once
        * */
        ArrayList<String> users = new ArrayList<>();
        db.collection("Users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("______Retrieving users", document.getId() + " => " + document.getData());
                            //users.add(document.getId());
                            users.addAll(Collections.singleton(document.getId()));
                            //users.forEach(user -> Log.d("______users item", String.valueOf(user)));
                            //Log.d("______users item", String.valueOf(users.get(0)));
                        }
                        Log.d("______users size", String.valueOf(users.size()));
                        users.forEach(user -> Log.d("_________users", user));
                        users.forEach(user ->
                                db.collection("posts")
                                        .whereEqualTo("userId", user)
                                        .addSnapshotListener((value, e) -> {
                                            if (e != null) {
                                                Log.w(TAG, "_______________Listen failed.", e);
                                                return;
                                            }
                                            Log.d("________current user", user);
                                            for (DocumentChange doc : value.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    postArrayList.add(doc.getDocument().toObject(Post.class));
                                                }
                                            }
                                            postAdapter.notifyDataSetChanged();
                                        })
                        );
                    } else {
                        Log.d(TAG, "Error getting users: ", task.getException());
                    }
                });
    }
}