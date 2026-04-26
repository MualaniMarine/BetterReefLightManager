package per.goweii.anylayer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import per.goweii.anylayer.LayerActivity;
import per.goweii.burred.Blurred;

/* JADX INFO: loaded from: classes.dex */
public final class AnyLayer {
    public static void initBlurred(Context context) {
        Blurred.init(context);
    }

    public static void recycleBlurred() {
        Blurred.recycle();
    }

    public static void dialog(LayerActivity.OnLayerCreatedCallback onLayerCreatedCallback) {
        LayerActivity.start(ActivityHolder.getApplication(), onLayerCreatedCallback);
    }

    public static DialogLayer dialog() {
        return new DialogLayer(ActivityHolder.getCurrentActivity());
    }

    public static DialogLayer dialog(Class<Activity> cls) {
        return new DialogLayer(ActivityHolder.getActivity(cls));
    }

    public static DialogLayer dialog(Context context) {
        return new DialogLayer(context);
    }

    public static PopupLayer popup(View view) {
        return new PopupLayer(view);
    }

    public static ToastLayer toast() {
        return new ToastLayer(ActivityHolder.getCurrentActivity());
    }

    public static ToastLayer toast(Context context) {
        return new ToastLayer(context);
    }
}
