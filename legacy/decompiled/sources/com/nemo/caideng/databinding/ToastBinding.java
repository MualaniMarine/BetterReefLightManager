package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;

/* JADX INFO: loaded from: classes.dex */
public final class ToastBinding implements ViewBinding {
    private final FrameLayout rootView;
    public final TextView tvToastContent;

    private ToastBinding(FrameLayout frameLayout, TextView textView) {
        this.rootView = frameLayout;
        this.tvToastContent = textView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static ToastBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ToastBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.toast, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ToastBinding bind(View view) {
        TextView textView = (TextView) view.findViewById(C0575R.id.tv_toast_content);
        if (textView != null) {
            return new ToastBinding((FrameLayout) view, textView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(C0575R.id.tv_toast_content)));
    }
}
