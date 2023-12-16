package me.t3sl4.gelkurye.Util.Component.EditText;

import android.widget.EditText;

import androidx.annotation.Nullable;

public class EditTextManager {
    public static boolean checkNull(EditText controlElement, @Nullable Integer controlSize) {
        if(controlSize != null) {
            return controlElement.getText().length() == controlSize;
        } else {
            return controlElement.getText() != null;
        }
    }

    public static String parseEditText(EditText controlElement) {
        if(controlElement.getText().toString().contains("@")) {
            return "mail";
        }
        return "username";
    }
}
