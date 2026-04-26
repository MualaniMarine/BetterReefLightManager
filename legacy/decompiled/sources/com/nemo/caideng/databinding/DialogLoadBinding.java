package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;

/* JADX INFO: loaded from: classes.dex */
public final class DialogLoadBinding implements ViewBinding {
    private final ConstraintLayout rootView;

    private DialogLoadBinding(ConstraintLayout constraintLayout) {
        this.rootView = constraintLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static DialogLoadBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static DialogLoadBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.dialog_load, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogLoadBinding bind(View view) {
        if (view == null) {
            throw new NullPointerException("rootView");
        }
        return new DialogLoadBinding((ConstraintLayout) view);
    }
}
