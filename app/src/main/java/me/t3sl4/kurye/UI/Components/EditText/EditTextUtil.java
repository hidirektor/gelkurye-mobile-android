package me.t3sl4.kurye.UI.Components.EditText;

import android.app.Activity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.Nullable;

import me.t3sl4.kurye.kurye.UI.Components.Sneaker.Sneaker;

public class EditTextUtil {
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

    public static void blockFirstZeroCharacter(EditText phoneField, Activity context) {
        phoneField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phoneField.getText().toString().matches("^0") ) {
                    Sneaker.with(context).setTitle("Hata !").setMessage("Telefon numarası sıfır ile başlayamaz!").sneakError();
                    phoneField.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        phoneField.setFilters(new InputFilter[] {
                (source, start, end, dest, dstart, dend) -> {
                    for (int i = start; i < end; i++) {
                        if (Character.isWhitespace(source.charAt(i))) {
                            return "";
                        }
                    }
                    return null;
                }
        });
    }
}
