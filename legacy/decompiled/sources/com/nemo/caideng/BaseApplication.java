package com.nemo.caideng;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.nemo.caideng.util.SharedPreferencesUtil;
import com.tencent.bugly.crashreport.CrashReport;

/* JADX INFO: loaded from: classes.dex */
public class BaseApplication extends Application {
    private static BaseApplication instance;
    private SharedPreferencesUtil dataStore;
    private MutableLiveData<String> mBroadcastData;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.nemo.caideng.BaseApplication.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            byte b = -1;
            int iHashCode = action.hashCode();
            if (iHashCode != -1184851779) {
                if (iHashCode == -343630553 && action.equals("android.net.wifi.STATE_CHANGE")) {
                    b = 0;
                }
            } else if (action.equals("android.location.PROVIDERS_CHANGED")) {
                b = 1;
            }
            if (b == 0 || b == 1) {
                BaseApplication.this.mBroadcastData.setValue(action);
            }
        }
    };
    public int screenH;
    public int screenW;

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        instance = this;
        initDispaly();
        initBugly();
        initBroadcast();
        initDataStore();
    }

    private void initDataStore() {
        this.dataStore = SharedPreferencesUtil.getInstance(this);
    }

    private void initBroadcast() {
        this.mBroadcastData = new MutableLiveData<>();
        IntentFilter intentFilter = new IntentFilter("android.net.wifi.STATE_CHANGE");
        if (Build.VERSION.SDK_INT >= 28) {
            intentFilter.addAction("android.location.PROVIDERS_CHANGED");
        }
        registerReceiver(this.mReceiver, intentFilter);
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(this.mReceiver);
    }

    public void observeBroadcast(LifecycleOwner lifecycleOwner, Observer<String> observer) {
        this.mBroadcastData.observe(lifecycleOwner, observer);
    }

    public void observeBroadcastForever(Observer<String> observer) {
        this.mBroadcastData.observeForever(observer);
    }

    public void removeBroadcastObserver(Observer<String> observer) {
        this.mBroadcastData.removeObserver(observer);
    }

    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), "f8ea30a429", true);
    }

    private void initDispaly() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.screenW = displayMetrics.widthPixels;
        this.screenH = displayMetrics.heightPixels;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public SharedPreferencesUtil getDataStore() {
        return this.dataStore;
    }
}
