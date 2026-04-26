package per.goweii.anylayer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import per.goweii.anylayer.DecorLayer;
import per.goweii.anylayer.Layer;
import per.goweii.burred.Blurred;

/* JADX INFO: loaded from: classes.dex */
public class DialogLayer extends DecorLayer {
    private SoftInputHelper mSoftInputHelper;

    public DialogLayer(Activity activity) {
        super(activity);
        this.mSoftInputHelper = null;
        getViewHolder().setActivityContent((FrameLayout) getViewHolder().getDecor().findViewById(android.R.id.content));
    }

    public DialogLayer(Context context) {
        this(Utils.getActivity((Context) Utils.requireNonNull(context, "context == null")));
    }

    @Override // per.goweii.anylayer.DecorLayer
    protected DecorLayer.Level getLevel() {
        return DecorLayer.Level.DIALOG;
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

    @Override // per.goweii.anylayer.Layer
    protected View onCreateChild(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        FrameLayout frameLayout = (FrameLayout) layoutInflater.inflate(C0814R.layout.anylayer_dialog_layer, viewGroup, false);
        getViewHolder().setChild(frameLayout);
        if (getViewHolder().getContent() != null) {
            ViewGroup viewGroup2 = (ViewGroup) getViewHolder().getContent().getParent();
            if (viewGroup2 != null) {
                viewGroup2.removeView(getViewHolder().getContent());
            }
        } else {
            getViewHolder().setContent(layoutInflater.inflate(getConfig().contentViewId, (ViewGroup) getViewHolder().getContentWrapper(), false));
        }
        getViewHolder().getContentWrapper().addView(getViewHolder().getContent());
        return frameLayout;
    }

    @Override // per.goweii.anylayer.Layer
    protected Animator onCreateInAnimator(View view) {
        Animator animatorCreateAlphaInAnim;
        Animator animatorCreateZoomAlphaInAnim;
        if (getConfig().mBackgroundAnimatorCreator != null) {
            animatorCreateAlphaInAnim = getConfig().mBackgroundAnimatorCreator.createInAnimator(getViewHolder().getBackground());
        } else {
            animatorCreateAlphaInAnim = AnimatorHelper.createAlphaInAnim(getViewHolder().getBackground());
        }
        if (getConfig().mContentAnimatorCreator != null) {
            animatorCreateZoomAlphaInAnim = getConfig().mContentAnimatorCreator.createInAnimator(getViewHolder().getContent());
        } else {
            animatorCreateZoomAlphaInAnim = AnimatorHelper.createZoomAlphaInAnim(getViewHolder().getContent());
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorCreateAlphaInAnim, animatorCreateZoomAlphaInAnim);
        return animatorSet;
    }

    @Override // per.goweii.anylayer.Layer
    protected Animator onCreateOutAnimator(View view) {
        Animator animatorCreateAlphaOutAnim;
        Animator animatorCreateZoomAlphaOutAnim;
        if (getConfig().mBackgroundAnimatorCreator != null) {
            animatorCreateAlphaOutAnim = getConfig().mBackgroundAnimatorCreator.createOutAnimator(getViewHolder().getBackground());
        } else {
            animatorCreateAlphaOutAnim = AnimatorHelper.createAlphaOutAnim(getViewHolder().getBackground());
        }
        if (getConfig().mContentAnimatorCreator != null) {
            animatorCreateZoomAlphaOutAnim = getConfig().mContentAnimatorCreator.createOutAnimator(getViewHolder().getContent());
        } else {
            animatorCreateZoomAlphaOutAnim = AnimatorHelper.createZoomAlphaOutAnim(getViewHolder().getContent());
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorCreateAlphaOutAnim, animatorCreateZoomAlphaOutAnim);
        return animatorSet;
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnLifeListener
    public void onAttach() {
        super.onAttach();
        initContent();
        initBackground();
        initContainer();
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnPreDrawListener
    public void onPreDraw() {
        super.onPreDraw();
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public void onShow() {
        super.onShow();
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public void onPerRemove() {
        super.onPerRemove();
    }

    @Override // per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnLifeListener
    public void onDetach() {
        super.onDetach();
        getViewHolder().recycle();
    }

    protected void initContainer() {
        getViewHolder().getChild().setClickable(true);
        if (getConfig().mCancelableOnTouchOutside) {
            getViewHolder().getChild().setOnClickListener(new View.OnClickListener() { // from class: per.goweii.anylayer.DialogLayer.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    DialogLayer.this.dismiss();
                }
            });
        }
        if (getViewHolder().getActivityContent() != null) {
            int[] iArr = new int[2];
            getViewHolder().getDecor().getLocationOnScreen(iArr);
            int[] iArr2 = new int[2];
            getViewHolder().getActivityContent().getLocationOnScreen(iArr2);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getViewHolder().getChild().getLayoutParams();
            layoutParams.leftMargin = iArr2[0] - iArr[0];
            layoutParams.topMargin = 0;
            layoutParams.width = getViewHolder().getActivityContent().getWidth();
            layoutParams.height = getViewHolder().getActivityContent().getHeight() + (iArr2[1] - iArr[1]);
            getViewHolder().getChild().setLayoutParams(layoutParams);
        }
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) getViewHolder().getContentWrapper().getLayoutParams();
        layoutParams2.width = -1;
        layoutParams2.height = -1;
        getViewHolder().getContentWrapper().setLayoutParams(layoutParams2);
    }

    protected void initBackground() {
        if (getConfig().mBackgroundBlurPercent > 0.0f || getConfig().mBackgroundBlurRadius > 0.0f) {
            getViewHolder().getBackground().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: per.goweii.anylayer.DialogLayer.2
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    DialogLayer.this.getViewHolder().getBackground().getViewTreeObserver().removeOnPreDrawListener(this);
                    float fMin = DialogLayer.this.getConfig().mBackgroundBlurRadius;
                    if (DialogLayer.this.getConfig().mBackgroundBlurPercent > 0.0f) {
                        fMin = Math.min(DialogLayer.this.getViewHolder().getBackground().getWidth(), DialogLayer.this.getViewHolder().getBackground().getHeight()) * DialogLayer.this.getConfig().mBackgroundBlurPercent;
                    }
                    float f = DialogLayer.this.getConfig().mBackgroundBlurScale;
                    if (fMin > 25.0f) {
                        f *= fMin / 25.0f;
                        fMin = 25.0f;
                    }
                    Bitmap bitmapSnapshot = Utils.snapshot(DialogLayer.this.getViewHolder().getDecor(), DialogLayer.this.getViewHolder().getBackground(), f, DialogLayer.this.getLevel());
                    Blurred.init(DialogLayer.this.getActivity());
                    Bitmap bitmapBlur = Blurred.with(bitmapSnapshot).recycleOriginal(true).keepSize(false).radius(fMin).blur();
                    DialogLayer.this.getViewHolder().getBackground().setScaleType(ImageView.ScaleType.CENTER_CROP);
                    DialogLayer.this.getViewHolder().getBackground().setImageBitmap(bitmapBlur);
                    DialogLayer.this.getViewHolder().getBackground().setColorFilter(DialogLayer.this.getConfig().mBackgroundColor);
                    return true;
                }
            });
            return;
        }
        if (getConfig().mBackgroundBitmap != null) {
            getViewHolder().getBackground().setImageBitmap(getConfig().mBackgroundBitmap);
            if (getConfig().mBackgroundColor != -1) {
                getViewHolder().getBackground().setColorFilter(getConfig().mBackgroundColor);
                return;
            }
            return;
        }
        if (getConfig().mBackgroundDrawable != null) {
            getViewHolder().getBackground().setImageDrawable(getConfig().mBackgroundDrawable);
            if (getConfig().mBackgroundColor != -1) {
                getViewHolder().getBackground().setColorFilter(getConfig().mBackgroundColor);
                return;
            }
            return;
        }
        if (getConfig().mBackgroundResource != -1) {
            getViewHolder().getBackground().setImageResource(getConfig().mBackgroundResource);
            if (getConfig().mBackgroundColor != -1) {
                getViewHolder().getBackground().setColorFilter(getConfig().mBackgroundColor);
                return;
            }
            return;
        }
        if (getConfig().mBackgroundColor != -1) {
            getViewHolder().getBackground().setImageDrawable(new ColorDrawable(getConfig().mBackgroundColor));
        } else if (getConfig().mBackgroundDimAmount != -1.0f) {
            getViewHolder().getBackground().setImageDrawable(new ColorDrawable(Color.argb((int) (Utils.floatRange01(getConfig().mBackgroundDimAmount) * 255.0f), 0, 0, 0)));
        } else {
            getViewHolder().getBackground().setImageDrawable(new ColorDrawable(0));
        }
    }

