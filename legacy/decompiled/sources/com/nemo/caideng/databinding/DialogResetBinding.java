package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.widget.PreventRepeatButton;

/* JADX INFO: loaded from: classes.dex */
public final class DialogResetBinding implements ViewBinding {
    public final PreventRepeatButton btnCancel;
    public final PreventRepeatButton btnSure;
    private final ConstraintLayout rootView;
    public final TextView tvTitle;
    public final TextView twvTime;

    private DialogResetBinding(ConstraintLayout constraintLayout, PreventRepeatButton preventRepeatButton, PreventRepeatButton preventRepeatButton2, TextView textView, TextView textView2) {
        this.rootView = constraintLayout;
        this.btnCancel = preventRepeatButton;
        this.btnSure = preventRepeatButton2;
        this.tvTitle = textView;
        this.twvTime = textView2;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static DialogResetBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static DialogResetBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.dialog_reset, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogResetBinding bind(View view) {
        int i = C0575R.id.btn_cancel;
        PreventRepeatButton preventRepeatButton = (PreventRepeatButton) view.findViewById(C0575R.id.btn_cancel);
        if (preventRepeatButton != null) {
            i = C0575R.id.btn_sure;
            PreventRepeatButton preventRepeatButton2 = (PreventRepeatButton) view.findViewById(C0575R.id.btn_sure);
            if (preventRepeatButton2 != null) {
                i = C0575R.id.tv_title;
                TextView textView = (TextView) view.findViewById(C0575R.id.tv_title);
                if (textView != null) {
                    i = C0575R.id.twv_time;
                    TextView textView2 = (TextView) view.findViewById(C0575R.id.twv_time);
                    if (textView2 != null) {
                        return new DialogResetBinding((ConstraintLayout) view, preventRepeatButton, preventRepeatButton2, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
