package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.widget.AllSeekNumView;
import com.nemo.caideng.widget.PreventRepeatButton;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityTestControlModel1Binding implements ViewBinding {
    public final AllSeekNumView asnvSeek;
    public final PreventRepeatButton btnSave;
    private final FrameLayout rootView;

    private ActivityTestControlModel1Binding(FrameLayout frameLayout, AllSeekNumView allSeekNumView, PreventRepeatButton preventRepeatButton) {
        this.rootView = frameLayout;
        this.asnvSeek = allSeekNumView;
        this.btnSave = preventRepeatButton;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static ActivityTestControlModel1Binding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityTestControlModel1Binding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.activity_test_control_model1, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityTestControlModel1Binding bind(View view) {
        int i = C0575R.id.asnv_seek;
        AllSeekNumView allSeekNumView = (AllSeekNumView) view.findViewById(C0575R.id.asnv_seek);
        if (allSeekNumView != null) {
            i = C0575R.id.btn_save;
            PreventRepeatButton preventRepeatButton = (PreventRepeatButton) view.findViewById(C0575R.id.btn_save);
            if (preventRepeatButton != null) {
                return new ActivityTestControlModel1Binding((FrameLayout) view, allSeekNumView, preventRepeatButton);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
