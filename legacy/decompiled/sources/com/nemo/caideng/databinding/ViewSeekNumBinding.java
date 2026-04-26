package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;

/* JADX INFO: loaded from: classes.dex */
public final class ViewSeekNumBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final SeekBar sbSeekPro;
    public final Button tvSeekAdd;
    public final TextView tvSeekName;
    public final TextView tvSeekProNum;
    public final Button tvSeekReduce;

    private ViewSeekNumBinding(LinearLayout linearLayout, SeekBar seekBar, Button button, TextView textView, TextView textView2, Button button2) {
        this.rootView = linearLayout;
        this.sbSeekPro = seekBar;
        this.tvSeekAdd = button;
        this.tvSeekName = textView;
        this.tvSeekProNum = textView2;
        this.tvSeekReduce = button2;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ViewSeekNumBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ViewSeekNumBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.view_seek_num, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ViewSeekNumBinding bind(View view) {
        int i = C0575R.id.sb_seek_pro;
        SeekBar seekBar = (SeekBar) view.findViewById(C0575R.id.sb_seek_pro);
        if (seekBar != null) {
            i = C0575R.id.tv_seek_add;
            Button button = (Button) view.findViewById(C0575R.id.tv_seek_add);
            if (button != null) {
                i = C0575R.id.tv_seek_name;
                TextView textView = (TextView) view.findViewById(C0575R.id.tv_seek_name);
                if (textView != null) {
                    i = C0575R.id.tv_seek_pro_num;
                    TextView textView2 = (TextView) view.findViewById(C0575R.id.tv_seek_pro_num);
                    if (textView2 != null) {
                        i = C0575R.id.tv_seek_reduce;
                        Button button2 = (Button) view.findViewById(C0575R.id.tv_seek_reduce);
                        if (button2 != null) {
                            return new ViewSeekNumBinding((LinearLayout) view, seekBar, button, textView, textView2, button2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
