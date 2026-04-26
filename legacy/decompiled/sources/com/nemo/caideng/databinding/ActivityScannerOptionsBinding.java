package com.nemo.caideng.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import com.mylhyl.zxing.scanner.ScannerView;
import com.nemo.caideng.C0575R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityScannerOptionsBinding implements ViewBinding {
    public final ImageView ivPicture;
    private final RelativeLayout rootView;
    public final ScannerView scannerView;

    private ActivityScannerOptionsBinding(RelativeLayout relativeLayout, ImageView imageView, ScannerView scannerView) {
        this.rootView = relativeLayout;
        this.ivPicture = imageView;
        this.scannerView = scannerView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ActivityScannerOptionsBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityScannerOptionsBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(C0575R.layout.activity_scanner_options, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityScannerOptionsBinding bind(View view) {
        int i = C0575R.id.iv_picture;
        ImageView imageView = (ImageView) view.findViewById(C0575R.id.iv_picture);
        if (imageView != null) {
            i = C0575R.id.scanner_view;
            ScannerView scannerView = (ScannerView) view.findViewById(C0575R.id.scanner_view);
            if (scannerView != null) {
                return new ActivityScannerOptionsBinding((RelativeLayout) view, imageView, scannerView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
