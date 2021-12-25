package com.smiley.yo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText aname, aemail, apassword;
    private Button signup;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //get main_activity: login intent
        Intent signUpIntent = getIntent();

        //Hiding the appbar
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        signup.setOnClickListener(v -> createAccount());
    }

    private void createAccount() {
        String name, email, password;
        name = aname.getText().toString();
        email = aemail.getText().toString();
        password = apassword.getText().toString();

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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);

                        /*Intent HomeIntent = new Intent(this, HomeActivity.class);
                        startActivity(HomeIntent);*/
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));

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

    private void initializeUI() {
        aname = findViewById(R.id.signup_name);
        aemail = findViewById(R.id.signup_email);
        apassword = findViewById(R.id.signup_password);
        signup = findViewById(R.id.signup);
    }
}