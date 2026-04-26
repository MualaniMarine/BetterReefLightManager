package com.nemo.caideng;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;

/* JADX INFO: loaded from: classes.dex */
public class AirkissActivity_ViewBinding implements Unbinder {
    private AirkissActivity target;
    private View view7f080059;

    public AirkissActivity_ViewBinding(AirkissActivity airkissActivity) {
        this(airkissActivity, airkissActivity.getWindow().getDecorView());
    }

    public AirkissActivity_ViewBinding(final AirkissActivity airkissActivity, View view) {
        this.target = airkissActivity;
        View viewFindRequiredView = Utils.findRequiredView(view, C0575R.id.bt_confirm, "field 'mSendButton' and method 'onViewClicked'");
        airkissActivity.mSendButton = (Button) Utils.castView(viewFindRequiredView, C0575R.id.bt_confirm, "field 'mSendButton'", Button.class);
        this.view7f080059 = viewFindRequiredView;
        viewFindRequiredView.setOnClickListener(new DebouncingOnClickListener() { // from class: com.nemo.caideng.AirkissActivity_ViewBinding.1
            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                airkissActivity.onViewClicked(view2);
            }
        });
        airkissActivity.mSSIDTV = (TextView) Utils.findRequiredViewAsType(view, C0575R.id.tv_ssid, "field 'mSSIDTV'", TextView.class);
        airkissActivity.mPasswordEt = (EditText) Utils.findRequiredViewAsType(view, C0575R.id.et_password, "field 'mPasswordEt'", EditText.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        AirkissActivity airkissActivity = this.target;
        if (airkissActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        airkissActivity.mSendButton = null;
        airkissActivity.mSSIDTV = null;
        airkissActivity.mPasswordEt = null;
        this.view7f080059.setOnClickListener(null);
        this.view7f080059 = null;
    }
}
