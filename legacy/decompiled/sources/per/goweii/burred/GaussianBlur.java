package per.goweii.burred;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RSInvalidStateException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/* JADX INFO: loaded from: classes.dex */
public final class GaussianBlur implements IBlur {
    private static GaussianBlur INSTANCE;
    private final ScriptIntrinsicBlur gaussianBlur;
    private final RenderScript renderScript;
    private boolean mRealTimeMode = false;
    private Allocation mInput = null;
    private Allocation mOutput = null;

    private GaussianBlur(Context context) {
        Utils.requireNonNull(context);
        if (Build.VERSION.SDK_INT < 17) {
            throw new RuntimeException("Call requires API level 17 (current min is " + Build.VERSION.SDK_INT + ")");
        }
        RenderScript renderScriptCreate = RenderScript.create(context.getApplicationContext());
        this.renderScript = renderScriptCreate;
        this.gaussianBlur = ScriptIntrinsicBlur.create(renderScriptCreate, Element.U8_4(renderScriptCreate));
    }

    public static GaussianBlur get(Context context) {
        if (INSTANCE == null) {
            synchronized (GaussianBlur.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GaussianBlur(context);
                }
            }
        }
        return INSTANCE;
    }

    public GaussianBlur realTimeMode(boolean z) {
        this.mRealTimeMode = z;
        if (!z) {
            destroyAllocations();
        }
        BitmapProcessor.get().realTimeMode(z);
        return this;
    }

    @Override // per.goweii.burred.IBlur
    public Bitmap process(Bitmap bitmap, float f, float f2, boolean z, boolean z2) {
        Utils.requireNonNull(bitmap, "待模糊Bitmap不能为空");
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f2 <= 0.0f) {
            f2 = 1.0f;
        }
        if (f == 0.0f) {
            if (f2 == 1.0f) {
                return bitmap;
            }
            Bitmap bitmapScaleBitmap = BitmapProcessor.get().scaleBitmap(bitmap, f2);
            if (z2) {
                bitmap.recycle();
            }
            return bitmapScaleBitmap;
        }
        if (f > 25.0f) {
            f2 /= f / 25.0f;
            f = 25.0f;
        }
        if (f2 == 1.0f) {
            Bitmap bitmapBlurIn25 = blurIn25(bitmap, f);
            if (z2) {
                bitmap.recycle();
            }
            return bitmapBlurIn25;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmapScaleBitmap2 = BitmapProcessor.get().scaleBitmap(bitmap, f2);
        if (z2) {
            bitmap.recycle();
        }
        Bitmap bitmapBlurIn252 = blurIn25(bitmapScaleBitmap2, f);
        bitmapScaleBitmap2.recycle();
        if (!z) {
            return bitmapBlurIn252;
        }
        Bitmap bitmapScaleBitmap3 = BitmapProcessor.get().scaleBitmap(bitmapBlurIn252, width, height);
        bitmapBlurIn252.recycle();
        return bitmapScaleBitmap3;
    }

    @Override // per.goweii.burred.IBlur
    public void recycle() {
        this.gaussianBlur.destroy();
        this.renderScript.destroy();
        destroyAllocations();
        INSTANCE = null;
    }

    private Bitmap blurIn25(Bitmap bitmap, float f) {
        Utils.requireNonNull(bitmap, "待模糊Bitmap不能为空");
        if (Build.VERSION.SDK_INT < 17) {
            throw new RuntimeException("Call requires API level 17 (current min is " + Build.VERSION.SDK_INT + ")");
        }
        if (f < 0.0f) {
            f = 0.0f;
        } else if (f > 25.0f) {
            f = 25.0f;
        }
        if (this.mRealTimeMode) {
            tryReuseAllocation(bitmap);
        } else {
            createAllocation(bitmap);
        }
        try {
            this.gaussianBlur.setRadius(f);
            this.gaussianBlur.setInput(this.mInput);
            this.gaussianBlur.forEach(this.mOutput);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            this.mOutput.copyTo(bitmapCreateBitmap);
            return bitmapCreateBitmap;
        } finally {
            if (!this.mRealTimeMode) {
                destroyAllocations();
            }
        }
    }

    private void destroyAllocations() {
        Allocation allocation = this.mInput;
        if (allocation != null) {
            try {
                allocation.destroy();
                this.mInput = null;
            } catch (RSInvalidStateException unused) {
            }
        }
        Allocation allocation2 = this.mOutput;
        if (allocation2 != null) {
            try {
                allocation2.destroy();
                this.mOutput = null;
            } catch (RSInvalidStateException unused2) {
            }
        }
    }

    private void tryReuseAllocation(Bitmap bitmap) {
        if (this.mInput == null) {
            createAllocation(bitmap);
        }
        if (this.mInput.getType().getX() != bitmap.getWidth() || this.mInput.getType().getY() != bitmap.getHeight()) {
            createAllocation(bitmap);
        }
        try {
            this.mInput.copyFrom(bitmap);
        } catch (RSIllegalArgumentException unused) {
            destroyAllocations();
            createAllocation(bitmap);
        }
    }

    private void createAllocation(Bitmap bitmap) {
        destroyAllocations();
        Allocation allocationCreateFromBitmap = Allocation.createFromBitmap(this.renderScript, bitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
        this.mInput = allocationCreateFromBitmap;
        this.mOutput = Allocation.createTyped(this.renderScript, allocationCreateFromBitmap.getType());
    }
}
