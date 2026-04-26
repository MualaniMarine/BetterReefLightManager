package per.goweii.anylayer;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import per.goweii.anylayer.DecorLayer;

/* JADX INFO: loaded from: classes.dex */
public class FloatLayer extends DecorLayer {

    public static class ViewHolder extends DecorLayer.ViewHolder {
    }

    private void bindData() {
    }

    @Override // per.goweii.anylayer.Layer
    protected View onCreateChild(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return null;
    }

    public FloatLayer(Context context) {
        super(Utils.getActivity((Context) Utils.requireNonNull(context, "context == null")));
        interceptKeyEvent(false);
        cancelableOnKeyBack(false);
    }

    @Override // per.goweii.anylayer.DecorLayer
    protected DecorLayer.Level getLevel() {
        return DecorLayer.Level.FLOAT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public ViewHolder onCreateViewHolder() {
        return new ViewHolder();
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public ViewHolder getViewHolder() {
        return (ViewHolder) super.getViewHolder();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public Config onCreateConfig() {
        return new Config();
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public Config getConfig() {
        return (Config) super.getConfig();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public ListenerHolder onCreateListenerHolder() {
        return new ListenerHolder();
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public ListenerHolder getListenerHolder() {
        return (ListenerHolder) super.getListenerHolder();
    }

    @Override // per.goweii.anylayer.Layer
    public void show() {
        super.show();
    }

    @Override // per.goweii.anylayer.Layer
    protected Animator onCreateInAnimator(View view) {
        return AnimatorHelper.createZoomAlphaInAnim(view);
    }

    @Override // per.goweii.anylayer.Layer
    protected Animator onCreateOutAnimator(View view) {
        return AnimatorHelper.createZoomAlphaOutAnim(view);
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnLifeListener
    public void onAttach() {
        super.onAttach();
        bindData();
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public void onShow() {
        super.onShow();
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnLifeListener
    public void onDetach() {
        super.onDetach();
    }

    protected static class Config extends DecorLayer.Config {
        protected Config() {
        }
    }

    protected static class ListenerHolder extends DecorLayer.ListenerHolder {
        protected ListenerHolder() {
        }
    }
}
