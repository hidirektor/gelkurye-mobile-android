package me.t3sl4.kurye.UI.Components.PinView;

import androidx.annotation.Nullable;

import com.chaos.view.PinView;

public class PinViewUtil {
    public static boolean checkNull(PinView controlElement, @Nullable Integer controlSize) {
        if(controlSize != null) {
            return controlElement.getText().length() == controlSize;
        } else {
            return controlElement.getText() != null;
        }
    }
}
