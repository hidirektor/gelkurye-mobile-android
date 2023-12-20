package me.t3sl4.gelkurye.Screens.Hamburger;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.navigation.NavigationView;

import me.t3sl4.gelkurye.R;

public class FAQ extends AppCompatActivity {
    private NavigationView faqList;
    private LinearLayout question1;
    private LinearLayout answer1;


    private Animation slideDownAnimation;
    private Animation slideUpAnimation;

    private boolean isAnswerVisible = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        faqList = findViewById(R.id.faqMainHeader);

        View mainView = faqList.getHeaderView(0);
        question1 = mainView.findViewById(R.id.question1);
        answer1 = mainView.findViewById(R.id.answer1);

        slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        question1.setOnClickListener(v -> toggleAnswerVisibility());
    }

    private void toggleAnswerVisibility() {
        if (isAnswerVisible) {
            answer1.startAnimation(slideUpAnimation);
            answer1.setVisibility(View.GONE);
            isAnswerVisible = false;
        } else {
            answer1.startAnimation(slideDownAnimation);
            answer1.setVisibility(View.VISIBLE);
            isAnswerVisible = true;
        }
    }
}
