package me.t3sl4.gelkurye.Screens.PasswordReset;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.chaos.view.PinView;

import java.util.Objects;

import me.t3sl4.gelkurye.R;

public class Reset2 extends AppCompatActivity {
    private PinView enteredOTP;
    private Button otpVerifyButton;
    private PinView remainingTimeMinuteFirst;
    private PinView remainingTimeMinuteSecond;
    private PinView remainingTimeSecondFirst;
    private PinView remainingTimeSecondSecond;
    private CountDownTimer countDownTimer;
    private Button sendAgainButton;

    boolean isOTPEntered = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_2);

        enteredOTP = findViewById(R.id.enteredOTP);
        otpVerifyButton = findViewById(R.id.otpVerifyButton);

        remainingTimeMinuteFirst = findViewById(R.id.remainingTimeMinuteFirst);
        remainingTimeMinuteSecond = findViewById(R.id.remainingTimeMinuteSecond);
        remainingTimeSecondFirst = findViewById(R.id.remainingSecondFirst);
        remainingTimeSecondSecond = findViewById(R.id.remainingSecondSecond);
        sendAgainButton = findViewById(R.id.sendAgainButton);

        remainingTimeMinuteFirst.setText("0");
        remainingTimeMinuteSecond.setText("2");
        remainingTimeSecondFirst.setText("0");
        remainingTimeSecondSecond.setText("0");

        sendAgainButton.setOnClickListener(v -> {
            sendAgainButton.setVisibility(View.INVISIBLE);
            startCountdown();
        });

        otpVerifyButton.setOnClickListener(v -> {
            if(isOTPEntered) {
                //OTP Girildi ve 6 karaktere sahip işleme devam et
            }
        });

        startCountdown();

        countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdownUI(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                refreshColor(0);
                if(checkPinView() == 1) {
                    isOTPEntered = true;
                }
                sendAgainButton.setVisibility(View.VISIBLE);
                remainingTimeMinuteFirst.setText("0");
                remainingTimeMinuteSecond.setText("0");
                remainingTimeSecondFirst.setText("0");
                remainingTimeSecondSecond.setText("0");
            }
        }.start();
    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdownUI(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                refreshColor(0);
                if(checkPinView() == 1) {
                    isOTPEntered = true;
                }
                sendAgainButton.setVisibility(View.VISIBLE);
                remainingTimeMinuteFirst.setText("0");
                remainingTimeMinuteSecond.setText("0");
                remainingTimeSecondFirst.setText("0");
                remainingTimeSecondSecond.setText("0");
            }
        }.start();
    }

    private void updateCountdownUI(long millisUntilFinished) {
        long minutes = millisUntilFinished / 60000;
        long seconds = (millisUntilFinished % 60000) / 1000;

        String minutesFormatted = String.format("%02d", minutes);
        String secondsFormatted = String.format("%02d", seconds);

        remainingTimeMinuteFirst.setText(minutesFormatted.substring(0, 1));
        remainingTimeMinuteSecond.setText(minutesFormatted.substring(1, 2));

        remainingTimeSecondFirst.setText(secondsFormatted.substring(0, 1));
        remainingTimeSecondSecond.setText(secondsFormatted.substring(1, 2));

        if (minutes == 0 && seconds <= 30) {
            refreshColor(1);
        }
    }

    private void refreshColor(int type) {
        Drawable beforeDrawable = ResourcesCompat.getDrawable(getResources(), R.color.checkBoxColor, null);
        int beforeColor = ResourcesCompat.getColor(getResources(), R.color.checkBoxColor, null);
        int beforeColorWhite = ResourcesCompat.getColor(getResources(), R.color.editTextTopColor, null);

        Drawable afterDrawable = ResourcesCompat.getDrawable(getResources(), R.color.declineStartColor, null);
        int afterColor = ResourcesCompat.getColor(getResources(), R.color.declineStartColor, null);
        int afterColorWhite = ResourcesCompat.getColor(getResources(), R.color.white, null);

        Drawable processDrawable = null;
        int processColor = 0;
        int processColorWhite = 0;

        if(type == 0) {
            processDrawable = beforeDrawable;
            processColor = beforeColor;
            processColorWhite = beforeColorWhite;
        } else if(type == 1) {
            processDrawable = afterDrawable;
            processColor = afterColor;
            processColorWhite = afterColorWhite;
        }

        remainingTimeMinuteFirst.setItemBackground(processDrawable);
        remainingTimeMinuteFirst.setLineColor(processColor);
        remainingTimeMinuteFirst.setTextColor(processColorWhite);
        remainingTimeMinuteSecond.setItemBackground(processDrawable);
        remainingTimeMinuteSecond.setLineColor(processColor);
        remainingTimeMinuteSecond.setTextColor(processColorWhite);
        remainingTimeSecondFirst.setItemBackground(processDrawable);
        remainingTimeSecondFirst.setLineColor(processColor);
        remainingTimeSecondFirst.setTextColor(processColorWhite);
        remainingTimeSecondSecond.setItemBackground(processDrawable);
        remainingTimeSecondSecond.setLineColor(processColor);
        remainingTimeSecondSecond.setTextColor(processColorWhite);
    }

    private int checkPinView() {
        if(Objects.requireNonNull(enteredOTP.getText()).toString().length() != 6) {
            return 0;
        }
        return 1;
    }
}
