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

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileAddService#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileAddService extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileAddService() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddService.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileAddService newInstance(String param1, String param2) {
        ProfileAddService fragment = new ProfileAddService();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private FirebaseFirestore db;
    private static final String TAG = "ProfileFragment LOG";
    private EditText NameSer,PriceSer,DescSer;
    private Button btnadd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_service, container, false);

        //Initializing cloud firestore
        db = FirebaseFirestore.getInstance();
        initializeUI(v);
        btnadd.setOnClickListener(view -> addService());
        return v;
    }

    private void initializeUI(View v) {
        NameSer= v.findViewById(R.id.nameServ);
        PriceSer= v.findViewById(R.id.PriceServ);
        DescSer= v.findViewById(R.id.DescServ);
        btnadd = v.findViewById(R.id.btnadd);
    }

    //Add post with an auto-generate an ID
    public void addService() {
        String ptitle, pdescription,pprice;
        ptitle = NameSer.getText().toString();
        pdescription = DescSer.getText().toString();
        pprice = PriceSer.getText().toString();

        if (TextUtils.isEmpty(ptitle)) {
            Toast.makeText(getContext(), "Please enter a valid title", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pdescription)) {
            Toast.makeText(getContext(), "Provide service descritption", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pprice)) {
            Toast.makeText(getContext(), "Provide service price", Toast.LENGTH_LONG).show();
            return;
        }

        //Setting the post document
        Map<String, Object> data = new HashMap<>();
        data.put("title", ptitle);
        data.put("price", pprice);
        data.put("desc", pdescription);

        db.collection("services")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    Log.d(TAG, "Document created succesfully ");
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }
}