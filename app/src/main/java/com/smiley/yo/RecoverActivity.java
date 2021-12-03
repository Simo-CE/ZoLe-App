package com.smiley.yo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RecoverActivity extends AppCompatActivity {
    //public static final String EXTRA_MESSAGE = "com.smiley.yo.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);
        Intent recoverPassIntent = getIntent();
    }
}