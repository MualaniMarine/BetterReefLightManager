package per.goweii.burred;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes.dex */
public interface IBlur {
    Bitmap process(Bitmap bitmap, float f, float f2, boolean z, boolean z2);

    void recycle();
}
