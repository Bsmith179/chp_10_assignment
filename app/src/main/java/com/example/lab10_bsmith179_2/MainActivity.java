/**
 * Brigitte Smith
 * Lab 10 - Loading Animation Mockup
 * On opening this app displays a stationary image and a button. Users will press the Retry button
 * to start the loading animation, moving the now-flying bird to the other side of the screen,
 * simulating the movement of a loading bar. Clicking the button a second time will restart the
 * animation and play it again.
 * 12/14/2025
 */


package com.example.lab10_bsmith179_2;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;

public class MainActivity extends AppCompatActivity {

    private ImageView ivBird;
    private Button btStart;
    private ConstraintLayout bottomContainer;
    private AnimationDrawable birdAnimation;
    private float startX, finalX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivBird = findViewById(R.id.ivBird);
        btStart = findViewById(R.id.btStart);
        bottomContainer = findViewById(R.id.bottomContainer);

        // Keeps bird 150x150 dp
        LayoutParams params = (LayoutParams) ivBird.getLayoutParams();
        params.width = (int) dpToPx(150);
        params.height = (int) dpToPx(150);
        ivBird.setLayoutParams(params);

        // Initialize wings flapping
        ivBird.setBackgroundResource(R.drawable.animation);
        birdAnimation = (AnimationDrawable) ivBird.getBackground();

        // Calculate start and end positions
        bottomContainer.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        bottomContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        startX = ivBird.getX();
                        finalX = bottomContainer.getWidth() - ivBird.getWidth() - dpToPx(16);
                    }
                });

        // Recalculate on rotation
        bottomContainer.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            startX = ivBird.getX();
            finalX = bottomContainer.getWidth() - ivBird.getWidth() - dpToPx(16);
        });

        btStart.setOnClickListener(v -> startBirdAnimation());
    }

    private void startBirdAnimation() {
        // Stop previous animations
        ivBird.clearAnimation();
        if (birdAnimation != null && birdAnimation.isRunning()) birdAnimation.stop();

        // Reset bird position
        ivBird.setX(startX);
        ivBird.setTranslationY(0);

        // Restart flapping
        ivBird.setBackgroundResource(R.drawable.animation);
        birdAnimation = (AnimationDrawable) ivBird.getBackground();
        birdAnimation.start();

        // Move right
        float deltaX = finalX - startX;
        TranslateAnimation moveRight = new TranslateAnimation(0, deltaX, 0, 0);
        moveRight.setDuration(18000);
        moveRight.setFillAfter(true);

        // Floating
        TranslateAnimation floating = new TranslateAnimation(0, 0, 0, -dpToPx(20));
        floating.setDuration(800);
        floating.setRepeatMode(Animation.REVERSE);
        floating.setRepeatCount(Animation.INFINITE);
        floating.setFillAfter(true);

        // Create animation set
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(moveRight);
        set.addAnimation(floating);

        moveRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Stop all animations and set final position
                ivBird.clearAnimation();
                if (birdAnimation.isRunning()) birdAnimation.stop();
                ivBird.setX(finalX);
                ivBird.setTranslationY(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        // Start full animation
        ivBird.startAnimation(set);
    }

    private float dpToPx(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return dp * metrics.density;
    }
}
