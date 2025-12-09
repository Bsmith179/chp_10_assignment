package com.example.lab10_bsmith179_2;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    AnimationDrawable birdAnimation;
    ImageView ivBird;
    Button btStart, btStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageView ivBird = findViewById(R.id.ivBird);
        Button btStart = findViewById(R.id.btStart);
        Button btStop = findViewById(R.id.btStop);

        ivBird.setBackgroundResource(R.drawable.animation);
        birdAnimation = (AnimationDrawable) ivBird.getBackground();

        final Animation startLoading = AnimationUtils.loadAnimation(this, R.anim.start_loading);
        final Animation stopLoading = AnimationUtils.loadAnimation(this, R.anim.finish_loading);


        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birdAnimation.start();

                // Find center and move the bird to stay there
                int screenWidth = getResources().getDisplayMetrics().widthPixels;
                float centerX = (float) screenWidth / 2 - ivBird.getWidth() / 2;
                TranslateAnimation moveToCenter = new TranslateAnimation(
                        0, centerX,
                        0, 0
                );
                moveToCenter.setDuration(4000);
                moveToCenter.setFillAfter(true);

                // Create set
                android.view.animation.AnimationSet animationSet = new android.view.animation.AnimationSet(false);
                animationSet.addAnimation(startLoading);
                animationSet.addAnimation(moveToCenter);

                //Start combined animations
                ivBird.startAnimation(animationSet);
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivBird.clearAnimation();
                ivBird.startAnimation(stopLoading);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}