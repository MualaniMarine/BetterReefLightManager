package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.widget.PreventRepeatButton;

/* JADX INFO: loaded from: classes.dex */
public final class DialogTimeLuminanceListBinding implements ViewBinding {
    public final PreventRepeatButton btnSave;
    private final ConstraintLayout rootView;
    public final RecyclerView rvTimeLuminanceList;
    public final TextView tvTitle;

    private DialogTimeLuminanceListBinding(ConstraintLayout constraintLayout, PreventRepeatButton preventRepeatButton, RecyclerView recyclerView, TextView textView) {
        this.rootView = constraintLayout;
        this.btnSave = preventRepeatButton;
        this.rvTimeLuminanceList = recyclerView;
        this.tvTitle = textView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static DialogTimeLuminanceListBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static DialogTimeLuminanceListBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.dialog_time_luminance_list, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogTimeLuminanceListBinding bind(View view) {
        int i = C0575R.id.btn_save;
        PreventRepeatButton preventRepeatButton = (PreventRepeatButton) view.findViewById(C0575R.id.btn_save);
        if (preventRepeatButton != null) {
            i = C0575R.id.rv_time_luminance_list;
            RecyclerView recyclerView = (RecyclerView) view.findViewById(C0575R.id.rv_time_luminance_list);
            if (recyclerView != null) {
                i = C0575R.id.tv_title;
                TextView textView = (TextView) view.findViewById(C0575R.id.tv_title);
                if (textView != null) {
                    return new DialogTimeLuminanceListBinding((ConstraintLayout) view, preventRepeatButton, recyclerView, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