    protected void initContent() {
        FrameLayout.LayoutParams layoutParams;
        View viewFindViewById;
        getViewHolder().getContent().setClickable(true);
        ViewGroup.LayoutParams layoutParams2 = getViewHolder().getContent().getLayoutParams();
        if (layoutParams2 == null) {
            layoutParams = new FrameLayout.LayoutParams(-2, -2);
        } else if (layoutParams2 instanceof FrameLayout.LayoutParams) {
            layoutParams = (FrameLayout.LayoutParams) layoutParams2;
        } else {
            layoutParams = new FrameLayout.LayoutParams(layoutParams2.width, layoutParams2.height);
        }
        if (getConfig().mGravity != -1) {
            layoutParams.gravity = getConfig().mGravity;
        }
        getViewHolder().getContent().setLayoutParams(layoutParams);
        if (getConfig().mAsStatusBarViewId <= 0 || (viewFindViewById = getViewHolder().getContent().findViewById(getConfig().mAsStatusBarViewId)) == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams3 = viewFindViewById.getLayoutParams();
        layoutParams3.height = Utils.getStatusBarHeight(getActivity());
        viewFindViewById.setLayoutParams(layoutParams3);
        viewFindViewById.setVisibility(0);
    }

    public DialogLayer contentView(View view) {
        Utils.requireNonNull(view, "contentView == null");
        getViewHolder().setContent(view);
        return this;
    }

    public DialogLayer contentView(int i) {
        getConfig().contentViewId = i;
        return this;
    }

    public DialogLayer asStatusBar(int i) {
        getConfig().mAsStatusBarViewId = i;
        return this;
    }

    public DialogLayer gravity(int i) {
        getConfig().mGravity = i;
        return this;
    }

    public DialogLayer contentAnimator(Layer.AnimatorCreator animatorCreator) {
        getConfig().mContentAnimatorCreator = animatorCreator;
        return this;
    }

    public DialogLayer backgroundAnimator(Layer.AnimatorCreator animatorCreator) {
        getConfig().mBackgroundAnimatorCreator = animatorCreator;
        return this;
    }

    public DialogLayer backgroundBlurRadius(float f) {
        getConfig().mBackgroundBlurRadius = f;
        return this;
    }

    public DialogLayer backgroundBlurPercent(float f) {
        getConfig().mBackgroundBlurPercent = f;
        return this;
    }

    public DialogLayer backgroundBlurScale(float f) {
        getConfig().mBackgroundBlurScale = f;
        return this;
    }

    public DialogLayer backgroundBitmap(Bitmap bitmap) {
        Utils.requireNonNull(bitmap, "bitmap == null");
        getConfig().mBackgroundBitmap = bitmap;
        return this;
    }

    public DialogLayer backgroundDimAmount(float f) {
        getConfig().mBackgroundDimAmount = Utils.floatRange01(f);
        return this;
    }

    public DialogLayer backgroundResource(int i) {
        getConfig().mBackgroundResource = i;
        return this;
    }

    public DialogLayer backgroundDrawable(Drawable drawable) {
        Utils.requireNonNull(drawable, "drawable == null");
        getConfig().mBackgroundDrawable = drawable;
        return this;
    }

    public DialogLayer backgroundColorInt(int i) {
        getConfig().mBackgroundColor = i;
        return this;
    }

    public DialogLayer backgroundColorRes(int i) {
        getConfig().mBackgroundColor = getActivity().getResources().getColor(i);
        return this;
    }

    public DialogLayer cancelableOnTouchOutside(boolean z) {
        getConfig().mCancelableOnTouchOutside = z;
        return this;
    }

    @Override // per.goweii.anylayer.DecorLayer
    public DialogLayer cancelableOnClickKeyBack(boolean z) {
        return (DialogLayer) super.cancelableOnClickKeyBack(z);
    }

    public View getContentView() {
        return getViewHolder().getContent();
    }

    public ImageView getBackground() {
        return getViewHolder().getBackground();
    }

    public void compatSoftInput(EditText... editTextArr) {
        Activity activity = Utils.getActivity(getActivity());
        if (activity != null) {
            this.mSoftInputHelper = SoftInputHelper.attach(activity).init(getViewHolder().getContentWrapper(), getViewHolder().getContent(), editTextArr).moveWithTranslation();
        }
    }

    public void removeSoftInput() {
        SoftInputHelper softInputHelper = this.mSoftInputHelper;
        if (softInputHelper != null) {
            softInputHelper.detach();
        }
    }

    public static class ViewHolder extends DecorLayer.ViewHolder {
        private FrameLayout mActivityContent;
        private ImageView mBackground;
        private View mContent;
        private FrameLayout mContentWrapper;

        public void recycle() {
            if (this.mBackground.getDrawable() instanceof BitmapDrawable) {
                ((BitmapDrawable) this.mBackground.getDrawable()).getBitmap().recycle();
            }
        }

        public void setActivityContent(FrameLayout frameLayout) {
            this.mActivityContent = frameLayout;
        }

        public FrameLayout getActivityContent() {
            return this.mActivityContent;
        }

        @Override // per.goweii.anylayer.Layer.ViewHolder
        public void setChild(View view) {
            super.setChild(view);
            this.mContentWrapper = (FrameLayout) getChild().findViewById(C0814R.id.fl_content_wrapper);
            this.mBackground = (ImageView) getChild().findViewById(C0814R.id.iv_background);
        }

        @Override // per.goweii.anylayer.Layer.ViewHolder
        public FrameLayout getChild() {
            return (FrameLayout) super.getChild();
        }

        void setContent(View view) {
            this.mContent = view;
        }

        public View getContent() {
            return this.mContent;
        }

        public FrameLayout getContentWrapper() {
            return this.mContentWrapper;
        }

        public ImageView getBackground() {
            return this.mBackground;
        }
    }

    protected static class Config extends DecorLayer.Config {
        protected Layer.AnimatorCreator mBackgroundAnimatorCreator = null;
        protected Layer.AnimatorCreator mContentAnimatorCreator = null;
        protected int contentViewId = 0;
        protected boolean mCancelableOnTouchOutside = true;
        protected int mAsStatusBarViewId = 0;
        protected int mGravity = 17;
        protected float mBackgroundBlurPercent = 0.0f;
        protected float mBackgroundBlurRadius = 0.0f;
        protected float mBackgroundBlurScale = 2.0f;
        protected Bitmap mBackgroundBitmap = null;
        protected int mBackgroundResource = -1;
        protected Drawable mBackgroundDrawable = null;
        protected float mBackgroundDimAmount = -1.0f;
        protected int mBackgroundColor = -1;

        protected Config() {
        }
    }

    protected static class ListenerHolder extends DecorLayer.ListenerHolder {
        protected ListenerHolder() {
        }
    }
}
