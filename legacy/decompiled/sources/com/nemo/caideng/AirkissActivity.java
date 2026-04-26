package com.nemo.caideng;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.PointerIconCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hjq.permissions.Permission;
import com.nemo.caideng.util.HexStringUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/* JADX INFO: loaded from: classes.dex */
public class AirkissActivity extends AppCompatActivity {
    private static String TAG = "Airkiss";
    private boolean isSendPackage = false;

    @BindView(C0575R.id.et_password)
    EditText mPasswordEt;

    @BindView(C0575R.id.tv_ssid)
    TextView mSSIDTV;

    @BindView(C0575R.id.bt_confirm)
    Button mSendButton;
    private Disposable receiveSubscribe;
    private Disposable sendSubscribe;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C0575R.layout.activity_airkiss);
        ButterKnife.bind(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setLogo(getResources().getDrawable(C0575R.mipmap.ic_launcher));
        supportActionBar.setDisplayUseLogoEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setTitle("  Scics-SmartConfig");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        WifiInfo connectionInfo;
        super.onResume();
        if (checkPermission(this)) {
            Context applicationContext = getApplicationContext();
            if (!((ConnectivityManager) applicationContext.getSystemService("connectivity")).getNetworkInfo(1).isConnected() || (connectionInfo = ((WifiManager) applicationContext.getSystemService("wifi")).getConnectionInfo()) == null) {
                return;
            }
            String ssid = connectionInfo.getSSID();
            if (Build.VERSION.SDK_INT >= 17 && ssid.startsWith("\"") && ssid.endsWith("\"")) {
                ssid = ssid.replaceAll("^\"|\"$", "");
            }
            this.mSSIDTV.setText(ssid);
        }
    }

    public boolean checkPermission(Activity activity) {
        if (Build.VERSION.SDK_INT < 26 || activity.checkSelfPermission(Permission.ACCESS_COARSE_LOCATION) == 0) {
            return true;
        }
        activity.requestPermissions(new String[]{Permission.ACCESS_COARSE_LOCATION}, PointerIconCompat.TYPE_CONTEXT_MENU);
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1001) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("权限拒绝");
                builder.setMessage("请在设置中打开此应用的位置权限后重试");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { // from class: com.nemo.caideng.AirkissActivity.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        AirkissActivity.this.finish();
                    }
                });
                builder.show();
            }
        }
    }

    @OnClick({C0575R.id.bt_confirm})
    public void onViewClicked(View view) {
        if (view.getId() != C0575R.id.bt_confirm) {
            return;
        }
        if (!this.isSendPackage) {
            this.isSendPackage = true;
            onConnectBtnClick(view);
            this.mSendButton.setText("停止");
        } else {
            this.isSendPackage = false;
            this.mSendButton.setText("发送");
        }
    }

    public void onConnectBtnClick(View view) {
        Disposable disposable = this.sendSubscribe;
        if (disposable != null && !disposable.isDisposed()) {
            this.sendSubscribe.dispose();
        }
        Disposable disposable2 = this.receiveSubscribe;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.receiveSubscribe.dispose();
        }
        final String string = this.mSSIDTV.getText().toString();
        final String string2 = this.mPasswordEt.getText().toString();
        if (string.isEmpty() || string2.isEmpty()) {
            Toast.makeText(getApplicationContext(), "请输入wifi密码", 0).show();
        } else {
            Observable.create(new ObservableOnSubscribe<String>() { // from class: com.nemo.caideng.AirkissActivity.3
                @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
                public void subscribe(ObservableEmitter<String> observableEmitter) throws Throwable {
                    observableEmitter.onNext("start");
                    byte[] bArr = new byte[1500];
                    Log.i(AirkissActivity.TAG, " send subscribe start  ");
                    AirKissEncoder airKissEncoder = new AirKissEncoder(string, string2);
                    DatagramSocket datagramSocket = null;
                    try {
                        try {
                            DatagramSocket datagramSocket2 = new DatagramSocket();
                            try {
                                datagramSocket2.setBroadcast(true);
                                int[] encodedData = airKissEncoder.getEncodedData();
                                for (int i = 0; i < 5; i++) {
                                    for (int i2 : encodedData) {
                                        datagramSocket2.send(new DatagramPacket(bArr, i2, InetAddress.getByName("255.255.255.255"), 10000));
                                        Thread.sleep(4L);
                                    }
                                }
                                Log.i(AirkissActivity.TAG, " send subscribe oncmpleted  ");
                                observableEmitter.onComplete();
                                datagramSocket2.close();
                                datagramSocket2.disconnect();
                            } catch (Exception e) {
                                e = e;
                                datagramSocket = datagramSocket2;
                                observableEmitter.onError(e);
                                e.printStackTrace();
                                datagramSocket.close();
                                datagramSocket.disconnect();
                            } catch (Throwable th) {
                                th = th;
                                datagramSocket = datagramSocket2;
                                datagramSocket.close();
                                datagramSocket.disconnect();
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
            }).subscribeOn(Schedulers.m532io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new C05512());
        }
    }

    /* JADX INFO: renamed from: com.nemo.caideng.AirkissActivity$2 */
    class C05512 implements Observer<String> {
        ProgressDialog mDialog;

        C05512() {
            this.mDialog = new ProgressDialog(AirkissActivity.this);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable th) {
            Log.i(AirkissActivity.TAG, "send onError ");
            Toast.makeText(AirkissActivity.this, "连接失败: " + th.getMessage(), 0).show();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            Log.i(AirkissActivity.TAG, "send onCompleted");
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            AirkissActivity.this.sendSubscribe = disposable;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(String str) {
            Log.i(AirkissActivity.TAG, "send onNext =" + str);
            this.mDialog.setTitle("配网");
            this.mDialog.setMessage("发送配网信息...");
            this.mDialog.setCancelable(false);
            this.mDialog.show();
            new Handler().postDelayed(new Runnable() { // from class: com.nemo.caideng.AirkissActivity.2.1
                @Override // java.lang.Runnable
                public void run() {
                    C05512.this.mDialog.dismiss();
                    AirkissActivity.this.receiveUDPPackage();
                }
            }, 5000L);
        }
    }

    public void receiveUDPPackage() {
        Observable.create(new ObservableOnSubscribe<String>() { // from class: com.nemo.caideng.AirkissActivity.5
            @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Throwable {
                String strByteArrayToHex;
                byte[] bArr = new byte[15000];
                DatagramSocket datagramSocket = null;
                try {
                    try {
                        DatagramSocket datagramSocket2 = new DatagramSocket(24333);
                        try {
                            datagramSocket2.setSoTimeout(60000);
                            DatagramPacket datagramPacket = new DatagramPacket(bArr, 15000);
                            do {
                                Log.d(AirkissActivity.TAG, "running");
                                datagramSocket2.receive(datagramPacket);
                                strByteArrayToHex = HexStringUtil.byteArrayToHex(datagramPacket.getData());
                                Log.d("received:", strByteArrayToHex);
                            } while (TextUtils.isEmpty(strByteArrayToHex));
                            Log.d("received:", strByteArrayToHex);
                            observableEmitter.onNext(strByteArrayToHex);
                            observableEmitter.onComplete();
                            Log.i(AirkissActivity.TAG, "receive finally");
                            datagramSocket2.close();
                            datagramSocket2.disconnect();
                        } catch (SocketException e) {
                            e = e;
                            datagramSocket = datagramSocket2;
                            Log.i(AirkissActivity.TAG, "receive socket exception");
                            observableEmitter.onError(e);
                            e.printStackTrace();
                            Log.i(AirkissActivity.TAG, "receive finally");
                            datagramSocket.close();
                            datagramSocket.disconnect();
                        } catch (IOException e2) {
                            e = e2;
                            datagramSocket = datagramSocket2;
                            Log.i(AirkissActivity.TAG, "receive io exception");
                            observableEmitter.onError(e);
                            e.printStackTrace();
                            Log.i(AirkissActivity.TAG, "receive finally");
                            datagramSocket.close();
                            datagramSocket.disconnect();
                        } catch (Throwable th) {
                            th = th;
                            datagramSocket = datagramSocket2;
                            Log.i(AirkissActivity.TAG, "receive finally");
                            datagramSocket.close();
                            datagramSocket.disconnect();
                            throw th;
                        }
                    } catch (SocketException e3) {
                        e = e3;
                    } catch (IOException e4) {
                        e = e4;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() { // from class: com.nemo.caideng.AirkissActivity.4
            ProgressDialog mDialog;

            {
                this.mDialog = new ProgressDialog(AirkissActivity.this);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable th) {
                Log.i(AirkissActivity.TAG, "receive onError");
                Toast.makeText(AirkissActivity.this, "连接失败: " + th.getMessage(), 0).show();
                this.mDialog.dismiss();
                AirkissActivity.this.mSendButton.performClick();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                this.mDialog.dismiss();
                AirkissActivity.this.mSendButton.performClick();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable disposable) {
                AirkissActivity.this.receiveSubscribe = disposable;
                this.mDialog.setTitle("配网");
                this.mDialog.setMessage("正在连接...");
                this.mDialog.setCancelable(false);
                this.mDialog.show();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(String str) {
                Log.i(AirkissActivity.TAG, "receive onNext");
                Toast.makeText(AirkissActivity.this, "收到的UDP包：" + str, 0).show();
            }
        });
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        Log.i(TAG, "onStop ");
        super.onStop();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        Disposable disposable = this.sendSubscribe;
        if (disposable != null && !disposable.isDisposed()) {
            this.sendSubscribe.dispose();
        }
        Disposable disposable2 = this.receiveSubscribe;
        if (disposable2 != null && disposable2.isDisposed()) {
            this.receiveSubscribe.dispose();
        }
        this.mSendButton.performClick();
    }
}
