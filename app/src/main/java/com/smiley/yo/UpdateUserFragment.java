package com.smiley.yo;

import static android.app.appsearch.AppSearchResult.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateUserFragment extends Fragment {

    EditText Edituser_phone,Edituser_desc,Edituser_location,Edituser_name;
    CircleImageView Edituser_img;
    Button btnUpdateUser;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();


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
        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        
        if(user != null){
            DocumentReference documentReference=db.collection("Users").document(user.getUid());
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    Edituser_name.setText(value.getString("FullName"));
                    Edituser_location.setText(value.getString("Location"));
                    Edituser_phone.setText(value.getString("Phone"));
                    Edituser_desc.setText(value.getString("Description"));

                }

            });

            StorageReference imageref=storageReference.child("image/"+user.getUid()+".jpeg");
            imageref.getDownloadUrl().addOnSuccessListener((OnSuccessListener<? super Uri>) (uri)->{
               Picasso.get().load(uri).into(Edituser_img);
            });


        }





        Edituser_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });


        btnUpdateUser.setOnClickListener(v1 -> updateProfile());
        return v;
    }

    // GetContent creates an ActivityResultLauncher<String> to allow you to pass
// in the mime type you'd like to allow the user to select
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    if(uri != null){
                        Edituser_img.setImageURI(uri);
                        filePath=uri;
                    }
                }
            });
    private void InitDataUser(View view) {
        btnUpdateUser=view.findViewById(R.id.btn_update_save);
        Edituser_location=view.findViewById(R.id.user_location_profile);
        Edituser_desc=view.findViewById(R.id.user_desc_profile);
        Edituser_name=view.findViewById(R.id.user_name_profile);
        Edituser_phone=view.findViewById(R.id.user_phone_profile);
        Edituser_img=view.findViewById(R.id.user_image_profile);

    }

    private void updateProfile() {

        String UserName,UserPhone,UserLocation,UserDesc ;
        UserName = Edituser_name.getText().toString();
        UserPhone = Edituser_phone.getText().toString();
        UserLocation = Edituser_location.getText().toString();
        UserDesc = Edituser_desc.getText().toString();
        //Update
        db=FirebaseFirestore.getInstance();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        /*assert user != null;
       user.updateEmail(UserEmail)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {*/
                            DocumentReference dc=db.collection("Users").document(user.getUid());
                            Map<String, Object> dataUser = new HashMap<>();
                            dataUser.put("FullName", UserName);
                            dataUser.put("Phone", UserPhone);
                            dataUser.put("Location", UserLocation);
                            dataUser.put("Description", UserDesc);
                            dc.update(dataUser);

        uploadProfileImage();

    }

    private void uploadProfileImage() {
        if(filePath != null){
            StorageReference reference=storage.getReference().child("image/"+user.getUid()+".jpeg");
            reference.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


}