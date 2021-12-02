package com.smiley.yo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //public static final String EXTRA_MESSAGE = "com.smiley.yo.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginActivity(View view) {

        Intent intent = new Intent(this, ServicesActivity.class);

        /*EditText editText = (EditText) findViewById(R.id.username);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);*/

        startActivity(intent);
    }

    public void RecoverPassword(View view){
        Intent intent = new Intent(this,RecoverActivity.class);
        startActivity(intent);
    }

    public void SignupActivity(View view){
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }

}