package com.nemo.caideng;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.Observer;
import com.nemo.caideng.databinding.ActivityMainBinding;
import com.nemo.caideng.util.ToastMaker;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends BaseActivity implements View.OnClickListener, Observer<String> {
    private ActivityMainBinding activityMainBinding;
    StateResult stateResult;

    @Override // com.nemo.caideng.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityMainBinding activityMainBindingInflate = ActivityMainBinding.inflate(getLayoutInflater());
        this.activityMainBinding = activityMainBindingInflate;
        setContentView(activityMainBindingInflate.getRoot());
        this.activityMainBinding.tvK7Sps.setOnClickListener(this);
        this.activityMainBinding.tvK7Lps.setOnClickListener(this);
        this.activityMainBinding.tvK7Sl.setOnClickListener(this);
        this.activityMainBinding.tvX4Sps.setOnClickListener(this);
        this.activityMainBinding.tvX4Lps.setOnClickListener(this);
        this.activityMainBinding.tvX4Sl.setOnClickListener(this);
        this.activityMainBinding.ivSetting.setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$MainActivity$rjmJqnyiHoaGOsSh3sb1B-N36Mw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$0$MainActivity(view);
            }
        });
        BaseApplication.getInstance().observeBroadcast(this, this);
        this.activityMainBinding.tvVersion.setText(getString(C0575R.string.version, new Object[]{getAppVersionName(this)}));
    }

    public /* synthetic */ void lambda$onCreate$0$MainActivity(View view) {
        NetWorkAndModelActivity.startActivity(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.stateResult = check();
        checkModel();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void checkModel() {
        /*
            Method dump skipped, instruction units count: 414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nemo.caideng.MainActivity.checkModel():void");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (!BaseApplication.getInstance().getDataStore().isSet()) {
            ToastMaker.showLongToast(getString(C0575R.string.set_network_and_model));
            return;
        }
        int id = view.getId();
        int i = 3;
        int i2 = 0;
        switch (id) {
            case C0575R.id.tv_k7_lps /* 2131231024 */:
                i = 2;
            case C0575R.id.tv_k7_sl /* 2131231025 */:
                i2 = 1;
                break;
            case C0575R.id.tv_k7_sps /* 2131231026 */:
                i = 1;
                i2 = i;
                break;
            default:
                switch (id) {
                    case C0575R.id.tv_x4_lps /* 2131231045 */:
                        i = 2;
                        i2 = i;
                        break;
                    case C0575R.id.tv_x4_sl /* 2131231046 */:
                        i2 = 2;
                        break;
                    case C0575R.id.tv_x4_sps /* 2131231047 */:
                        i2 = 2;
                        i = 1;
                        break;
                    default:
                        i = 0;
                        break;
                }
                break;
        }
        ControlModelActivity1.startActivity(this, i2, i);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        BaseApplication.getInstance().removeBroadcastObserver(this);
        super.onDestroy();
    }

    @Override // androidx.lifecycle.Observer
    public void onChanged(String str) {
        this.stateResult = check();
        checkModel();
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
            return null;
        }
    }
}
