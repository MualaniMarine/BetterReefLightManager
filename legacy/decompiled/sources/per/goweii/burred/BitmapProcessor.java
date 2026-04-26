package per.goweii.burred;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
class BitmapProcessor {
    private static BitmapProcessor INSTANCE;
    private boolean mRealTimeMode = false;
    private PaintFlagsDrawFilter mFilter = null;
    private Paint mPaint = null;
    private Canvas mCanvas = null;

    static BitmapProcessor get() {
        if (INSTANCE == null) {
            synchronized (BitmapProcessor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BitmapProcessor();
                }
            }
        }
        return INSTANCE;
    }

    private BitmapProcessor() {
    }

    void realTimeMode(boolean z) {
        this.mRealTimeMode = z;
        if (z) {
            prepare();
        } else {
            recycle();
        }
    }

    private void prepare() {
        if (this.mFilter == null) {
            this.mFilter = new PaintFlagsDrawFilter(0, 3);
        }
        if (this.mPaint == null) {
            this.mPaint = new Paint();
        }
        if (this.mCanvas == null) {
            this.mCanvas = new Canvas();
        }
    }

    private void recycle() {
        this.mFilter = null;
        this.mPaint = null;
        this.mCanvas = null;
    }

    private Canvas reuseOrCreateCanvas() {
        if (this.mRealTimeMode) {
            prepare();
            return this.mCanvas;
        }
        return new Canvas();
    }

    private PaintFlagsDrawFilter reuseOrCreateFilter() {
        if (this.mRealTimeMode) {
            prepare();
            return this.mFilter;
        }
        return new PaintFlagsDrawFilter(0, 3);
    }

    private Paint reuseOrCreatePaint() {
        if (this.mRealTimeMode) {
            prepare();
            return this.mPaint;
        }
        return new Paint();
    }

    Bitmap snapshot(View view, int i, int i2, float f, boolean z) {
        if (f <= 0.0f) {
            f = 1.0f;
        }
        int width = (int) (view.getWidth() * f);
        int height = (int) (view.getHeight() * f);
        if (width <= 0) {
            width = 1;
        }
        if (height <= 0) {
            height = 1;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvasReuseOrCreateCanvas = reuseOrCreateCanvas();
        canvasReuseOrCreateCanvas.setBitmap(bitmapCreateBitmap);
        if (z) {
            canvasReuseOrCreateCanvas.setDrawFilter(reuseOrCreateFilter());
        } else {
            canvasReuseOrCreateCanvas.setDrawFilter(null);
        }
        canvasReuseOrCreateCanvas.save();
        canvasReuseOrCreateCanvas.scale(f, f);
        if (i != 0) {
            canvasReuseOrCreateCanvas.drawColor(i);
        }
        view.draw(canvasReuseOrCreateCanvas);
        if (i2 != 0) {
            canvasReuseOrCreateCanvas.drawColor(i2);
        }
        canvasReuseOrCreateCanvas.restore();
        return bitmapCreateBitmap;
    }

    Bitmap clip(Bitmap bitmap, View view, ImageView imageView, boolean z, boolean z2) {
        view.getLocationOnScreen(new int[2]);
        imageView.getLocationOnScreen(new int[2]);
        int width = bitmap.getWidth();
        float width2 = width / view.getWidth();
        float height = bitmap.getHeight() / view.getHeight();
        Rect rect = new Rect((int) ((r0[0] - r1[0]) * width2), (int) ((r0[1] - r1[1]) * height), (int) (((r0[0] - r1[0]) * width2) + (imageView.getWidth() * width2)), (int) (((r0[1] - r1[1]) * height) + (imageView.getHeight() * height)));
        Rect rect2 = new Rect(0, 0, imageView.getWidth(), imageView.getHeight());
        if (!z && Math.max(imageView.getWidth() / rect.width(), imageView.getHeight() / rect.height()) > 1.0f) {
            rect2.right = rect.width();
            rect2.bottom = rect.height();
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(rect2.width(), rect2.height(), Bitmap.Config.ARGB_8888);
        Canvas canvasReuseOrCreateCanvas = reuseOrCreateCanvas();
        canvasReuseOrCreateCanvas.setBitmap(bitmapCreateBitmap);
        Paint paint = null;
        if (z2) {
            canvasReuseOrCreateCanvas.setDrawFilter(reuseOrCreateFilter());
        } else {
            canvasReuseOrCreateCanvas.setDrawFilter(null);
        }
        if (z2) {
            Paint paintReuseOrCreatePaint = reuseOrCreatePaint();
            paintReuseOrCreatePaint.setXfermode(null);
            paintReuseOrCreatePaint.setAntiAlias(true);
            paint = paintReuseOrCreatePaint;
        }
        canvasReuseOrCreateCanvas.drawBitmap(bitmap, rect, rect2, paint);
        return bitmapCreateBitmap;
    }

    private static class StopDrawException extends Exception {
        private StopDrawException() {
        }
    }

    Bitmap scaleBitmap(Bitmap bitmap, float f) {
        return scaleBitmap(bitmap, f, true);
    }

    Bitmap scaleBitmap(Bitmap bitmap, float f, boolean z) {
        return scaleBitmap(bitmap, (int) (bitmap.getWidth() * f), (int) (bitmap.getHeight() * f), z);
    }

    Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
        return scaleBitmap(bitmap, i, i2, true);
    }

    Bitmap scaleBitmap(Bitmap bitmap, int i, int i2, boolean z) {
        Bitmap.Config config;
        int i3 = C08181.$SwitchMap$android$graphics$Bitmap$Config[bitmap.getConfig().ordinal()];
        if (i3 == 1) {
            config = Bitmap.Config.RGB_565;
        } else if (i3 == 2) {
            config = Bitmap.Config.ALPHA_8;
        } else {
            config = Bitmap.Config.ARGB_8888;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, config);
        Canvas canvasReuseOrCreateCanvas = reuseOrCreateCanvas();
        canvasReuseOrCreateCanvas.setBitmap(bitmapCreateBitmap);
        Paint paint = null;
        if (z) {
            canvasReuseOrCreateCanvas.setDrawFilter(reuseOrCreateFilter());
        } else {
            canvasReuseOrCreateCanvas.setDrawFilter(null);
        }
        if (z) {
            Paint paintReuseOrCreatePaint = reuseOrCreatePaint();
            paintReuseOrCreatePaint.setXfermode(null);
            paintReuseOrCreatePaint.setAntiAlias(true);
            paint = paintReuseOrCreatePaint;
        }
        canvasReuseOrCreateCanvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, i, i2), paint);
        return bitmapCreateBitmap;
    }

    /* JADX INFO: renamed from: per.goweii.burred.BitmapProcessor$1 */
    static /* synthetic */ class C08181 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$Config;

        static {
            int[] iArr = new int[Bitmap.Config.values().length];
            $SwitchMap$android$graphics$Bitmap$Config = iArr;
            try {
                iArr[Bitmap.Config.RGB_565.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ALPHA_8.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ARGB_4444.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ARGB_8888.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }
}
