package per.goweii.burred;

import android.graphics.Bitmap;
import android.view.View;
import per.goweii.burred.Blurred;

/* JADX INFO: loaded from: classes.dex */
public class DefaultSnapshotInterceptor implements Blurred.SnapshotInterceptor {
    @Override // per.goweii.burred.Blurred.SnapshotInterceptor
    public Bitmap snapshot(View view, int i, int i2, float f, boolean z) {
        return BitmapProcessor.get().snapshot(view, i, i2, f, z);
    }
}
