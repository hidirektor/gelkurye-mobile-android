package me.t3sl4.gelkurye.UI.PinView;

import androidx.annotation.Nullable;

import com.chaos.view.PinView;

public class PinViewManager {
    public static boolean checkNull(PinView controlElement, @Nullable Integer controlSize) {
        if(controlSize != null) {
            return controlElement.getText().length() == controlSize;
        } else {
            return controlElement.getText() != null;
        }
    }
}
