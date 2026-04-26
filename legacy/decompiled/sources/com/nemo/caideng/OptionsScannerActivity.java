package com.nemo.caideng;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.ScannerView;
import com.mylhyl.zxing.scanner.decode.QRDecode;
import com.nemo.caideng.util.ToastMaker;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class OptionsScannerActivity extends Activity implements OnScannerCompletionListener, OnPermissionCallback {
    private ImageView ivPicture;
    private ScannerView mScannerView;
    private String[] permissions = {Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE};

    @Override // com.hjq.permissions.OnPermissionCallback
    public /* synthetic */ void onDenied(List<String> list, boolean z) {
        OnPermissionCallback.CC.$default$onDenied(this, list, z);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(C0575R.layout.activity_scanner_options);
        ScannerView scannerView = (ScannerView) findViewById(C0575R.id.scanner_view);
        this.mScannerView = scannerView;
        scannerView.setOnScannerCompletionListener(this);
        ImageView imageView = (ImageView) findViewById(C0575R.id.iv_picture);
        this.ivPicture = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.nemo.caideng.-$$Lambda$OptionsScannerActivity$s-B98WZO9VLJqNRdvUbZQpBGivc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$0$OptionsScannerActivity(view);
            }
        });
        initScannerView();
        initPermissions();
    }

    public /* synthetic */ void lambda$onCreate$0$OptionsScannerActivity(View view) {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 2);
    }

    private void initPermissions() {
        if (XXPermissions.isGranted(this, this.permissions)) {
            return;
        }
        XXPermissions.with(this).permission(this.permissions).request(this);
    }

    private void initScannerView() {
        Drawable drawableMutate = DrawableCompat.wrap(ContextCompat.getDrawable(this, C0575R.mipmap.wx_scan_line)).mutate();
        DrawableCompat.setTint(drawableMutate, getColor(C0575R.color.colorPrimaryDark));
        ScannerOptions.Builder builder = new ScannerOptions.Builder();
        builder.setFrameStrokeColor(getColor(C0575R.color.colorPrimary)).setFrameStrokeWidth(1.5f).setFrameSize(320, 320).setFrameCornerLength(22).setFrameCornerWidth(2).setFrameCornerColor(getColor(C0575R.color.colorPrimaryDark)).setFrameCornerInside(false).setLaserLine(ScannerOptions.LaserStyle.DRAWABLE_LINE, drawableMutate).setScanMode(BarcodeFormat.QR_CODE).setTipTextSize(18).setTipTextColor(getResources().getColor(C0575R.color.colorAccent));
        this.mScannerView.setScannerOptions(builder.build());
    }

    @Override // android.app.Activity
    protected void onResume() {
        this.mScannerView.onResume();
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onPause() {
        this.mScannerView.onPause();
        super.onPause();
    }

    @Override // com.mylhyl.zxing.scanner.OnScannerCompletionListener
    public void onScannerCompletion(Result result, ParsedResult parsedResult, Bitmap bitmap) {
        if (result != null) {
            vibrate();
            this.mScannerView.restartPreviewAfterDelay(0L);
            Intent intent = new Intent();
            intent.putExtra("content", result.getText());
            setResult(-1, intent);
            finish();
            return;
        }
        vibrate();
        this.mScannerView.restartPreviewAfterDelay(0L);
        ToastMaker.showShortToast(getString(C0575R.string.scanner_code_fail));
    }

    private void vibrate() {
        ((Vibrator) getSystemService("vibrator")).vibrate(200L);
    }

    @Override // com.hjq.permissions.OnPermissionCallback
    public void onGranted(List<String> list, boolean z) {
        this.mScannerView.restartPreviewAfterDelay(0L);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 2) {
            Log.e(getClass().getName(), "Result:" + intent.toString());
            if (Build.VERSION.SDK_INT >= 19) {
                handleImageOnKitKat(intent);
            } else {
                handleImageBeforeKitKat(intent);
            }
        }
    }

    private void handleImageOnKitKat(Intent intent) {
        String imagePath;
        Uri data = intent.getData();
        String path = null;
        if (DocumentsContract.isDocumentUri(this, data)) {
            String documentId = DocumentsContract.getDocumentId(data);
            if ("com.android.providers.media.documents".equals(data.getAuthority())) {
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_id=" + documentId.split(":")[1]);
            } else if ("com.android.providers.downloads.documents".equals(data.getAuthority())) {
                imagePath = getImagePath(ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId).longValue()), null);
            }
            path = imagePath;
        } else if ("content".equalsIgnoreCase(data.getScheme())) {
            path = getImagePath(data, null);
        } else if ("file".equalsIgnoreCase(data.getScheme())) {
            path = data.getPath();
        }
        QRDecode.decodeQR(path, this);
    }

    private void handleImageBeforeKitKat(Intent intent) {
        QRDecode.decodeQR(getImagePath(intent.getData(), null), this);
    }

    private String getImagePath(Uri uri, String str) {
        Cursor cursorQuery = getContentResolver().query(uri, null, str, null, null);
        if (cursorQuery != null) {
            string = cursorQuery.moveToFirst() ? cursorQuery.getString(cursorQuery.getColumnIndex("_data")) : null;
            cursorQuery.close();
        }
        return string;
    }
}
