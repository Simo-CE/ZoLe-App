package com.smiley.yo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddServiceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddServiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddServiceFragment newInstance(String param1, String param2) {
        AddServiceFragment fragment = new AddServiceFragment();
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
    private FirebaseFirestore db;
    private static final String TAG = "ProfileFragment LOG";
    private EditText NameService,PriceService,DescService;
    private Button btnaddService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_service, container, false);
        //Initializing cloud firestore
        db = FirebaseFirestore.getInstance();
        initializeUI(v);
        btnaddService.setOnClickListener(view -> addService());
        return v;
    }
    private void initializeUI(View v) {
        NameService= v.findViewById(R.id.nameServ);
        PriceService= v.findViewById(R.id.PriceServ);
        DescService= v.findViewById(R.id.DescServ);
        btnaddService = v.findViewById(R.id.addServiceBtn);
    }

    //Add service with an auto-generate an ID
    public void addService() {
        String stitle, sdescription,sprice;
        stitle = NameService.getText().toString();
        sdescription = DescService.getText().toString();
        sprice = PriceService.getText().toString();

        if (TextUtils.isEmpty(stitle)) {
            Toast.makeText(getContext(), "Please enter a valid title", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(sdescription)) {
            Toast.makeText(getContext(), "Provide service descritption", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(sprice)) {
            Toast.makeText(getContext(), "Provide service price", Toast.LENGTH_LONG).show();
            return;
        }
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //Setting the service document
        Map<String, Object> data = new HashMap<>();
        data.put("title", stitle);
        data.put("price", sprice);
        data.put("desc", sdescription);

        db.collection("services")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    Log.d(TAG, "Document created succesfully ");
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }
}