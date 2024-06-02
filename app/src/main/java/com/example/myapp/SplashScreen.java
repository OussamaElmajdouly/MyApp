package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {
    ImageView imagelogo;
    ProgressBar bar;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //1.get view by id
        //Imageview
        imagelogo =findViewById(R.id.logo);
        //progressbar
        bar=findViewById(R.id.progressBar);
        //2.get animation
       anim =AnimationUtils.loadAnimation(SplashScreen.this,R.anim.logo_anim);
        //apply animation
        imagelogo.startAnimation(anim);
        //4. apply delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent I = new Intent(SplashScreen.this, login.class);
               startActivity(I);
               finish();
            }
        }, 5000);

    }
}