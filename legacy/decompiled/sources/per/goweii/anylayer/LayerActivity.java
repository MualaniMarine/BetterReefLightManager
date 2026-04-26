package per.goweii.anylayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import per.goweii.anylayer.Layer;

/* JADX INFO: loaded from: classes.dex */
public class LayerActivity extends Activity implements Layer.OnVisibleChangeListener {
    private static OnLayerCreatedCallback sOnLayerCreatedCallback;

    public interface OnLayerCreatedCallback {
        void onLayerCreated(DialogLayer dialogLayer);
    }

    @Override // per.goweii.anylayer.Layer.OnVisibleChangeListener
    public void onShow(Layer layer) {
    }

    static void start(Context context, OnLayerCreatedCallback onLayerCreatedCallback) {
        sOnLayerCreatedCallback = onLayerCreatedCallback;
        Intent intent = new Intent(context, (Class<?>) LayerActivity.class);
        intent.addFlags(268435456);
        context.startActivity(intent);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        overridePendingTransition(0, 0);
        super.onCreate(bundle);
        Utils.transparent(this);
        DialogLayer dialogLayerDialog = AnyLayer.dialog(this);
        dialogLayerDialog.onVisibleChangeListener(this);
        OnLayerCreatedCallback onLayerCreatedCallback = sOnLayerCreatedCallback;
        if (onLayerCreatedCallback != null) {
            onLayerCreatedCallback.onLayerCreated(dialogLayerDialog);
        }
    }

    @Override // per.goweii.anylayer.Layer.OnVisibleChangeListener
    public void onDismiss(Layer layer) {
        finish();
        overridePendingTransition(0, 0);
    }
}
