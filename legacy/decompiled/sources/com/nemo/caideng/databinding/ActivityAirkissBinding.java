package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityAirkissBinding implements ViewBinding {
    public final Button btConfirm;
    public final EditText etPassword;
    private final LinearLayout rootView;
    public final TextView tvSsid;

    private ActivityAirkissBinding(LinearLayout linearLayout, Button button, EditText editText, TextView textView) {
        this.rootView = linearLayout;
        this.btConfirm = button;
        this.etPassword = editText;
        this.tvSsid = textView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityAirkissBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityAirkissBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.activity_airkiss, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityAirkissBinding bind(View view) {
        int i = C0575R.id.bt_confirm;
        Button button = (Button) view.findViewById(C0575R.id.bt_confirm);
        if (button != null) {
            i = C0575R.id.et_password;
            EditText editText = (EditText) view.findViewById(C0575R.id.et_password);
            if (editText != null) {
                i = C0575R.id.tv_ssid;
                TextView textView = (TextView) view.findViewById(C0575R.id.tv_ssid);
                if (textView != null) {
                    return new ActivityAirkissBinding((LinearLayout) view, button, editText, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
