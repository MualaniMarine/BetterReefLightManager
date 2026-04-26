package per.goweii.anylayer;

import android.animation.Animator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import per.goweii.anylayer.DecorLayer;

/* JADX INFO: loaded from: classes.dex */
public class ToastLayer extends DecorLayer implements Runnable {
    public ToastLayer(Context context) {
        super(Utils.getActivity((Context) Utils.requireNonNull(context, "context == null")));
        interceptKeyEvent(false);
        cancelableOnKeyBack(false);
    }

    @Override // per.goweii.anylayer.DecorLayer
    protected DecorLayer.Level getLevel() {
        return DecorLayer.Level.TOAST;
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
    public void dismiss(boolean z) {
        super.dismiss(z);
    }

    public ToastLayer removeOthers(boolean z) {
        getConfig().mRemoveOthers = z;
        return this;
    }

    public ToastLayer duration(long j) {
        getConfig().mDuration = j;
        return this;
    }

    public ToastLayer message(CharSequence charSequence) {
        Utils.requireNonNull(charSequence, "message == null");
        getConfig().mMessage = charSequence;
        return this;
    }

    public ToastLayer message(int i) {
        getConfig().mMessage = getActivity().getString(i);
        return this;
    }

    public ToastLayer icon(int i) {
        getConfig().mIcon = i;
        return this;
    }

    public ToastLayer gravity(int i) {
        getConfig().mGravity = i;
        return this;
    }

    public ToastLayer marginLeft(int i) {
        getConfig().mMarginLeft = i;
        return this;
    }

    public ToastLayer marginTop(int i) {
        getConfig().mMarginTop = i;
        return this;
    }

    public ToastLayer marginRight(int i) {
        getConfig().mMarginRight = i;
        return this;
    }

    public ToastLayer marginBottom(int i) {
        getConfig().mMarginBottom = i;
        return this;
    }

    public ToastLayer alpha(float f) {
        getConfig().mAlpha = f;
        return this;
    }

    public ToastLayer backgroundDrawable(Drawable drawable) {
        getConfig().mBackgroundDrawable = drawable;
        return this;
    }

    public ToastLayer backgroundDrawable(int i) {
        getConfig().mBackgroundDrawableRes = i;
        return this;
    }

    public ToastLayer backgroundColorInt(int i) {
        getConfig().mBackgroundColor = i;
        return this;
    }

    public ToastLayer backgroundColorRes(int i) {
        getConfig().mBackgroundColor = getActivity().getResources().getColor(i);
        return this;
    }

    @Override // per.goweii.anylayer.Layer
    protected View onCreateChild(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(C0814R.layout.anylayer_toast_layer, viewGroup, false);
    }

    @Override // per.goweii.anylayer.Layer
    protected Animator onCreateInAnimator(View view) {
        Animator animatorOnCreateInAnimator = super.onCreateInAnimator(view);
        return animatorOnCreateInAnimator == null ? AnimatorHelper.createZoomAlphaInAnim(view) : animatorOnCreateInAnimator;
    }

    @Override // per.goweii.anylayer.Layer
    protected Animator onCreateOutAnimator(View view) {
        Animator animatorOnCreateOutAnimator = super.onCreateOutAnimator(view);
        return animatorOnCreateOutAnimator == null ? AnimatorHelper.createZoomAlphaOutAnim(view) : animatorOnCreateOutAnimator;
    }

    private void bindData() {
        if (getConfig().mIcon > 0) {
            getViewHolder().getIcon().setVisibility(0);
            getViewHolder().getIcon().setImageResource(getConfig().mIcon);
        } else {
            getViewHolder().getIcon().setVisibility(8);
        }
        if (TextUtils.isEmpty(getConfig().mMessage)) {
            getViewHolder().getMessage().setVisibility(8);
            getViewHolder().getMessage().setText("");
        } else {
            getViewHolder().getMessage().setVisibility(0);
            getViewHolder().getMessage().setText(getConfig().mMessage);
        }
        if (getConfig().mBackgroundDrawable != null) {
            getChild().setBackgroundDrawable(getConfig().mBackgroundDrawable);
        } else if (getConfig().mBackgroundDrawableRes == -1) {
            Drawable background = getChild().getBackground();
            if (background instanceof GradientDrawable) {
                background.setColorFilter(getConfig().mBackgroundColor, PorterDuff.Mode.SRC_ATOP);
            }
        } else {
            getChild().setBackgroundResource(getConfig().mBackgroundDrawableRes);
        }
        getChild().setAlpha(getConfig().mAlpha);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getChild().getLayoutParams();
        layoutParams.gravity = getConfig().mGravity;
        if (getConfig().mMarginLeft >= 0) {
            layoutParams.leftMargin = getConfig().mMarginLeft;
        }
        if (getConfig().mMarginTop >= 0) {
            layoutParams.topMargin = getConfig().mMarginTop;
        }
        if (getConfig().mMarginRight >= 0) {
            layoutParams.rightMargin = getConfig().mMarginRight;
        }
        if (getConfig().mMarginBottom >= 0) {
            layoutParams.bottomMargin = getConfig().mMarginBottom;
        }
        getChild().setLayoutParams(layoutParams);
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnLifeListener
    public void onAttach() {
        ToastLayer toastLayer;
        super.onAttach();
        getChild().setTag(this);
        if (getConfig().mRemoveOthers) {
            ViewGroup parent = getParent();
            for (int childCount = parent.getChildCount() - 1; childCount >= 0; childCount--) {
                Object tag = parent.getChildAt(childCount).getTag();
                if ((tag instanceof ToastLayer) && (toastLayer = (ToastLayer) tag) != this) {
                    toastLayer.dismiss(false);
                }
            }
        }
        bindData();
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnPreDrawListener
    public void onPreDraw() {
        super.onPreDraw();
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public void onShow() {
        super.onShow();
        if (getConfig().mDuration > 0) {
            getChild().postDelayed(this, getConfig().mDuration);
        }
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public void onPerRemove() {
        getChild().removeCallbacks(this);
        super.onPerRemove();
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnLifeListener
    public void onDetach() {
        getChild().setTag(null);
        super.onDetach();
    }

    @Override // java.lang.Runnable
    public void run() {
        if (isShow()) {
            dismiss();
        }
    }

    public static class ViewHolder extends DecorLayer.ViewHolder {
        private ImageView mIcon;
        private TextView mMessage;

        @Override // per.goweii.anylayer.Layer.ViewHolder
        public void setChild(View view) {
            super.setChild(view);
            this.mIcon = (ImageView) view.findViewById(C0814R.id.iv_icon);
            this.mMessage = (TextView) view.findViewById(C0814R.id.tv_msg);
        }

        public ImageView getIcon() {
            return this.mIcon;
        }

        public TextView getMessage() {
            return this.mMessage;
        }
    }

    protected static class Config extends DecorLayer.Config {
        private boolean mRemoveOthers = true;
        private long mDuration = 3000;
        private CharSequence mMessage = "";
        private int mIcon = 0;
        private int mBackgroundDrawableRes = -1;
        private Drawable mBackgroundDrawable = null;
        private int mBackgroundColor = ViewCompat.MEASURED_STATE_MASK;
        private float mAlpha = 1.0f;
        private int mGravity = 81;
        private int mMarginLeft = -1;
        private int mMarginTop = -1;
        private int mMarginRight = -1;
        private int mMarginBottom = -1;

        protected Config() {
        }
    }

    protected static class ListenerHolder extends DecorLayer.ListenerHolder {
        protected ListenerHolder() {
        }
    }
}
