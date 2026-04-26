package per.goweii.anylayer;

import android.animation.Animator;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import per.goweii.anylayer.ViewManager;

/* JADX INFO: loaded from: classes.dex */
public class Layer implements ViewManager.OnLifeListener, ViewManager.OnKeyListener, ViewManager.OnPreDrawListener {
    private final ViewManager mViewManager;
    private SparseArray<View> mViewCaches = null;
    private boolean mShowWithAnim = false;
    private boolean mDismissWithAnim = false;
    private Animator mAnimatorIn = null;
    private Animator mAnimatorOut = null;
    private final Config mConfig = (Config) Utils.requireNonNull(onCreateConfig(), "onCreateConfig() == null");
    private final ViewHolder mViewHolder = (ViewHolder) Utils.requireNonNull(onCreateViewHolder(), "onCreateViewHolder() == null");
    private final ListenerHolder mListenerHolder = (ListenerHolder) Utils.requireNonNull(onCreateListenerHolder(), "onCreateListenerHolder() == null");

    public interface AnimatorCreator {
        Animator createInAnimator(View view);

        Animator createOutAnimator(View view);
    }

    public interface DataBinder {
        void bindData(Layer layer);
    }

    public interface OnClickListener {
        void onClick(Layer layer, View view);
    }

    public interface OnDismissListener {
        void onDismissed(Layer layer);

        void onDismissing(Layer layer);
    }

    public interface OnShowListener {
        void onShowing(Layer layer);

        void onShown(Layer layer);
    }

    public interface OnVisibleChangeListener {
        void onDismiss(Layer layer);

        void onShow(Layer layer);
    }

    public Layer() {
        ViewManager viewManager = new ViewManager();
        this.mViewManager = viewManager;
        viewManager.setOnLifeListener(this);
        this.mViewManager.setOnPreDrawListener(this);
    }

    public ViewHolder getViewHolder() {
        Utils.requireNonNull(this.mViewHolder, "mViewHolder == null");
        return this.mViewHolder;
    }

    public Config getConfig() {
        Utils.requireNonNull(this.mConfig, "mConfig == null");
        return this.mConfig;
    }

    public ListenerHolder getListenerHolder() {
        Utils.requireNonNull(this.mListenerHolder, "mListenerHolder == null");
        return this.mListenerHolder;
    }

    protected Config onCreateConfig() {
        return new Config();
    }

    protected ViewHolder onCreateViewHolder() {
        return new ViewHolder();
    }

    protected ListenerHolder onCreateListenerHolder() {
        return new ListenerHolder();
    }

    protected ViewGroup onGetParent() {
        return this.mViewHolder.getParent();
    }

    protected View onCreateChild(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        if (this.mViewHolder.getChild() == null) {
            this.mViewHolder.setChild(layoutInflater.inflate(this.mConfig.mChildId, viewGroup, false));
        }
        return this.mViewHolder.getChild();
    }

    protected Animator onCreateInAnimator(View view) {
        Utils.requireNonNull(view, "view == null");
        if (this.mConfig.mAnimatorCreator == null) {
            return null;
        }
        return this.mConfig.mAnimatorCreator.createInAnimator(view);
    }

    protected Animator onCreateOutAnimator(View view) {
        Utils.requireNonNull(view, "view == null");
        if (this.mConfig.mAnimatorCreator == null) {
            return null;
        }
        return this.mConfig.mAnimatorCreator.createOutAnimator(view);
    }

    @Override // per.goweii.anylayer.ViewManager.OnLifeListener
    public void onAttach() {
        this.mListenerHolder.bindClickListeners(this);
        this.mListenerHolder.notifyVisibleChangeOnShow(this);
        this.mListenerHolder.notifyDataBinder(this);
    }

