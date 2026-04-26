package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.widget.AllSeekNumView;
import com.nemo.caideng.widget.PreventRepeatButton;
import com.nemo.caideng.widget.TimeWheelView;

/* JADX INFO: loaded from: classes.dex */
public final class DialogTimeSeekPickerBinding implements ViewBinding {
    public final AllSeekNumView asnvSeek;
    public final PreventRepeatButton btnCancel;
    public final PreventRepeatButton btnPreview;
    public final PreventRepeatButton btnSure;
    private final ConstraintLayout rootView;
    public final TextView tvTitle;
    public final TimeWheelView twvTime;

    private DialogTimeSeekPickerBinding(ConstraintLayout constraintLayout, AllSeekNumView allSeekNumView, PreventRepeatButton preventRepeatButton, PreventRepeatButton preventRepeatButton2, PreventRepeatButton preventRepeatButton3, TextView textView, TimeWheelView timeWheelView) {
        this.rootView = constraintLayout;
        this.asnvSeek = allSeekNumView;
        this.btnCancel = preventRepeatButton;
        this.btnPreview = preventRepeatButton2;
        this.btnSure = preventRepeatButton3;
        this.tvTitle = textView;
        this.twvTime = timeWheelView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static DialogTimeSeekPickerBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static DialogTimeSeekPickerBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.dialog_time_seek_picker, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogTimeSeekPickerBinding bind(View view) {
        int i = C0575R.id.asnv_seek;
        AllSeekNumView allSeekNumView = (AllSeekNumView) view.findViewById(C0575R.id.asnv_seek);
        if (allSeekNumView != null) {
            i = C0575R.id.btn_cancel;
            PreventRepeatButton preventRepeatButton = (PreventRepeatButton) view.findViewById(C0575R.id.btn_cancel);
            if (preventRepeatButton != null) {
                i = C0575R.id.btn_preview;
                PreventRepeatButton preventRepeatButton2 = (PreventRepeatButton) view.findViewById(C0575R.id.btn_preview);
                if (preventRepeatButton2 != null) {
                    i = C0575R.id.btn_sure;
                    PreventRepeatButton preventRepeatButton3 = (PreventRepeatButton) view.findViewById(C0575R.id.btn_sure);
                    if (preventRepeatButton3 != null) {
                        i = C0575R.id.tv_title;
                        TextView textView = (TextView) view.findViewById(C0575R.id.tv_title);
                        if (textView != null) {
                            i = C0575R.id.twv_time;
                            TimeWheelView timeWheelView = (TimeWheelView) view.findViewById(C0575R.id.twv_time);
                            if (timeWheelView != null) {
                                return new DialogTimeSeekPickerBinding((ConstraintLayout) view, allSeekNumView, preventRepeatButton, preventRepeatButton2, preventRepeatButton3, textView, timeWheelView);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
