package per.goweii.anylayer;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import per.goweii.anylayer.DecorLayer;

/* JADX INFO: loaded from: classes.dex */
final class Utils {
    static float floatRange(float f, float f2, float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f;
    }

    static int intRange(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    Utils() {
    }

    static <T> T requireNonNull(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    static <T> T requireNonNull(T t) {
        if (t != null) {
            return t;
        }
        throw null;
    }

    static float floatRange01(float f) {
        return floatRange(f, 0.0f, 1.0f);
    }

    static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    static Activity getActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            Context baseContext = ((ContextWrapper) context).getBaseContext();
            if (baseContext instanceof Activity) {
                return (Activity) baseContext;
            }
        }
        return null;
    }

    static Bitmap snapshot(FrameLayout frameLayout, ImageView imageView, float f, DecorLayer.Level level) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) (imageView.getWidth() / f), (int) (imageView.getHeight() / f), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.save();
        int[] iArr = new int[2];
        frameLayout.getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        imageView.getLocationOnScreen(iArr2);
        int i = iArr2[0] - iArr[0];
        int i2 = iArr2[1] - iArr[1];
        float f2 = 1.0f / f;
        canvas.scale(f2, f2);
        canvas.translate(i / f, i2 / f);
        frameLayout.getBackground().draw(canvas);
        loop0: for (int i3 = 0; i3 < frameLayout.getChildCount(); i3++) {
            View childAt = frameLayout.getChildAt(i3);
            if (childAt instanceof DecorLayer.LayerLayout) {
                DecorLayer.LayerLayout layerLayout = (DecorLayer.LayerLayout) childAt;
                for (int i4 = 0; i4 < layerLayout.getChildCount(); i4++) {
                    View childAt2 = layerLayout.getChildAt(i4);
                    if ((childAt2 instanceof DecorLayer.LevelLayout) && ((DecorLayer.LevelLayout) childAt2).getLevel().level() < level.level()) {
                        break loop0;
                    }
                }
            }
            childAt.draw(canvas);
        }
        canvas.restore();
        return bitmapCreateBitmap;
    }

    static void transparent(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= 21) {
            window.clearFlags(67108864);
            window.getDecorView().setSystemUiVisibility(1280);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(0);
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.addFlags(67108864);
        }
    }
}
