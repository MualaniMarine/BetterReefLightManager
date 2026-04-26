package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;

/* JADX INFO: loaded from: classes.dex */
public final class DialogConfigProgressBinding implements ViewBinding {
    private final ConstraintLayout rootView;

    private DialogConfigProgressBinding(ConstraintLayout constraintLayout) {
        this.rootView = constraintLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static DialogConfigProgressBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static DialogConfigProgressBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.dialog_config_progress, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogConfigProgressBinding bind(View view) {
        if (view == null) {
            throw new NullPointerException("rootView");
        }
        return new DialogConfigProgressBinding((ConstraintLayout) view);
    }
}
