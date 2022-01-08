package com.smiley.yo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignUp Activity Tag: ";

    private EditText aname, aemail, apassword;
    private Button signup;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //get main_activity: login intent
        Intent signUpIntent = getIntent();

        //Hiding the appbar
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        initializeUI();
        signup.setOnClickListener(v -> createAccount());
    }

    private void initializeUI() {
        aname = findViewById(R.id.signup_name);
        aemail = findViewById(R.id.signup_email);
        apassword = findViewById(R.id.signup_password);
        signup = findViewById(R.id.signup);
    }

    private void createAccount() {
        String name, email, password;
        name = aname.getText().toString();
        email = aemail.getText().toString();
        password = apassword.getText().toString();

        //check empty field
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Name required", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Email required", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter a valid password!", Toast.LENGTH_LONG).show();
            return;
        }
        //end check empty field
        db= FirebaseFirestore.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        Toast.makeText(SignupActivity.this, "Account created successfully",
                                Toast.LENGTH_SHORT).show();
                        //Extract user
                        FirebaseUser user = mAuth.getCurrentUser();
                        //Store in Users collection
                        DocumentReference df = db.collection("users").document(user.getUid());
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("fullname", name);
                        userInfo.put("email", email);
                        userInfo.put("isUser", "1");
                        //Add to firestore
                        df.set(userInfo).addOnSuccessListener(documentReference -> {
                            Log.d(TAG, "User stored succesfully ");
                        }).addOnFailureListener(e -> {
                            Log.w(TAG, "Error adding User", e);
                        });
                        updateUI(user);

                        startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                        finish();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignupActivity.this, "Registration failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
    }

    public void BackToLogin(View view) {
        Intent backToLoginIntent = new Intent(this, MainActivity.class);
        startActivity(backToLoginIntent);
    }
}