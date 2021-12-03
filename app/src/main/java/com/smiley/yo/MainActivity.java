package com.smiley.yo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginActivity(View view) {
        Intent HomeIntent = new Intent(this, HomeActivity.class);
        startActivity(HomeIntent);
    }

    public void RecoverPassword(View view){
        Intent recoverPassIntent = new Intent(this,RecoverActivity.class);
        startActivity(recoverPassIntent);
    }

    public void SignupActivity(View view){
        Intent signUpIntent = new Intent(this,SignupActivity.class);
        startActivity(signUpIntent);
    }

}