    public void onPreDraw() {
        this.mListenerHolder.notifyLayerOnShowing(this);
        cancelAnimator();
        if (this.mShowWithAnim) {
            Animator animatorOnCreateInAnimator = onCreateInAnimator(this.mViewManager.getChild());
            this.mAnimatorIn = animatorOnCreateInAnimator;
            if (animatorOnCreateInAnimator != null) {
                animatorOnCreateInAnimator.addListener(new Animator.AnimatorListener() { // from class: per.goweii.anylayer.Layer.1
                    private boolean beenCanceled = false;

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (this.beenCanceled) {
                            return;
                        }
                        Layer.this.onShow();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        this.beenCanceled = true;
                    }
                });
                this.mAnimatorIn.start();
                return;
            } else {
                onShow();
                return;
            }
        }
        onShow();
    }

    public void onShow() {
        this.mListenerHolder.notifyLayerOnShown(this);
        if (this.mAnimatorIn != null) {
            this.mAnimatorIn = null;
        }
    }

    public void onPerRemove() {
        this.mListenerHolder.notifyLayerOnDismissing(this);
        cancelAnimator();
        if (this.mDismissWithAnim) {
            Animator animatorOnCreateOutAnimator = onCreateOutAnimator(this.mViewManager.getChild());
            this.mAnimatorOut = animatorOnCreateOutAnimator;
            if (animatorOnCreateOutAnimator != null) {
                animatorOnCreateOutAnimator.addListener(new Animator.AnimatorListener() { // from class: per.goweii.anylayer.Layer.2
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        Layer.this.mViewManager.detach();
                    }
                });
                this.mAnimatorOut.start();
                return;
            } else {
                this.mViewManager.detach();
                return;
            }
        }
        this.mViewManager.detach();
    }

    @Override // per.goweii.anylayer.ViewManager.OnLifeListener
    public void onDetach() {
        this.mListenerHolder.notifyVisibleChangeOnDismiss(this);
        this.mListenerHolder.notifyLayerOnDismissed(this);
        if (this.mAnimatorOut != null) {
            this.mAnimatorOut = null;
        }
    }

    private void cancelAnimator() {
        Animator animator = this.mAnimatorIn;
        if (animator != null) {
            animator.cancel();
            this.mAnimatorIn = null;
        }
        Animator animator2 = this.mAnimatorOut;
        if (animator2 != null) {
            animator2.cancel();
            this.mAnimatorOut = null;
        }
    }

    @Override // per.goweii.anylayer.ViewManager.OnKeyListener
    public boolean onKey(int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || i != 4) {
            return false;
        }
        if (!this.mConfig.mCancelableOnKeyBack) {
            return true;
        }
        dismiss();
        return true;
    }

    public void show() {
        show(true);
    }

    public void show(boolean z) {
        this.mShowWithAnim = z;
        this.mViewHolder.setParent(onGetParent());
        this.mViewHolder.setChild((View) Utils.requireNonNull(onCreateChild(LayoutInflater.from(this.mViewHolder.getParent().getContext()), this.mViewHolder.getParent()), "onCreateChild() == null"));
        this.mViewManager.setParent(this.mViewHolder.getParent());
        this.mViewManager.setChild(this.mViewHolder.getChild());
        this.mViewManager.setOnKeyListener(this.mConfig.mInterceptKeyEvent ? this : null);
        this.mViewManager.attach();
    }

    public void dismiss() {
        dismiss(true);
    }

    public void dismiss(boolean z) {
        this.mDismissWithAnim = z;
        onPerRemove();
    }

    public boolean isShow() {
        return this.mViewManager.isAttached();
    }

    public ViewManager getViewManager() {
        return this.mViewManager;
    }

    public ViewGroup getParent() {
        return this.mViewHolder.getParent();
    }

    public View getChild() {
        return this.mViewHolder.getChild();
    }

    public <V extends View> V getView(int i) {
        if (this.mViewCaches == null) {
            this.mViewCaches = new SparseArray<>();
        }
        if (this.mViewCaches.indexOfKey(i) < 0) {
            V v = (V) getChild().findViewById(i);
            this.mViewCaches.put(i, v);
            return v;
        }
        return (V) this.mViewCaches.get(i);
    }

    public Layer parent(ViewGroup viewGroup) {
        Utils.requireNonNull(viewGroup, "parent == null");
        this.mViewHolder.setParent(viewGroup);
        return this;
    }

    public Layer child(View view) {
        Utils.requireNonNull(view, "child == null");
        this.mViewHolder.setChild(view);
        return this;
    }

    public Layer child(int i) {
        this.mConfig.mChildId = i;
        return this;
    }

    public Layer animator(AnimatorCreator animatorCreator) {
        this.mConfig.mAnimatorCreator = animatorCreator;
        return this;
    }

    public Layer interceptKeyEvent(boolean z) {
        this.mConfig.mInterceptKeyEvent = z;
        return this;
    }

    public Layer cancelableOnKeyBack(boolean z) {
        this.mConfig.mCancelableOnKeyBack = z;
        return this;
    }

    public Layer bindData(DataBinder dataBinder) {
        this.mListenerHolder.addDataBinder(dataBinder);
        return this;
    }

    public Layer onVisibleChangeListener(OnVisibleChangeListener onVisibleChangeListener) {
        this.mListenerHolder.addOnVisibleChangeListener(onVisibleChangeListener);
        return this;
    }

    public Layer onShowListener(OnShowListener onShowListener) {
        this.mListenerHolder.addOnLayerShowListener(onShowListener);
        return this;
    }

    public Layer onDismissListener(OnDismissListener onDismissListener) {
        this.mListenerHolder.addOnLayerDismissListener(onDismissListener);
        return this;
    }

    public Layer onClickToDismiss(final OnClickListener onClickListener, int... iArr) {
        onClick(new OnClickListener() { // from class: per.goweii.anylayer.Layer.3
            @Override // per.goweii.anylayer.Layer.OnClickListener
            public void onClick(Layer layer, View view) {
                OnClickListener onClickListener2 = onClickListener;
                if (onClickListener2 != null) {
                    onClickListener2.onClick(layer, view);
                }
                Layer.this.dismiss();
            }
        }, iArr);
        return this;
    }

    public Layer onClickToDismiss(int... iArr) {
        onClick(new OnClickListener() { // from class: per.goweii.anylayer.Layer.4
            @Override // per.goweii.anylayer.Layer.OnClickListener
            public void onClick(Layer layer, View view) {
                Layer.this.dismiss();
            }
        }, iArr);
        return this;
    }

    public Layer onClick(OnClickListener onClickListener, int... iArr) {
        this.mListenerHolder.addOnClickListener(onClickListener, iArr);
        return this;
    }

    protected static class Config {
        private int mChildId;
        private boolean mInterceptKeyEvent = true;
        private boolean mCancelableOnKeyBack = true;
        private AnimatorCreator mAnimatorCreator = null;

        protected Config() {
        }
    }

    public static class ViewHolder {
        private View mChild;
        private ViewGroup mParent;

        public void setParent(ViewGroup viewGroup) {
            this.mParent = (ViewGroup) Utils.requireNonNull(viewGroup, "parent == null");
        }

        public ViewGroup getParent() {
            return (ViewGroup) Utils.requireNonNull(this.mParent, "parent == null, You have to call it after the show method");
        }

        public void setChild(View view) {
            this.mChild = (View) Utils.requireNonNull(view, "child == null");
        }

        public View getChild() {
            return (View) Utils.requireNonNull(this.mChild, "child == null, You have to call it after the show method");
        }
    }

    protected static class ListenerHolder {
        private SparseArray<OnClickListener> mOnClickListeners = null;
        private List<DataBinder> mDataBinders = null;
        private List<OnVisibleChangeListener> mOnVisibleChangeListeners = null;
        private List<OnShowListener> mOnShowListeners = null;
        private List<OnDismissListener> mOnDismissListeners = null;

        protected ListenerHolder() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void bindClickListeners(final Layer layer) {
            Utils.requireNonNull(layer, "layer == null");
            if (this.mOnClickListeners == null) {
                return;
            }
            for (int i = 0; i < this.mOnClickListeners.size(); i++) {
                int iKeyAt = this.mOnClickListeners.keyAt(i);
                final OnClickListener onClickListenerValueAt = this.mOnClickListeners.valueAt(i);
                layer.getView(iKeyAt).setOnClickListener(new View.OnClickListener() { // from class: per.goweii.anylayer.Layer.ListenerHolder.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        onClickListenerValueAt.onClick(layer, view);
                    }
                });
            }
        }

        public void addOnClickListener(OnClickListener onClickListener, int... iArr) {
            if (this.mOnClickListeners == null) {
                this.mOnClickListeners = new SparseArray<>();
            }
            if (iArr == null || iArr.length <= 0) {
                return;
            }
            for (int i : iArr) {
                if (this.mOnClickListeners.indexOfKey(i) < 0) {
                    this.mOnClickListeners.put(i, onClickListener);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataBinder(DataBinder dataBinder) {
            if (this.mDataBinders == null) {
                this.mDataBinders = new ArrayList(1);
            }
            this.mDataBinders.add(dataBinder);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addOnVisibleChangeListener(OnVisibleChangeListener onVisibleChangeListener) {
            if (this.mOnVisibleChangeListeners == null) {
                this.mOnVisibleChangeListeners = new ArrayList(1);
            }
            this.mOnVisibleChangeListeners.add(onVisibleChangeListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addOnLayerShowListener(OnShowListener onShowListener) {
            if (this.mOnShowListeners == null) {
                this.mOnShowListeners = new ArrayList(1);
            }
            this.mOnShowListeners.add(onShowListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addOnLayerDismissListener(OnDismissListener onDismissListener) {
            if (this.mOnDismissListeners == null) {
                this.mOnDismissListeners = new ArrayList(1);
            }
            this.mOnDismissListeners.add(onDismissListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyDataBinder(Layer layer) {
            Utils.requireNonNull(layer, "layer == null");
            List<DataBinder> list = this.mDataBinders;
            if (list != null) {
                Iterator<DataBinder> it = list.iterator();
                while (it.hasNext()) {
                    it.next().bindData(layer);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyVisibleChangeOnShow(Layer layer) {
            Utils.requireNonNull(layer, "layer == null");
            List<OnVisibleChangeListener> list = this.mOnVisibleChangeListeners;
            if (list != null) {
                Iterator<OnVisibleChangeListener> it = list.iterator();
                while (it.hasNext()) {
                    it.next().onShow(layer);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyVisibleChangeOnDismiss(Layer layer) {
            Utils.requireNonNull(layer, "layer == null");
            List<OnVisibleChangeListener> list = this.mOnVisibleChangeListeners;
            if (list != null) {
                Iterator<OnVisibleChangeListener> it = list.iterator();
                while (it.hasNext()) {
                    it.next().onDismiss(layer);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyLayerOnShowing(Layer layer) {
            Utils.requireNonNull(layer, "layer == null");
            List<OnShowListener> list = this.mOnShowListeners;
            if (list != null) {
                Iterator<OnShowListener> it = list.iterator();
                while (it.hasNext()) {
                    it.next().onShowing(layer);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyLayerOnShown(Layer layer) {
            Utils.requireNonNull(layer, "layer == null");
            List<OnShowListener> list = this.mOnShowListeners;
            if (list != null) {
                Iterator<OnShowListener> it = list.iterator();
                while (it.hasNext()) {
                    it.next().onShown(layer);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyLayerOnDismissing(Layer layer) {
            Utils.requireNonNull(layer, "layer == null");
            List<OnDismissListener> list = this.mOnDismissListeners;
            if (list != null) {
                Iterator<OnDismissListener> it = list.iterator();
                while (it.hasNext()) {
                    it.next().onDismissing(layer);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyLayerOnDismissed(Layer layer) {
            Utils.requireNonNull(layer, "layer == null");
            List<OnDismissListener> list = this.mOnDismissListeners;
            if (list != null) {
                Iterator<OnDismissListener> it = list.iterator();
                while (it.hasNext()) {
                    it.next().onDismissed(layer);
                }
            }
        }
    }
}
