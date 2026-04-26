package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityWebBinding implements ViewBinding {
    private final ConstraintLayout rootView;
    public final WebView web;

    private ActivityWebBinding(ConstraintLayout constraintLayout, WebView webView) {
        this.rootView = constraintLayout;
        this.web = webView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityWebBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityWebBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.activity_web, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityWebBinding bind(View view) {
        WebView webView = (WebView) view.findViewById(C0575R.id.web);
        if (webView != null) {
            return new ActivityWebBinding((ConstraintLayout) view, webView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(C0575R.id.web)));
    }
}
