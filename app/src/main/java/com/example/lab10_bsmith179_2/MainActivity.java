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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageView ivBird = findViewById(R.id.ivBird);
        Button btStart = findViewById(R.id.btStart);

        ivBird.setBackgroundResource(R.drawable.animation);
        birdAnimation = (AnimationDrawable) ivBird.getBackground();

        final Animation floating = AnimationUtils.loadAnimation(this, R.anim.floating);

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birdAnimation.start();

                // Find center and move the bird to pause there
                int screenWidth = getResources().getDisplayMetrics().widthPixels;
                float centerX = (float) screenWidth / 2 - ivBird.getWidth() / 2;
                TranslateAnimation moveToCenter = new TranslateAnimation(
                        0, centerX,
                        0, 0
                );
                moveToCenter.setDuration(8000);
                moveToCenter.setFillAfter(true);

                // Create set
                android.view.animation.AnimationSet animationSet = new android.view.animation.AnimationSet(false);
                animationSet.addAnimation(floating);
                animationSet.addAnimation(moveToCenter);

                //Start combined animations
                ivBird.startAnimation(animationSet);

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //Calculations post-pause
                        int screenWidth = getResources().getDisplayMetrics().widthPixels;
                        float initialLeftMargin = ivBird.getLeft();
                        float finalXPosition = screenWidth - ivBird.getWidth() - initialLeftMargin;

                        TranslateAnimation moveToRight = new TranslateAnimation(
                                centerX, finalXPosition,
                                0, 0
                        );

                        moveToRight.setDuration(5500);
                        moveToRight.setFillAfter(true);

                        //Create animation set for post-pause
                        android.view.animation.AnimationSet animationSet2 = new android.view.animation.AnimationSet(false);
                        animationSet2.addAnimation(floating);
                        animationSet2.addAnimation(moveToRight);

                        animationSet2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                ivBird.clearAnimation();  // Stop the animations
                                birdAnimation.stop();
                                birdAnimation.selectDrawable(0);
                            }
                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });

                        ivBird.startAnimation(animationSet2);
                    }
                }, 8700);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}