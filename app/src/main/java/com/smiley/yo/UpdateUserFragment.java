package com.smiley.yo;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateUserFragment extends Fragment {

    EditText Edituser_phone,Edituser_desc,Edituser_location,Edituser_email,Edituser_name;
    Button btnUpdateUser;
    FirebaseFirestore db;

    //

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateUserFragment newInstance(String param1, String param2) {
        UpdateUserFragment fragment = new UpdateUserFragment();
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
        View v= inflater.inflate(R.layout.fragment_update_user, container, false);
        db=FirebaseFirestore.getInstance();
        InitDataUser(v);

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        //display data
        if(user != null){
            Log.d(TAG,"ON CREATE: "+ user.getEmail());
            if(user.getEmail() != null){
                Edituser_email.setText(user.getEmail());
            }
            DocumentReference documentReference=db.collection("User").document(user.getUid());
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    Edituser_name.setText(value.getString("FullName"));
                    Edituser_location.setText(value.getString("Location"));
                    Edituser_phone.setText(value.getString("Phone"));
                    Edituser_desc.setText(value.getString("Description"));

                }
            });
        }

        btnUpdateUser.setOnClickListener(v1 -> updateProfile());

        return v;
    }

    private void InitDataUser(View view) {
        btnUpdateUser=view.findViewById(R.id.btn_update_save);
        Edituser_email=view.findViewById(R.id.user_email_profile);
        Edituser_location=view.findViewById(R.id.user_location_profile);
        Edituser_desc=view.findViewById(R.id.user_desc_profile);
        Edituser_name=view.findViewById(R.id.user_name_profile);
        Edituser_phone=view.findViewById(R.id.user_phone_profile);
    }

    private void updateProfile() {

        String UserName,UserPhone,UserLocation,UserDesc ;
        UserName = Edituser_name.getText().toString();
        String UserEmail = Edituser_email.getText().toString();
        UserPhone = Edituser_phone.getText().toString();
        UserLocation = Edituser_location.getText().toString();
        UserDesc = Edituser_desc.getText().toString();
        //Update
        db=FirebaseFirestore.getInstance();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        user.updateEmail(UserEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            DocumentReference dc=db.collection("User").document(user.getUid());
                            Map<String, Object> dataUser = new HashMap<>();
                            dataUser.put("FullName", UserName);
                            dataUser.put("Phone", UserPhone);
                            dataUser.put("Location", UserLocation);
                            dataUser.put("Description", UserDesc);
                            dataUser.put("email", UserEmail);
                            dc.update(dataUser)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(@NonNull Void unused) {
                                    Toast.makeText(getContext(), "Edit Success", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.d(TAG, "User email address updated.");

                        }
                    }
                });


        //Update password --> reset password
        //Verify EMAIL Account





    }




}