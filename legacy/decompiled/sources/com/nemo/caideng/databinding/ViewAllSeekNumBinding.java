package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.widget.SeekNumView;

/* JADX INFO: loaded from: classes.dex */
public final class ViewAllSeekNumBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final SeekNumView svSeek1;
    public final SeekNumView svSeek2;
    public final SeekNumView svSeek3;
    public final SeekNumView svSeek4;
    public final SeekNumView svSeek5;
    public final SeekNumView svSeek6;

    private ViewAllSeekNumBinding(LinearLayout linearLayout, SeekNumView seekNumView, SeekNumView seekNumView2, SeekNumView seekNumView3, SeekNumView seekNumView4, SeekNumView seekNumView5, SeekNumView seekNumView6) {
        this.rootView = linearLayout;
        this.svSeek1 = seekNumView;
        this.svSeek2 = seekNumView2;
        this.svSeek3 = seekNumView3;
        this.svSeek4 = seekNumView4;
        this.svSeek5 = seekNumView5;
        this.svSeek6 = seekNumView6;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ViewAllSeekNumBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ViewAllSeekNumBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.view_all_seek_num, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ViewAllSeekNumBinding bind(View view) {
        int i = C0575R.id.sv_seek_1;
        SeekNumView seekNumView = (SeekNumView) view.findViewById(C0575R.id.sv_seek_1);
        if (seekNumView != null) {
            i = C0575R.id.sv_seek_2;
            SeekNumView seekNumView2 = (SeekNumView) view.findViewById(C0575R.id.sv_seek_2);
            if (seekNumView2 != null) {
                i = C0575R.id.sv_seek_3;
                SeekNumView seekNumView3 = (SeekNumView) view.findViewById(C0575R.id.sv_seek_3);
                if (seekNumView3 != null) {
                    i = C0575R.id.sv_seek_4;
                    SeekNumView seekNumView4 = (SeekNumView) view.findViewById(C0575R.id.sv_seek_4);
                    if (seekNumView4 != null) {
                        i = C0575R.id.sv_seek_5;
                        SeekNumView seekNumView5 = (SeekNumView) view.findViewById(C0575R.id.sv_seek_5);
                        if (seekNumView5 != null) {
                            i = C0575R.id.sv_seek_6;
                            SeekNumView seekNumView6 = (SeekNumView) view.findViewById(C0575R.id.sv_seek_6);
                            if (seekNumView6 != null) {
                                return new ViewAllSeekNumBinding((LinearLayout) view, seekNumView, seekNumView2, seekNumView3, seekNumView4, seekNumView5, seekNumView6);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
