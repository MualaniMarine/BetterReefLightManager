package com.mylhyl.zxing.scanner.camera;

import android.hardware.Camera;
import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
class SizeComparator implements Comparator<Camera.Size> {
    private final int height;
    private final float ratio;
    private final int width;

    SizeComparator(int i, int i2) {
        if (i < i2) {
            this.width = i2;
            this.height = i;
        } else {
            this.width = i;
            this.height = i2;
        }
        this.ratio = this.height / this.width;
    }

    @Override // java.util.Comparator
    public int compare(Camera.Size size, Camera.Size size2) {
        int i = size.width;
        int i2 = size.height;
        int i3 = size2.width;
        int i4 = size2.height;
        int iCompare = Float.compare(Math.abs((i2 / i) - this.ratio), Math.abs((i4 / i3) - this.ratio));
        return iCompare != 0 ? iCompare : (Math.abs(this.width - i) + Math.abs(this.height - i2)) - (Math.abs(this.width - i3) + Math.abs(this.height - i4));
    }
}
