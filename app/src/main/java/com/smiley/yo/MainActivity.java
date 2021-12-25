package com.smiley.yo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Login
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText aemail, apassword;
    private Button signin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hiding the appbar
        getSupportActionBar().hide();

        //Inializing firebase authentication
        mAuth = FirebaseAuth.getInstance();
        initializeUI();
        signin.setOnClickListener(v -> loginUserAccount());
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        if (currentUser != null) {
            reload();
        }
    }

    private void loginUserAccount() {
        String email, password;
        email = aemail.getText().toString();
        password = apassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);

                        /*Intent HomeIntent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(HomeIntent);*/
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void reload() {
    }

    private void updateUI(FirebaseUser user) {
    }

    private void initializeUI() {
        aemail = findViewById(R.id.signin_email);
        apassword = findViewById(R.id.signin_password);
        signin = findViewById(R.id.signin);
    }

    private void sendEmailVerification() {
        // Send verification email
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, task -> {
                    // Email sent
                });
    }

    //Intents
    /*public void loginActivity(View view) {
        Intent HomeIntent = new Intent(this, HomeActivity.class);
        startActivity(HomeIntent);
    }*/

    public void RecoverPassword(View view) {
        Intent recoverPassIntent = new Intent(this, RecoverActivity.class);
        startActivity(recoverPassIntent);
    }

    public void SignupActivity(View view) {
        Intent signUpIntent = new Intent(this, SignupActivity.class);
        startActivity(signUpIntent);
    }

    //sing out user
    //FirebaseAuth.getInstance().signOut();

}