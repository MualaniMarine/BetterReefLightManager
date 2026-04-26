package per.goweii.anylayer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import per.goweii.anylayer.Layer;

/* JADX INFO: loaded from: classes.dex */
public class DecorLayer extends Layer {
    private final Activity mActivity;

    public DecorLayer(Activity activity) {
        Utils.requireNonNull(activity, "activity == null");
        this.mActivity = activity;
        getViewHolder().setDecor((FrameLayout) activity.getWindow().getDecorView());
    }

    protected Level getLevel() {
        return Level.DIALOG;
    }

    public Activity getActivity() {
        Utils.requireNonNull(this.mActivity, "activity == null");
        return this.mActivity;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // per.goweii.anylayer.Layer
    public ViewHolder onCreateViewHolder() {
        return new ViewHolder();
    }

    @Override // per.goweii.anylayer.Layer
    public ViewHolder getViewHolder() {
        return (ViewHolder) super.getViewHolder();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // per.goweii.anylayer.Layer
    public Config onCreateConfig() {
        return new Config();
    }

    @Override // per.goweii.anylayer.Layer
    public Config getConfig() {
        return (Config) super.getConfig();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // per.goweii.anylayer.Layer
    public ListenerHolder onCreateListenerHolder() {
        return new ListenerHolder();
    }

    @Override // per.goweii.anylayer.Layer
    public ListenerHolder getListenerHolder() {
        return (ListenerHolder) super.getListenerHolder();
    }

    @Override // per.goweii.anylayer.Layer
    protected ViewGroup onGetParent() {
        LayerLayout layerLayoutFindLayerLayoutFromDecor = findLayerLayoutFromDecor();
        if (layerLayoutFindLayerLayoutFromDecor == null) {
            layerLayoutFindLayerLayoutFromDecor = addNewLayerLayoutToDecor();
        }
        LevelLayout levelLayout = null;
        int childCount = layerLayoutFindLayerLayoutFromDecor.getChildCount();
        int i = 0;
        int i2 = -1;
        while (true) {
            if (i >= childCount) {
                i = i2;
                break;
            }
            View childAt = layerLayoutFindLayerLayoutFromDecor.getChildAt(i);
            if (childAt instanceof LevelLayout) {
                LevelLayout levelLayout2 = (LevelLayout) childAt;
                if (getLevel() == levelLayout2.getLevel()) {
                    levelLayout = levelLayout2;
                    break;
                }
                if (getLevel().level() > levelLayout2.getLevel().level()) {
                    i--;
                    break;
                }
            }
            i2 = i;
            i++;
        }
        if (levelLayout == null) {
            levelLayout = new LevelLayout(layerLayoutFindLayerLayoutFromDecor.getContext(), getLevel());
            levelLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            layerLayoutFindLayerLayoutFromDecor.addView(levelLayout, i + 1);
        }
        getViewHolder().setParent(levelLayout);
        return levelLayout;
    }

    @Override // per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnLifeListener
    public void onAttach() {
        super.onAttach();
    }

    @Override // per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnPreDrawListener
    public void onPreDraw() {
        super.onPreDraw();
    }

    @Override // per.goweii.anylayer.Layer
    public void onShow() {
        super.onShow();
    }

    @Override // per.goweii.anylayer.Layer
    public void onPerRemove() {
        super.onPerRemove();
    }

    @Override // per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnLifeListener
    public void onDetach() {
        super.onDetach();
        LayerLayout layerLayoutFindLayerLayoutFromDecor = findLayerLayoutFromDecor();
        if (layerLayoutFindLayerLayoutFromDecor == null) {
            return;
        }
        LevelLayout levelLayout = null;
        int childCount = layerLayoutFindLayerLayoutFromDecor.getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                break;
            }
            View childAt = layerLayoutFindLayerLayoutFromDecor.getChildAt(i);
            if (childAt instanceof LevelLayout) {
                LevelLayout levelLayout2 = (LevelLayout) childAt;
                if (getLevel() == levelLayout2.getLevel()) {
                    levelLayout = levelLayout2;
                    break;
                }
            }
            i++;
        }
        if (levelLayout == null) {
            return;
        }
        if (levelLayout.getChildCount() == 0) {
            layerLayoutFindLayerLayoutFromDecor.removeView(levelLayout);
        }
        if (layerLayoutFindLayerLayoutFromDecor.getChildCount() == 0) {
            removeLayerLayoutFromDecor(layerLayoutFindLayerLayoutFromDecor);
        }
    }

    private LayerLayout findLayerLayoutFromDecor() {
        FrameLayout frameLayout = getViewHolder().mDecor;
        int childCount = frameLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = frameLayout.getChildAt(i);
            if (childAt instanceof LayerLayout) {
                return (LayerLayout) childAt;
            }
        }
        return null;
    }

    private LayerLayout addNewLayerLayoutToDecor() {
        FrameLayout frameLayout = getViewHolder().mDecor;
        LayerLayout layerLayout = new LayerLayout(frameLayout.getContext());
        layerLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        frameLayout.addView(layerLayout, frameLayout.getChildCount());
        return layerLayout;
    }

    private void removeLayerLayoutFromDecor(LayerLayout layerLayout) {
        getViewHolder().mDecor.removeView(layerLayout);
    }

    public DecorLayer cancelableOnClickKeyBack(boolean z) {
        cancelableOnKeyBack(z);
        return this;
    }

    public static class ViewHolder extends Layer.ViewHolder {
        private FrameLayout mDecor;

        public void setDecor(FrameLayout frameLayout) {
            this.mDecor = frameLayout;
        }

        public FrameLayout getDecor() {
            return this.mDecor;
        }
    }

    protected static class Config extends Layer.Config {
        protected Config() {
        }
    }

    protected static class ListenerHolder extends Layer.ListenerHolder {
        protected ListenerHolder() {
        }
    }

    protected enum Level {
        FLOAT(1),
        TOAST(2),
        DIALOG(3),
        POPUP(4),
        GUIDE(5);

        private final int level;

        Level(int i) {
            this.level = i;
        }

        protected int level() {
            return this.level;
        }
    }

    public static class LayerLayout extends FrameLayout {
        public LayerLayout(Context context) {
            super(context);
        }
    }

    public static class LevelLayout extends FrameLayout {
        private final Level mLevel;

        public LevelLayout(Context context, Level level) {
            super(context);
            this.mLevel = level;
        }

        public Level getLevel() {
            return this.mLevel;
        }
    }
}
