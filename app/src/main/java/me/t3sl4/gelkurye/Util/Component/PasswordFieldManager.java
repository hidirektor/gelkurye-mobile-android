package me.t3sl4.gelkurye.Util.Component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.EditText;

import androidx.annotation.DrawableRes;

import me.t3sl4.gelkurye.R;

public class PasswordFieldManager {
    private static boolean isPasswordVisible = false;

    public static void togglePasswordVisibility(EditText editTextTextPassword, Context context) {
        isPasswordVisible = !isPasswordVisible;
        int drawableResId = isPasswordVisible ? R.drawable.password_hide_ikon : R.drawable.password_show_ikon;
        setPasswordVisibility(isPasswordVisible, editTextTextPassword);
        updatePasswordToggleIcon(drawableResId, editTextTextPassword, context);
    }

    private static void setPasswordVisibility(boolean visible, EditText editTextTextPassword) {
        int inputType = visible ?
                android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
        editTextTextPassword.setInputType(inputType);
        editTextTextPassword.setSelection(editTextTextPassword.getText().length());
    }

    private static void updatePasswordToggleIcon(@DrawableRes int drawableResId, EditText editTextTextPassword, Context context) {
        Drawable[] drawables = editTextTextPassword.getCompoundDrawablesRelative();
        drawables[2] = context.getResources().getDrawable(drawableResId, context.getTheme());
        editTextTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                drawables[0], drawables[1], drawables[2], drawables[3]);
    }
}
