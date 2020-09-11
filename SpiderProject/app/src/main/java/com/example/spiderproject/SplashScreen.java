package com.example.spiderproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spiderproject.home.MainActivity;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toStartScreen = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(toStartScreen);
                finish();
            }
        }, SPLASH_DURATION);

    }
}
