package me.t3sl4.gelkurye.Screens.Hamburger;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.navigation.NavigationView;
import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Utils;

public class FAQ extends AppCompatActivity {
    private ImageView backButton;
    private NavigationView faqList;


    private LinearLayout question1;
    private LinearLayout answer1;
    private LinearLayout question2;
    private LinearLayout answer2;
    private LinearLayout question3;
    private LinearLayout answer3;
    private LinearLayout question4;
    private LinearLayout answer4;

    private Animation slideDownAnimation;
    private Animation slideUpAnimation;

    private boolean isFirstAnswerVisible = false;
    private boolean isSecondAnswerVisible = false;
    private boolean isThirthAnswerVisible = false;
    private boolean isFourthAnswerVisible = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamburger_faq);

        Utils.getInstance().getNavigationBar().hideNavigationBar(this);

        backButton = findViewById(R.id.backButtonImage);
        faqList = findViewById(R.id.faqMainHeader);

        View mainView = faqList.getHeaderView(0);
        question1 = mainView.findViewById(R.id.question1);
        answer1 = mainView.findViewById(R.id.answer1);
        question2 = mainView.findViewById(R.id.question2);
        answer2 = mainView.findViewById(R.id.answer2);
        question3 = mainView.findViewById(R.id.question3);
        answer3 = mainView.findViewById(R.id.answer3);
        question4 = mainView.findViewById(R.id.question4);
        answer4 = mainView.findViewById(R.id.answer4);

        slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        backButton.setOnClickListener(v -> finish());
        question1.setOnClickListener(v -> toggleAnswerVisibility(answer1));
        question2.setOnClickListener(v -> toggleAnswerVisibility(answer2));
        question3.setOnClickListener(v -> toggleAnswerVisibility(answer3));
        question4.setOnClickListener(v -> toggleAnswerVisibility(answer4));
    }

    private void toggleAnswerVisibility(LinearLayout answerLayout) {
        if (answerLayout.getVisibility() == View.VISIBLE) {
            answerLayout.startAnimation(slideUpAnimation);
            answerLayout.setVisibility(View.GONE);
        } else {
            if (isFirstAnswerVisible) {
                answer1.startAnimation(slideUpAnimation);
                answer1.setVisibility(View.GONE);
                isFirstAnswerVisible = false;
            }

            if (isSecondAnswerVisible) {
                answer2.startAnimation(slideUpAnimation);
                answer2.setVisibility(View.GONE);
                isSecondAnswerVisible = false;
            }

            if(isThirthAnswerVisible) {
                answer3.startAnimation(slideUpAnimation);
                answer3.setVisibility(View.GONE);
                isThirthAnswerVisible = false;
            }

            if(isFourthAnswerVisible) {
                answer4.startAnimation(slideUpAnimation);
                answer4.setVisibility(View.GONE);
                isFourthAnswerVisible = false;
            }

            answerLayout.startAnimation(slideDownAnimation);
            answerLayout.setVisibility(View.VISIBLE);

            if (answerLayout == answer1) {
                isFirstAnswerVisible = true;
                isSecondAnswerVisible = false;
                isThirthAnswerVisible = false;
                isFourthAnswerVisible = false;
            } else if (answerLayout == answer2) {
                isSecondAnswerVisible = true;
                isFirstAnswerVisible = false;
                isThirthAnswerVisible = false;
                isFourthAnswerVisible = false;
            } else if(answerLayout == answer3) {
                isThirthAnswerVisible = true;
                isFirstAnswerVisible = false;
                isSecondAnswerVisible = false;
                isFourthAnswerVisible = false;
            } else if(answerLayout == answer4) {
                isFourthAnswerVisible = true;
                isFirstAnswerVisible = false;
                isSecondAnswerVisible = false;
                isThirthAnswerVisible = false;
            }
        }
    }
}