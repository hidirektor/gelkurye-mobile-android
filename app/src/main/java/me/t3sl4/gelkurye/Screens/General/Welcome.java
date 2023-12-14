package me.t3sl4.gelkurye.Screens.General;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.navigation.NavigationView;

import me.t3sl4.gelkurye.R;

public class Welcome extends AppCompatActivity {
    private NavigationView hamburgerMenu;
    private ImageView navigationButton;
    private ConstraintLayout mainLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        hamburgerMenu = findViewById(R.id.hamburgerMenu);
        navigationButton = findViewById(R.id.navigationButton);
        mainLayout = findViewById(R.id.mainLayout);

        navigationButton.setOnClickListener(v -> {
            showNavigationViewWithAnimation();
        });

        mainLayout.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                hideNavigationViewWithAnimation();
                return true;
            }
            return false;
        });
    }

    private void showNavigationViewWithAnimation() {
        Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        hamburgerMenu.setVisibility(View.VISIBLE);
        hamburgerMenu.startAnimation(slideIn);
    }

    private void hideNavigationViewWithAnimation() {
        Animation slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        slideOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                hamburgerMenu.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        hamburgerMenu.startAnimation(slideOut);
    }
}
