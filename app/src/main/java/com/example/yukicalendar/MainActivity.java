package com.example.yukicalendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openApp();
                finish();
            }
        }, 1000);
    }

    public void openApp() {
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
    }

}