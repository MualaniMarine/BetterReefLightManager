package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;

/* JADX INFO: loaded from: classes.dex */
public final class ItemTimeLuminanceListBinding implements ViewBinding {
    public final Button btnEdit;
    private final LinearLayout rootView;
    public final TextView tvLuminance;
    public final TextView tvTime;

    private ItemTimeLuminanceListBinding(LinearLayout linearLayout, Button button, TextView textView, TextView textView2) {
        this.rootView = linearLayout;
        this.btnEdit = button;
        this.tvLuminance = textView;
        this.tvTime = textView2;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemTimeLuminanceListBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ItemTimeLuminanceListBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.item_time_luminance_list, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ItemTimeLuminanceListBinding bind(View view) {
        int i = C0575R.id.btn_edit;
        Button button = (Button) view.findViewById(C0575R.id.btn_edit);
        if (button != null) {
            i = C0575R.id.tv_luminance;
            TextView textView = (TextView) view.findViewById(C0575R.id.tv_luminance);
            if (textView != null) {
                i = C0575R.id.tv_time;
                TextView textView2 = (TextView) view.findViewById(C0575R.id.tv_time);
                if (textView2 != null) {
                    return new ItemTimeLuminanceListBinding((LinearLayout) view, button, textView, textView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
