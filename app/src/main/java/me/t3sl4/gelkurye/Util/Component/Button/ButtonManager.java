package me.t3sl4.gelkurye.Util.Component.Button;

import android.content.Context;
import android.util.Log;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import me.t3sl4.gelkurye.R;

public class ButtonManager {
    public static void orderButtonColorEffect(int type, Button firstButton, Button secondButton, Context context) {
        switch (type) {
            case 1:
                secondButton.setBackgroundResource(R.drawable.allorders);
                firstButton.setBackgroundResource(R.drawable.waitingorders);
                secondButton.setTextColor(ContextCompat.getColor(context, R.color.editTextTopColor));
                firstButton.setTextColor(ContextCompat.getColor(context, R.color.white));
                break;
            case 2:
                firstButton.setBackgroundResource(R.drawable.allorders);
                secondButton.setBackgroundResource(R.drawable.waitingorders);
                firstButton.setTextColor(ContextCompat.getColor(context, R.color.editTextTopColor));
                secondButton.setTextColor(ContextCompat.getColor(context, R.color.white));
                break;
            default:
                Log.e("ButtonManager", "Unsupported button color change type !");
        }
    }
}
