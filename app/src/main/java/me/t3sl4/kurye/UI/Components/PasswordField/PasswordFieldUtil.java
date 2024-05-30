package me.t3sl4.kurye.UI.Components.PasswordField;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;

import me.t3sl4.kurye.R;

public class PasswordFieldUtil {
    private static boolean isPasswordVisible = false;
    private static boolean isPasswordTouch = false;

    public static void togglePasswordVisibility(EditText editTextPassword, Context context) {
        isPasswordVisible = !isPasswordVisible;
        int drawableResId = isPasswordVisible ? R.drawable.ikon_pass_hide : R.drawable.ikon_pass_show;
        setPasswordVisibility(isPasswordVisible, editTextPassword);
        updatePasswordToggleIcon(drawableResId, editTextPassword, context);
    }

    private static void setPasswordVisibility(boolean visible, EditText editTextPassword) {
        int inputType = visible ?
                android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
        editTextPassword.setInputType(inputType);
        editTextPassword.setSelection(editTextPassword.getText().length());
    }

    private static void updatePasswordToggleIcon(@DrawableRes int drawableResId, EditText editTextPassword, Context context) {
        Drawable[] drawables = editTextPassword.getCompoundDrawablesRelative();
        drawables[2] = ResourcesCompat.getDrawable(context.getResources(), drawableResId, context.getTheme());
        editTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void setChangeablePasswordField(EditText editTextPassword, Context context) {
        editTextPassword.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        PasswordFieldUtil.togglePasswordVisibility(editTextPassword, context);
                        isPasswordTouch = !isPasswordTouch;
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
