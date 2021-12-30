package com.smiley.yo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSerFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileSerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileSerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileSerFragment newInstance(String param1, String param2) {
        ProfileSerFragment fragment = new ProfileSerFragment();
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
    private Button btnadd1;
    private RecyclerView recyclerView1;
    private ArrayList<Service> List;
    private ProfileAdapter RVAdapter;
    private FirebaseFirestore db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profileser, container, false);
        recyclerView1=v.findViewById(R.id.recyclerView1);
        db = FirebaseFirestore.getInstance();
        // creating our new array list
        List = new ArrayList<>();
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        // adding our array list to our recycler view adapter class.
        RVAdapter = new ProfileAdapter(List, getContext());
        // setting adapter to our recycler view.
        recyclerView1.setAdapter(RVAdapter);
        // below line is use to get the data from Firebase Firestore.
        // previously we were saving data on a reference of Courses
        // now we will be getting the data from the same reference.
        btnadd1 = v.findViewById(R.id.btnaddser);
        btnadd1.setOnClickListener(view -> addService());
        db.collection("services")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }
                        for(DocumentChange dc:value.getDocumentChanges()){
                            if(dc.getType()== DocumentChange.Type.ADDED){
                                List.add(dc.getDocument().toObject(Service.class));
                            }
                            RVAdapter.notifyDataSetChanged();
                        }
                    }
                });

        return v;
    }

    private void addService() {
        FragmentTransaction fr=getFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_profileser,new ProfileAddService());
        fr.addToBackStack(null);
        fr.commit();
    }

   /*  DatabaseReference database1;
    Service service;
    FirebaseDatabase firebaseDatabase;
    List<Service> services;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_profileser, container, false);


        Button btnadd;

        {
            assert view != null;
            btnadd = (Button) view.findViewById(R.id.btnaddser);
        }

        EditText NameSer= view.findViewById(R.id.nameServ);
        EditText PriceSer= view.findViewById(R.id.PriceServ);
        EditText DescSer= view.findViewById(R.id.DescServ);

        firebaseDatabase = FirebaseDatabase.getInstance();
       // database1 = firebaseDatabase.getReference("Services");

        services = new ArrayList<>();


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service_title =  NameSer.getText().toString();
                String service_price = PriceSer.getText().toString();
                String desc = DescSer.getText().toString();
                service.setServiceTitle(service_title);
                service.setServicePrice(service_price);
                service.setServiceDesc(desc);
                database1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // inside the method of on Data change we are setting
                        // our object class to our database reference.
                        // data base reference will sends data to firebase.
                        String id = database1.push().getKey();
                        Service service = new Service();
                        DatabaseReference newRef = firebaseDatabase.child("Service").push();
                        newRef.setValue(service);
                        database1.child(id).setValue(service);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });
            }


        });

        return view;
    }
    private void addDatatoFirebase() {

    } */

}