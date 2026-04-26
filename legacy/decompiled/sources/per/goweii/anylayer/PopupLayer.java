package per.goweii.anylayer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import per.goweii.anylayer.Align;
import per.goweii.anylayer.DecorLayer;
import per.goweii.anylayer.DialogLayer;

/* JADX INFO: loaded from: classes.dex */
public class PopupLayer extends DialogLayer {
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;

    public PopupLayer(View view) {
        super(((View) Utils.requireNonNull(view, "targetView == null")).getContext());
        getViewHolder().setTarget(view);
    }

    @Override // per.goweii.anylayer.DialogLayer, per.goweii.anylayer.DecorLayer
    protected DecorLayer.Level getLevel() {
        return DecorLayer.Level.POPUP;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // per.goweii.anylayer.DialogLayer, per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public ViewHolder onCreateViewHolder() {
        return new ViewHolder();
    }

    @Override // per.goweii.anylayer.DialogLayer, per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public ViewHolder getViewHolder() {
        return (ViewHolder) super.getViewHolder();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // per.goweii.anylayer.DialogLayer, per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public Config onCreateConfig() {
        return new Config();
    }

    @Override // per.goweii.anylayer.DialogLayer, per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public Config getConfig() {
        return (Config) super.getConfig();
    }

    @Override // per.goweii.anylayer.DialogLayer, per.goweii.anylayer.Layer
    protected View onCreateChild(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return super.onCreateChild(layoutInflater, viewGroup);
    }

    @Override // per.goweii.anylayer.DialogLayer, per.goweii.anylayer.Layer
    protected Animator onCreateInAnimator(View view) {
        Animator animatorCreateAlphaInAnim;
        Animator animatorCreateTopInAnim;
        if (getConfig().mBackgroundAnimatorCreator != null) {
            animatorCreateAlphaInAnim = getConfig().mBackgroundAnimatorCreator.createInAnimator(getViewHolder().getBackground());
        } else {
            animatorCreateAlphaInAnim = AnimatorHelper.createAlphaInAnim(getViewHolder().getBackground());
        }
        if (getConfig().mContentAnimatorCreator != null) {
            animatorCreateTopInAnim = getConfig().mContentAnimatorCreator.createInAnimator(getViewHolder().getContent());
        } else {
            animatorCreateTopInAnim = AnimatorHelper.createTopInAnim(getViewHolder().getContent());
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorCreateAlphaInAnim, animatorCreateTopInAnim);
        return animatorSet;
    }

    @Override // per.goweii.anylayer.DialogLayer, per.goweii.anylayer.Layer
    protected Animator onCreateOutAnimator(View view) {
        Animator animatorCreateAlphaOutAnim;
        Animator animatorCreateTopOutAnim;
        if (getConfig().mBackgroundAnimatorCreator != null) {
            animatorCreateAlphaOutAnim = getConfig().mBackgroundAnimatorCreator.createOutAnimator(getViewHolder().getBackground());
        } else {
            animatorCreateAlphaOutAnim = AnimatorHelper.createAlphaOutAnim(getViewHolder().getBackground());
        }
        if (getConfig().mContentAnimatorCreator != null) {
            animatorCreateTopOutAnim = getConfig().mContentAnimatorCreator.createOutAnimator(getViewHolder().getContent());
        } else {
            animatorCreateTopOutAnim = AnimatorHelper.createTopOutAnim(getViewHolder().getContent());
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorCreateAlphaOutAnim, animatorCreateTopOutAnim);
        return animatorSet;
    }

    @Override // per.goweii.anylayer.DialogLayer, per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnLifeListener
    public void onAttach() {
        super.onAttach();
    }

    @Override // per.goweii.anylayer.DialogLayer, per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnPreDrawListener
    public void onPreDraw() {
        super.onPreDraw();
    }

    @Override // per.goweii.anylayer.DialogLayer, per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public void onShow() {
        super.onShow();
    }

    @Override // per.goweii.anylayer.DialogLayer, per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer
    public void onPerRemove() {
        super.onPerRemove();
    }

    @Override // per.goweii.anylayer.DialogLayer, per.goweii.anylayer.DecorLayer, per.goweii.anylayer.Layer, per.goweii.anylayer.ViewManager.OnLifeListener
    public void onDetach() {
        if (this.mOnScrollChangedListener != null) {
            getViewHolder().getParent().getViewTreeObserver().removeOnScrollChangedListener(this.mOnScrollChangedListener);
            this.mOnScrollChangedListener = null;
        }
        super.onDetach();
    }

    @Override // per.goweii.anylayer.DialogLayer
    protected void initContainer() {
        super.initContainer();
        if (!getConfig().mOutsideInterceptTouchEvent) {
            getViewHolder().getChild().setOnClickListener(null);
            getViewHolder().getChild().setClickable(false);
        }
        getViewHolder().getContentWrapper().setClipChildren(getConfig().mContentClip);
        getViewHolder().getChild().setClipChildren(getConfig().mContentClip);
        getViewHolder().getChild().setClipToPadding(false);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getViewHolder().getContent().getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) getViewHolder().getContentWrapper().getLayoutParams();
        if (layoutParams.width == -1) {
            layoutParams2.width = -1;
        } else {
            layoutParams2.width = -2;
        }
        if (layoutParams.height == -1) {
            layoutParams2.height = -1;
        } else {
            layoutParams2.height = -2;
        }
        getViewHolder().getContentWrapper().setLayoutParams(layoutParams2);
        initLocation();
    }

    private void initLocation() {
        getViewHolder().getChild().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: per.goweii.anylayer.PopupLayer.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                if (PopupLayer.this.getViewHolder().getChild().getViewTreeObserver().isAlive()) {
                    PopupLayer.this.getViewHolder().getChild().getViewTreeObserver().removeOnPreDrawListener(this);
                }
                PopupLayer.this.initLocationByTranslation();
                return false;
            }
        });
        if (getConfig().mOutsideInterceptTouchEvent) {
            return;
        }
        this.mOnScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: per.goweii.anylayer.PopupLayer.2
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public void onScrollChanged() {
                PopupLayer.this.initLocationByTranslation();
            }
        };
        getViewHolder().getParent().getViewTreeObserver().addOnScrollChangedListener(this.mOnScrollChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initLocationByTranslation() {
        int[] iArr = new int[2];
        getViewHolder().getTarget().getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        getViewHolder().getDecor().getLocationOnScreen(iArr2);
        int i = iArr[0] - iArr2[0];
        int i2 = iArr[1] - iArr2[1];
        int width = getViewHolder().getTarget().getWidth();
        int height = getViewHolder().getTarget().getHeight();
        initContentWrapperTranslation(i, i2, width, height);
        if (getConfig().mBackgroundAlign) {
            initBackgroundTranslation(i, i2, width, height);
        }
    }

    private void initBackgroundTranslation(int i, int i2, int i3, int i4) {
        int width = getViewHolder().getContentWrapper().getWidth();
        int height = getViewHolder().getContentWrapper().getHeight();
        int width2 = getViewHolder().getChild().getWidth() - width;
        int height2 = getViewHolder().getChild().getHeight() - height;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getViewHolder().getChild().getLayoutParams();
        if (getConfig().mAlignDirection == Align.Direction.HORIZONTAL) {
            int i5 = C08133.$SwitchMap$per$goweii$anylayer$Align$Horizontal[getConfig().mAlignHorizontal.ordinal()];
            if (i5 == 1) {
                i = -(layoutParams.width - i);
                if (getConfig().mInside) {
                    i = Utils.intRange(i, -width2, 0);
                }
            } else if (i5 == 2) {
                i = -((layoutParams.width - i) - i3);
                if (getConfig().mInside) {
                    i = Utils.intRange(i, -width2, 0);
                }
            } else if (i5 != 3) {
                if (i5 == 4) {
                    if (getConfig().mInside) {
                        i = Utils.intRange(i, 0, width2);
                    }
                }
                i = 0;
                i2 = 0;
            } else {
                i += i3;
                if (getConfig().mInside) {
                    i = Utils.intRange(i, 0, width2);
                }
            }
            i2 = 0;
        } else if (getConfig().mAlignDirection != Align.Direction.VERTICAL) {
            i = 0;
            i2 = 0;
        } else {
            int i6 = C08133.$SwitchMap$per$goweii$anylayer$Align$Vertical[getConfig().mAlignVertical.ordinal()];
            if (i6 == 1) {
                i2 = -(layoutParams.height - i2);
                if (getConfig().mInside) {
                    i2 = Utils.intRange(i2, -height2, 0);
                }
            } else if (i6 == 2) {
                i2 = -((layoutParams.height - i2) - i4);
                if (getConfig().mInside) {
                    i2 = Utils.intRange(i2, -height2, 0);
                }
            } else if (i6 != 3) {
                if (i6 == 4) {
                    if (getConfig().mInside) {
                        i2 = Utils.intRange(i2, 0, height2);
                    }
                }
                i = 0;
                i2 = 0;
            } else {
                i2 += i4;
                if (getConfig().mInside) {
                    i2 = Utils.intRange(i2, 0, height2);
                }
            }
            i = 0;
        }
        getViewHolder().getBackground().setTranslationX(i);
        getViewHolder().getBackground().setTranslationY(i2);
    }

    /* JADX INFO: renamed from: per.goweii.anylayer.PopupLayer$3 */
    static /* synthetic */ class C08133 {
        static final /* synthetic */ int[] $SwitchMap$per$goweii$anylayer$Align$Horizontal;
        static final /* synthetic */ int[] $SwitchMap$per$goweii$anylayer$Align$Vertical;

        static {
            int[] iArr = new int[Align.Vertical.values().length];
            $SwitchMap$per$goweii$anylayer$Align$Vertical = iArr;
            try {
                iArr[Align.Vertical.ABOVE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$per$goweii$anylayer$Align$Vertical[Align.Vertical.ALIGN_BOTTOM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$per$goweii$anylayer$Align$Vertical[Align.Vertical.BELOW.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$per$goweii$anylayer$Align$Vertical[Align.Vertical.ALIGN_TOP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$per$goweii$anylayer$Align$Vertical[Align.Vertical.CENTER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr2 = new int[Align.Horizontal.values().length];
            $SwitchMap$per$goweii$anylayer$Align$Horizontal = iArr2;
            try {
                iArr2[Align.Horizontal.TO_LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$per$goweii$anylayer$Align$Horizontal[Align.Horizontal.ALIGN_RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$per$goweii$anylayer$Align$Horizontal[Align.Horizontal.TO_RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$per$goweii$anylayer$Align$Horizontal[Align.Horizontal.ALIGN_LEFT.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$per$goweii$anylayer$Align$Horizontal[Align.Horizontal.CENTER.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    private void initContentWrapperTranslation(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int width = getViewHolder().getContentWrapper().getWidth();
        int height = getViewHolder().getContentWrapper().getHeight();
        int i7 = C08133.$SwitchMap$per$goweii$anylayer$Align$Horizontal[getConfig().mAlignHorizontal.ordinal()];
        if (i7 != 1) {
            if (i7 == 2) {
                i6 = width - i3;
            } else if (i7 == 3) {
                i += i3;
            } else if (i7 != 4) {
                if (i7 != 5) {
                    i = 0;
                } else {
                    i6 = (width - i3) / 2;
                }
            }
            i -= i6;
        } else {
            i -= width;
        }
        int i8 = C08133.$SwitchMap$per$goweii$anylayer$Align$Vertical[getConfig().mAlignVertical.ordinal()];
        if (i8 != 1) {
            if (i8 == 2) {
                i5 = height - i4;
            } else if (i8 == 3) {
                i2 += i4;
            } else if (i8 != 4) {
                if (i8 != 5) {
                    i2 = 0;
                } else {
                    i5 = (height - i4) / 2;
                }
            }
            i2 -= i5;
        } else {
            i2 -= height;
        }
        if (getConfig().mInside) {
            int width2 = getViewHolder().getChild().getWidth();
            int height2 = getViewHolder().getChild().getHeight() - height;
            i = Utils.intRange(i, 0, width2 - width);
            i2 = Utils.intRange(i2, 0, height2);
        }
        getViewHolder().getContentWrapper().setTranslationX(i);
        getViewHolder().getContentWrapper().setTranslationY(i2);
    }

    @Override // per.goweii.anylayer.DialogLayer
    protected void initBackground() {
        super.initBackground();
    }

    @Override // per.goweii.anylayer.DialogLayer
    protected void initContent() {
        super.initContent();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getViewHolder().getContent().getLayoutParams();
        layoutParams.gravity = -1;
        getViewHolder().getContent().setLayoutParams(layoutParams);
    }

    public PopupLayer outsideInterceptTouchEvent(boolean z) {
        getConfig().mOutsideInterceptTouchEvent = z;
        return this;
    }

    public PopupLayer align(Align.Direction direction, Align.Horizontal horizontal, Align.Vertical vertical, boolean z) {
        getConfig().mAlignDirection = (Align.Direction) Utils.requireNonNull(direction, "direction == null");
        getConfig().mAlignHorizontal = (Align.Horizontal) Utils.requireNonNull(horizontal, "horizontal == null");
        getConfig().mAlignVertical = (Align.Vertical) Utils.requireNonNull(vertical, "vertical == null");
        getConfig().mInside = z;
        return this;
    }

    public PopupLayer contentClip(boolean z) {
        getConfig().mContentClip = z;
        return this;
    }

    public PopupLayer backgroundAlign(boolean z) {
        getConfig().mBackgroundAlign = z;
        return this;
    }

    public PopupLayer inside(boolean z) {
        getConfig().mInside = z;
        return this;
    }

    public PopupLayer vertical(Align.Vertical vertical) {
        getConfig().mAlignVertical = (Align.Vertical) Utils.requireNonNull(vertical, "vertical == null");
        return this;
    }

    public PopupLayer horizontal(Align.Horizontal horizontal) {
        getConfig().mAlignHorizontal = (Align.Horizontal) Utils.requireNonNull(horizontal, "horizontal == null");
        return this;
    }

    public PopupLayer direction(Align.Direction direction) {
        getConfig().mAlignDirection = (Align.Direction) Utils.requireNonNull(direction, "direction == null");
        return this;
    }

    public static class ViewHolder extends DialogLayer.ViewHolder {
        private View mTarget;

        public void setTarget(View view) {
            this.mTarget = view;
        }

        public View getTarget() {
            return this.mTarget;
        }
    }

    protected static class Config extends DialogLayer.Config {
        protected boolean mOutsideInterceptTouchEvent = true;
        protected boolean mContentClip = true;
        protected boolean mBackgroundAlign = true;
        protected boolean mInside = true;
        protected Align.Direction mAlignDirection = Align.Direction.VERTICAL;
        protected Align.Horizontal mAlignHorizontal = Align.Horizontal.CENTER;
        protected Align.Vertical mAlignVertical = Align.Vertical.BELOW;

        protected Config() {
        }
    }

    protected static class ListenerHolder extends DialogLayer.ListenerHolder {
        protected ListenerHolder() {
        }
    }
}
