package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.nemo.caideng.C0575R;
import com.nemo.caideng.widget.PreventRepeatButton;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityNetworkAndModelBinding implements ViewBinding {
    public final PreventRepeatButton btnChangeNet;
    public final PreventRepeatButton btnSave;
    public final PreventRepeatButton cancelButton;
    public final LinearLayout content;
    public final EditText editIp;
    public final EditText editRoutePass;
    public final ImageView ivShowHidePass;
    public final LinearLayout llRoutePass;
    public final ProgressBar progressBar;
    public final ConstraintLayout progressView;
    public final RadioButton rbApNet;
    public final RadioButton rbLanNet;
    public final RadioGroup rgGroup;
    private final FrameLayout rootView;
    public final TextView testResult;
    public final TextView tvIpTitle;
    public final TextView tvIssue;
    public final TextView tvRemarkInfo;
    public final TextView tvRouteName;
    public final TextView tvRoutePassTitle;

    private ActivityNetworkAndModelBinding(FrameLayout frameLayout, PreventRepeatButton preventRepeatButton, PreventRepeatButton preventRepeatButton2, PreventRepeatButton preventRepeatButton3, LinearLayout linearLayout, EditText editText, EditText editText2, ImageView imageView, LinearLayout linearLayout2, ProgressBar progressBar, ConstraintLayout constraintLayout, RadioButton radioButton, RadioButton radioButton2, RadioGroup radioGroup, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6) {
        this.rootView = frameLayout;
        this.btnChangeNet = preventRepeatButton;
        this.btnSave = preventRepeatButton2;
        this.cancelButton = preventRepeatButton3;
        this.content = linearLayout;
        this.editIp = editText;
        this.editRoutePass = editText2;
        this.ivShowHidePass = imageView;
        this.llRoutePass = linearLayout2;
        this.progressBar = progressBar;
        this.progressView = constraintLayout;
        this.rbApNet = radioButton;
        this.rbLanNet = radioButton2;
        this.rgGroup = radioGroup;
        this.testResult = textView;
        this.tvIpTitle = textView2;
        this.tvIssue = textView3;
        this.tvRemarkInfo = textView4;
        this.tvRouteName = textView5;
        this.tvRoutePassTitle = textView6;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static ActivityNetworkAndModelBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityNetworkAndModelBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.activity_network_and_model, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityNetworkAndModelBinding bind(View view) {
        int i = C0575R.id.btn_change_net;
        PreventRepeatButton preventRepeatButton = (PreventRepeatButton) view.findViewById(C0575R.id.btn_change_net);
        if (preventRepeatButton != null) {
            i = C0575R.id.btn_save;
            PreventRepeatButton preventRepeatButton2 = (PreventRepeatButton) view.findViewById(C0575R.id.btn_save);
            if (preventRepeatButton2 != null) {
                i = C0575R.id.cancel_button;
                PreventRepeatButton preventRepeatButton3 = (PreventRepeatButton) view.findViewById(C0575R.id.cancel_button);
                if (preventRepeatButton3 != null) {
                    i = C0575R.id.content;
                    LinearLayout linearLayout = (LinearLayout) view.findViewById(C0575R.id.content);
                    if (linearLayout != null) {
                        i = C0575R.id.edit_ip;
                        EditText editText = (EditText) view.findViewById(C0575R.id.edit_ip);
                        if (editText != null) {
                            i = C0575R.id.edit_route_pass;
                            EditText editText2 = (EditText) view.findViewById(C0575R.id.edit_route_pass);
                            if (editText2 != null) {
                                i = C0575R.id.iv_show_hide_pass;
                                ImageView imageView = (ImageView) view.findViewById(C0575R.id.iv_show_hide_pass);
                                if (imageView != null) {
                                    i = C0575R.id.ll_route_pass;
                                    LinearLayout linearLayout2 = (LinearLayout) view.findViewById(C0575R.id.ll_route_pass);
                                    if (linearLayout2 != null) {
                                        i = C0575R.id.progressBar;
                                        ProgressBar progressBar = (ProgressBar) view.findViewById(C0575R.id.progressBar);
                                        if (progressBar != null) {
                                            i = C0575R.id.progressView;
                                            ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(C0575R.id.progressView);
                                            if (constraintLayout != null) {
                                                i = C0575R.id.rb_ap_net;
                                                RadioButton radioButton = (RadioButton) view.findViewById(C0575R.id.rb_ap_net);
                                                if (radioButton != null) {
                                                    i = C0575R.id.rb_lan_net;
                                                    RadioButton radioButton2 = (RadioButton) view.findViewById(C0575R.id.rb_lan_net);
                                                    if (radioButton2 != null) {
                                                        i = C0575R.id.rg_group;
                                                        RadioGroup radioGroup = (RadioGroup) view.findViewById(C0575R.id.rg_group);
                                                        if (radioGroup != null) {
                                                            i = C0575R.id.testResult;
                                                            TextView textView = (TextView) view.findViewById(C0575R.id.testResult);
                                                            if (textView != null) {
                                                                i = C0575R.id.tv_ip_title;
                                                                TextView textView2 = (TextView) view.findViewById(C0575R.id.tv_ip_title);
                                                                if (textView2 != null) {
                                                                    i = C0575R.id.tv_issue;
                                                                    TextView textView3 = (TextView) view.findViewById(C0575R.id.tv_issue);
                                                                    if (textView3 != null) {
                                                                        i = C0575R.id.tv_remark_info;
                                                                        TextView textView4 = (TextView) view.findViewById(C0575R.id.tv_remark_info);
                                                                        if (textView4 != null) {
                                                                            i = C0575R.id.tv_route_name;
                                                                            TextView textView5 = (TextView) view.findViewById(C0575R.id.tv_route_name);
                                                                            if (textView5 != null) {
                                                                                i = C0575R.id.tv_route_pass_title;
                                                                                TextView textView6 = (TextView) view.findViewById(C0575R.id.tv_route_pass_title);
                                                                                if (textView6 != null) {
                                                                                    return new ActivityNetworkAndModelBinding((FrameLayout) view, preventRepeatButton, preventRepeatButton2, preventRepeatButton3, linearLayout, editText, editText2, imageView, linearLayout2, progressBar, constraintLayout, radioButton, radioButton2, radioGroup, textView, textView2, textView3, textView4, textView5, textView6);
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
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
