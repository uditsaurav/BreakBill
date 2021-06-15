package com.example.dude.projectworkz1;

import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView =findViewById(R.id.SplashimageView);
        Glide.with(this)
                .load(R.drawable.bb)
                .into(imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }

        }, SPLASH_TIME_OUT);
    }
}
