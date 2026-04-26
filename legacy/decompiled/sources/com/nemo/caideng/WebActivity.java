package com.nemo.caideng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.nemo.caideng.databinding.ActivityWebBinding;

/* JADX INFO: loaded from: classes.dex */
public class WebActivity extends BaseActivity {
    ActivityWebBinding activityWebBinding;

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, (Class<?>) WebActivity.class));
    }

    @Override // com.nemo.caideng.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityWebBinding activityWebBindingInflate = ActivityWebBinding.inflate(getLayoutInflater());
        this.activityWebBinding = activityWebBindingInflate;
        setContentView(activityWebBindingInflate.getRoot());
        WebSettings settings = this.activityWebBinding.web.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        this.activityWebBinding.web.loadUrl("https://noo-psyche.com/");
        this.activityWebBinding.web.setWebViewClient(new WebViewClient() { // from class: com.nemo.caideng.WebActivity.1
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        });
    }
}
