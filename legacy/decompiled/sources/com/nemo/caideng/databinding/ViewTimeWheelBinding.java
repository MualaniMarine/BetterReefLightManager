package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.widget.MyWheelView;

/* JADX INFO: loaded from: classes.dex */
public final class ViewTimeWheelBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final TextView tvSecond;
    public final MyWheelView wvHours;
    public final MyWheelView wvMinute;
    public final MyWheelView wvSecond;

    private ViewTimeWheelBinding(LinearLayout linearLayout, TextView textView, MyWheelView myWheelView, MyWheelView myWheelView2, MyWheelView myWheelView3) {
        this.rootView = linearLayout;
        this.tvSecond = textView;
        this.wvHours = myWheelView;
        this.wvMinute = myWheelView2;
        this.wvSecond = myWheelView3;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ViewTimeWheelBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ViewTimeWheelBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.view_time_wheel, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ViewTimeWheelBinding bind(View view) {
        int i = C0575R.id.tv_second;
        TextView textView = (TextView) view.findViewById(C0575R.id.tv_second);
        if (textView != null) {
            i = C0575R.id.wv_hours;
            MyWheelView myWheelView = (MyWheelView) view.findViewById(C0575R.id.wv_hours);
            if (myWheelView != null) {
                i = C0575R.id.wv_minute;
                MyWheelView myWheelView2 = (MyWheelView) view.findViewById(C0575R.id.wv_minute);
                if (myWheelView2 != null) {
                    i = C0575R.id.wv_second;
                    MyWheelView myWheelView3 = (MyWheelView) view.findViewById(C0575R.id.wv_second);
                    if (myWheelView3 != null) {
                        return new ViewTimeWheelBinding((LinearLayout) view, textView, myWheelView, myWheelView2, myWheelView3);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
