package per.goweii.anylayer;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

/* JADX INFO: loaded from: classes.dex */
final class InputMethodUtils {
    InputMethodUtils() {
    }

    public static void hide(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (inputMethodManager == null || !inputMethodManager.isActive()) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 2);
    }

    public static void show(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (inputMethodManager == null || inputMethodManager.isActive()) {
            return;
        }
        inputMethodManager.showSoftInput(view, 2);
    }

    public static boolean isShow(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        return inputMethodManager != null && inputMethodManager.isActive();
    }
}
