package per.goweii.burred;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public final class Blurred {
    private static final Float MAX_FPS = Float.valueOf(60.0f);
    private static IBlur sBlur;
    private static ExecutorService sExecutor;
    private long mLastFrameTime = 0;
    private float mPercent = 0.0f;
    private float mRadius = 0.0f;
    private float mScale = 1.0f;
    private boolean mAntiAlias = false;
    private boolean mKeepSize = false;
    private boolean mFitIntoViewXY = false;
    private boolean mRecycleOriginal = false;
    private float mMaxFps = MAX_FPS.floatValue();
    private ViewTreeObserver.OnPreDrawListener mOnPreDrawListener = null;
    private ViewTreeObserver.OnDrawListener mOnDrawListener = null;
    private int mBackgroundColor = 0;
    private int mForegroundColor = 0;
    private Bitmap mOriginalBitmap = null;
    private View mViewFrom = null;
    private ImageView mViewInto = null;
    private SnapshotInterceptor mSnapshotInterceptor = null;
    private FpsListener mFpsListener = null;
    private Listener mListener = null;
    private Callback mCallback = null;
    private Handler mCallbackHandler = null;

    public interface Callback {
        void down(Bitmap bitmap);
    }

    public interface FpsListener {
        void currFps(float f);
    }

    public interface Listener {
        void begin();

        void end();
    }

    public interface SnapshotInterceptor {
        Bitmap snapshot(View view, int i, int i2, float f, boolean z);
    }

    public static void init(Context context) {
        if (sBlur == null) {
            if (Build.VERSION.SDK_INT >= 17) {
                sBlur = GaussianBlur.get(context);
            } else {
                sBlur = FastBlur.get();
            }
        }
    }

    public static void recycle() {
        IBlur iBlur = sBlur;
        if (iBlur != null) {
            iBlur.recycle();
            sBlur = null;
        }
        BitmapProcessor.get().realTimeMode(false);
        ExecutorService executorService = sExecutor;
        if (executorService != null) {
            if (!executorService.isShutdown()) {
                sExecutor.shutdown();
            }
            sExecutor = null;
        }
    }

    public static void realTimeMode(boolean z) {
        if (requireBlur() instanceof GaussianBlur) {
            ((GaussianBlur) sBlur).realTimeMode(z);
        }
        BitmapProcessor.get().realTimeMode(z);
    }

    private static IBlur requireBlur() {
        return (IBlur) Utils.requireNonNull(sBlur, "Blurred未初始化");
    }

    private static ExecutorService requireExecutor() {
        ExecutorService executorService = sExecutor;
        if (executorService == null || executorService.isShutdown()) {
            sExecutor = Executors.newSingleThreadExecutor();
        }
        return sExecutor;
    }

    public static Blurred with(Bitmap bitmap) {
        return new Blurred().bitmap(bitmap);
    }

    public static Blurred with(View view) {
        return new Blurred().view(view);
    }

    public void reset() {
        this.mMaxFps = MAX_FPS.floatValue();
        this.mPercent = 0.0f;
        this.mRadius = 0.0f;
        this.mScale = 1.0f;
        this.mKeepSize = false;
        this.mAntiAlias = false;
        this.mFitIntoViewXY = false;
        this.mRecycleOriginal = false;
        this.mOriginalBitmap = null;
        View view = this.mViewFrom;
        if (view != null) {
            if (this.mOnPreDrawListener != null) {
                view.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
                this.mOnPreDrawListener = null;
            }
            this.mViewFrom = null;
        }
        this.mViewInto = null;
        this.mBackgroundColor = 0;
        this.mForegroundColor = 0;
    }

    public Blurred view(View view) {
        reset();
        this.mViewFrom = view;
        return this;
    }

    public Blurred bitmap(Bitmap bitmap) {
        reset();
        this.mOriginalBitmap = bitmap;
        return this;
    }

    public Blurred suggestConfig() {
        this.mMaxFps = 60.0f;
        this.mPercent = 0.0f;
        this.mRadius = 10.0f;
        this.mScale = 8.0f;
        this.mKeepSize = false;
        this.mAntiAlias = false;
        this.mFitIntoViewXY = false;
        this.mRecycleOriginal = false;
        return this;
    }

    public Blurred backgroundColor(int i) {
        this.mBackgroundColor = i;
        return this;
    }

    public Blurred foregroundColor(int i) {
        this.mForegroundColor = i;
        return this;
    }

    public Blurred percent(float f) {
        this.mPercent = f;
        return this;
    }

    public Blurred radius(float f) {
        this.mRadius = f;
        return this;
    }

    public Blurred scale(float f) {
        this.mScale = f;
        return this;
    }

    public Blurred maxFps(float f) {
        this.mMaxFps = f;
        return this;
    }

    public Blurred keepSize(boolean z) {
        this.mKeepSize = z;
        return this;
    }

    public Blurred fitIntoViewXY(boolean z) {
        this.mFitIntoViewXY = z;
        return this;
    }

    public Blurred antiAlias(boolean z) {
        this.mAntiAlias = z;
        return this;
    }

    public Blurred recycleOriginal(boolean z) {
        this.mRecycleOriginal = z;
        return this;
    }

    public Blurred snapshotInterceptor(SnapshotInterceptor snapshotInterceptor) {
        this.mSnapshotInterceptor = snapshotInterceptor;
        return this;
    }

    public Blurred fpsListener(FpsListener fpsListener) {
        this.mFpsListener = fpsListener;
        return this;
    }

    public Blurred listener(Listener listener) {
        this.mListener = listener;
        return this;
    }

    public Bitmap blur() {
        float fMin;
        float f;
        Bitmap bitmapProcess;
        if (this.mViewFrom == null && this.mOriginalBitmap == null) {
            throw new NullPointerException("待模糊View和Bitmap不能同时为空");
        }
        Listener listener = this.mListener;
        if (listener != null) {
            listener.begin();
        }
        float f2 = this.mScale;
        if (f2 <= 0.0f) {
            f2 = 1.0f;
        }
        float f3 = f2;
        if (this.mPercent <= 0.0f) {
            fMin = this.mRadius;
        } else {
            View view = this.mViewFrom;
            int width = view != null ? view.getWidth() : this.mOriginalBitmap.getWidth();
            fMin = Math.min(width, this.mViewFrom != null ? r1.getHeight() : this.mOriginalBitmap.getHeight()) * this.mPercent;
        }
        float f4 = fMin;
        if (this.mViewFrom == null) {
            bitmapProcess = requireBlur().process(this.mOriginalBitmap, f4, f3, this.mKeepSize, this.mRecycleOriginal);
        } else {
            if (f4 > 25.0f) {
                f3 /= f4 / 25.0f;
                f = 25.0f;
            } else {
                f = f4;
            }
            bitmapProcess = requireBlur().process(checkSnapshotInterceptor().snapshot(this.mViewFrom, this.mBackgroundColor, this.mForegroundColor, f3, this.mAntiAlias), f, 1.0f, this.mKeepSize, this.mRecycleOriginal);
        }
        Listener listener2 = this.mListener;
        if (listener2 != null) {
            listener2.end();
        }
        return bitmapProcess;
    }

    public void blur(Callback callback) {
        Utils.requireNonNull(callback, "Callback不能为空");
        this.mCallback = callback;
        this.mCallbackHandler = new Handler(Looper.getMainLooper()) { // from class: per.goweii.burred.Blurred.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                Blurred.this.mCallbackHandler = null;
                Blurred.this.mCallback.down((Bitmap) message.obj);
            }
        };
        requireExecutor().submit(new Runnable() { // from class: per.goweii.burred.Blurred.2
            @Override // java.lang.Runnable
            public void run() {
                Bitmap bitmapBlur = Blurred.this.blur();
                Message messageObtainMessage = Blurred.this.mCallbackHandler.obtainMessage();
                messageObtainMessage.obj = bitmapBlur;
                Blurred.this.mCallbackHandler.sendMessage(messageObtainMessage);
            }
        });
    }

    public void blur(ImageView imageView) {
        Utils.requireNonNull(this.mViewFrom, "实时高斯模糊时待模糊View不能为空");
        Utils.requireNonNull(imageView, "ImageView不能为空");
        this.mViewInto = imageView;
        if (this.mOnPreDrawListener == null) {
            this.mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: per.goweii.burred.Blurred.3
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    if (Blurred.this.mViewInto == null) {
                        return true;
                    }
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    float f = 1000.0f / (jCurrentTimeMillis - Blurred.this.mLastFrameTime);
                    if (f > Blurred.this.mMaxFps) {
                        return true;
                    }
                    Blurred.this.mLastFrameTime = jCurrentTimeMillis;
                    if (Blurred.this.mFpsListener != null) {
                        Blurred.this.mFpsListener.currFps(f);
                    }
                    Blurred.realTimeMode(true);
                    Blurred.this.keepSize(false);
                    Blurred.this.recycleOriginal(true);
                    Bitmap bitmapBlur = Blurred.this.blur();
                    Bitmap bitmapClip = BitmapProcessor.get().clip(bitmapBlur, Blurred.this.mViewFrom, Blurred.this.mViewInto, Blurred.this.mFitIntoViewXY, Blurred.this.mAntiAlias);
                    bitmapBlur.recycle();
                    Blurred.this.mViewInto.setImageBitmap(bitmapClip);
                    return true;
                }
            };
            this.mViewFrom.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        }
    }

    private SnapshotInterceptor checkSnapshotInterceptor() {
        if (this.mSnapshotInterceptor == null) {
            this.mSnapshotInterceptor = new DefaultSnapshotInterceptor();
        }
        return this.mSnapshotInterceptor;
    }
}
