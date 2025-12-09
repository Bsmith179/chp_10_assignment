package com.example.lab10_bsmith179_2;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Tween extends AppCompatActivity {

    ImageView ivTween;
    Button btStart, btStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tween);

        ivTween = findViewById(R.id.ivBird);
        btStart = findViewById(R.id.btStart);
        btStop = findViewById(R.id.btStop);

        final Animation startLoading = AnimationUtils.loadAnimation(this, R.anim.start_loading);
        final Animation stopLoading = AnimationUtils.loadAnimation(this, R.anim.finish_loading);

        ivTween.startAnimation(startLoading);

        btStart.setOnClickListener(v -> {
            ivTween.startAnimation(startLoading);
        });

        btStop.setOnClickListener(v -> {
            ivTween.clearAnimation();
            ivTween.startAnimation(stopLoading);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}