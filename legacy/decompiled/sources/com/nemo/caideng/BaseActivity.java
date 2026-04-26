package com.nemo.caideng;

import android.graphics.Color;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.location.LocationManagerCompat;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.nemo.caideng.util.TouchNetUtil;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BaseActivity extends AppCompatActivity implements OnPermissionCallback {
    private WifiManager mWifiManager;
    private String[] permissions = {Permission.ACCESS_FINE_LOCATION};

    @Override // com.hjq.permissions.OnPermissionCallback
    public void onDenied(List<String> list, boolean z) {
    }

    @Override // com.hjq.permissions.OnPermissionCallback
    public void onGranted(List<String> list, boolean z) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mWifiManager = (WifiManager) getApplicationContext().getSystemService("wifi");
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowTitleEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected StateResult check() {
        StateResult stateResultCheckPermission = checkPermission();
        if (!stateResultCheckPermission.permissionGranted) {
            return stateResultCheckPermission;
        }
        StateResult stateResultCheckLocation = checkLocation();
        stateResultCheckLocation.permissionGranted = true;
        if (stateResultCheckLocation.locationRequirement) {
            return stateResultCheckLocation;
        }
        StateResult stateResultCheckWifi = checkWifi();
        stateResultCheckWifi.permissionGranted = true;
        stateResultCheckWifi.locationRequirement = false;
        return stateResultCheckWifi;
    }

    protected StateResult checkPermission() {
        StateResult stateResult = new StateResult();
        stateResult.permissionGranted = false;
        if (Build.VERSION.SDK_INT >= 23) {
            if (!(checkSelfPermission(Permission.ACCESS_FINE_LOCATION) == 0)) {
                String[] strArrSplit = getString(C0575R.string.esptouch_message_permission).split("\n");
                if (strArrSplit.length != 2) {
                    throw new IllegalArgumentException("Invalid String @RES esptouch_message_permission");
                }
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(strArrSplit[0]);
                spannableStringBuilder.append('\n');
                SpannableString spannableString = new SpannableString(strArrSplit[1]);
                spannableString.setSpan(new ClickableSpan() { // from class: com.nemo.caideng.BaseActivity.1
                    @Override // android.text.style.ClickableSpan
                    public void onClick(View view) {
                        XXPermissions.with(BaseActivity.this).permission(BaseActivity.this.permissions).request(BaseActivity.this);
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(Color.parseColor("#0022FF"));
                        textPaint.setUnderlineText(false);
                    }
                }, 0, spannableString.length(), 18);
                spannableStringBuilder.append((CharSequence) spannableString);
                stateResult.message = spannableStringBuilder;
                return stateResult;
            }
        }
        stateResult.permissionGranted = true;
        return stateResult;
    }

    protected StateResult checkLocation() {
        StateResult stateResult = new StateResult();
        stateResult.locationRequirement = true;
        if (Build.VERSION.SDK_INT >= 28) {
            LocationManager locationManager = (LocationManager) getSystemService(LocationManager.class);
            if (!(locationManager != null && LocationManagerCompat.isLocationEnabled(locationManager))) {
                stateResult.message = getString(C0575R.string.esptouch_message_location);
                return stateResult;
            }
        }
        stateResult.locationRequirement = false;
        return stateResult;
    }

    protected StateResult checkWifi() {
        StateResult stateResult = new StateResult();
        stateResult.wifiConnected = false;
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        if (!TouchNetUtil.isWifiConnected(this.mWifiManager)) {
            stateResult.message = getString(C0575R.string.esptouch_message_wifi_connection);
            return stateResult;
        }
        String ssidString = TouchNetUtil.getSsidString(connectionInfo);
        if (connectionInfo.getIpAddress() != 0) {
            stateResult.address = TouchNetUtil.getAddress(connectionInfo.getIpAddress());
        } else {
            stateResult.address = TouchNetUtil.getIPv4Address();
            if (stateResult.address == null) {
                stateResult.address = TouchNetUtil.getIPv6Address();
            }
        }
        int i = this.mWifiManager.getDhcpInfo().serverAddress;
        if (i != 0) {
            stateResult.serverAddress = TouchNetUtil.getAddress(i);
        }
        stateResult.wifiConnected = true;
        stateResult.message = "";
        stateResult.is5G = TouchNetUtil.is5G(connectionInfo.getFrequency());
        if (stateResult.is5G) {
            stateResult.message = getString(C0575R.string.esptouch_message_wifi_frequency);
        }
        stateResult.ssid = ssidString;
        stateResult.ssidBytes = TouchNetUtil.getRawSsidBytesOrElse(connectionInfo, ssidString.getBytes());
        stateResult.bssid = connectionInfo.getBSSID();
        return stateResult;
    }

    private String intToIp(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }

    private String intToIpServer(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + ".1";
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            back();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void back() {
        finish();
    }
}
