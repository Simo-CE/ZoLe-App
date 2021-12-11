package com.smiley.yo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/*
api title/desc:
    zole api
public key:
    dkzzktzd
private key:
    5d0e0984-e261-42d6-b7c2-1cbd9c9e4056
current ip address:
    105.67.3.13

app id:
    zole-vipwd

db name:
    ZoLeDB
* */

//Login
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