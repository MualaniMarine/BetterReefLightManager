package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityMainBinding implements ViewBinding {
    public final ImageView ivK7;
    public final ImageView ivSetting;
    public final ImageView ivX4;
    public final Guideline line1;
    public final Guideline line2;
    private final ConstraintLayout rootView;
    public final ImageView tvK7Lps;
    public final ImageView tvK7Sl;
    public final ImageView tvK7Sps;
    public final ImageView tvLogo;
    public final TextView tvVersion;
    public final ImageView tvX4Lps;
    public final ImageView tvX4Sl;
    public final ImageView tvX4Sps;
    public final View vChecked;

    private ActivityMainBinding(ConstraintLayout constraintLayout, ImageView imageView, ImageView imageView2, ImageView imageView3, Guideline guideline, Guideline guideline2, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, TextView textView, ImageView imageView8, ImageView imageView9, ImageView imageView10, View view) {
        this.rootView = constraintLayout;
        this.ivK7 = imageView;
        this.ivSetting = imageView2;
        this.ivX4 = imageView3;
        this.line1 = guideline;
        this.line2 = guideline2;
        this.tvK7Lps = imageView4;
        this.tvK7Sl = imageView5;
        this.tvK7Sps = imageView6;
        this.tvLogo = imageView7;
        this.tvVersion = textView;
        this.tvX4Lps = imageView8;
        this.tvX4Sl = imageView9;
        this.tvX4Sps = imageView10;
        this.vChecked = view;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityMainBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityMainBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.activity_main, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityMainBinding bind(View view) {
        int i = C0575R.id.iv_k7;
        ImageView imageView = (ImageView) view.findViewById(C0575R.id.iv_k7);
        if (imageView != null) {
            i = C0575R.id.iv_setting;
            ImageView imageView2 = (ImageView) view.findViewById(C0575R.id.iv_setting);
            if (imageView2 != null) {
                i = C0575R.id.iv_x4;
                ImageView imageView3 = (ImageView) view.findViewById(C0575R.id.iv_x4);
                if (imageView3 != null) {
                    i = C0575R.id.line1;
                    Guideline guideline = (Guideline) view.findViewById(C0575R.id.line1);
                    if (guideline != null) {
                        i = C0575R.id.line2;
                        Guideline guideline2 = (Guideline) view.findViewById(C0575R.id.line2);
                        if (guideline2 != null) {
                            i = C0575R.id.tv_k7_lps;
                            ImageView imageView4 = (ImageView) view.findViewById(C0575R.id.tv_k7_lps);
                            if (imageView4 != null) {
                                i = C0575R.id.tv_k7_sl;
                                ImageView imageView5 = (ImageView) view.findViewById(C0575R.id.tv_k7_sl);
                                if (imageView5 != null) {
                                    i = C0575R.id.tv_k7_sps;
                                    ImageView imageView6 = (ImageView) view.findViewById(C0575R.id.tv_k7_sps);
                                    if (imageView6 != null) {
                                        i = C0575R.id.tv_logo;
                                        ImageView imageView7 = (ImageView) view.findViewById(C0575R.id.tv_logo);
                                        if (imageView7 != null) {
                                            i = C0575R.id.tv_version;
                                            TextView textView = (TextView) view.findViewById(C0575R.id.tv_version);
                                            if (textView != null) {
                                                i = C0575R.id.tv_x4_lps;
                                                ImageView imageView8 = (ImageView) view.findViewById(C0575R.id.tv_x4_lps);
                                                if (imageView8 != null) {
                                                    i = C0575R.id.tv_x4_sl;
                                                    ImageView imageView9 = (ImageView) view.findViewById(C0575R.id.tv_x4_sl);
                                                    if (imageView9 != null) {
                                                        i = C0575R.id.tv_x4_sps;
                                                        ImageView imageView10 = (ImageView) view.findViewById(C0575R.id.tv_x4_sps);
                                                        if (imageView10 != null) {
                                                            i = C0575R.id.v_checked;
                                                            View viewFindViewById = view.findViewById(C0575R.id.v_checked);
                                                            if (viewFindViewById != null) {
                                                                return new ActivityMainBinding((ConstraintLayout) view, imageView, imageView2, imageView3, guideline, guideline2, imageView4, imageView5, imageView6, imageView7, textView, imageView8, imageView9, imageView10, viewFindViewById);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
