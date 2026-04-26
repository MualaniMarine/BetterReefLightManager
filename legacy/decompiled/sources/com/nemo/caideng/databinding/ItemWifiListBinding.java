package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;

/* JADX INFO: loaded from: classes.dex */
public final class ItemWifiListBinding implements ViewBinding {
    public final CheckedTextView rbRadio;
    private final ConstraintLayout rootView;
    public final TextView tvWifiName;

    private ItemWifiListBinding(ConstraintLayout constraintLayout, CheckedTextView checkedTextView, TextView textView) {
        this.rootView = constraintLayout;
        this.rbRadio = checkedTextView;
        this.tvWifiName = textView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ItemWifiListBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ItemWifiListBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.item_wifi_list, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemWifiListBinding bind(View view) {
        int i = C0575R.id.rb_radio;
        CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(C0575R.id.rb_radio);
        if (checkedTextView != null) {
            i = C0575R.id.tv_wifi_name;
            TextView textView = (TextView) view.findViewById(C0575R.id.tv_wifi_name);
            if (textView != null) {
                return new ItemWifiListBinding((ConstraintLayout) view, checkedTextView, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
