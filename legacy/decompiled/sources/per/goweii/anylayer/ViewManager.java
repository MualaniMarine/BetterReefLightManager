package per.goweii.anylayer;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/* JADX INFO: loaded from: classes.dex */
public final class ViewManager {
    private ViewGroup mParent = null;
    private View mChild = null;
    private LayerKeyListener mLayerKeyListener = null;
    private LayerGlobalFocusChangeListener mLayerGlobalFocusChangeListener = null;
    private View currentKeyView = null;
    private OnLifeListener mOnLifeListener = null;
    private OnPreDrawListener mOnPreDrawListener = null;
    private OnKeyListener mOnKeyListener = null;

    public interface OnKeyListener {
        boolean onKey(int i, KeyEvent keyEvent);
    }

    public interface OnLifeListener {
        void onAttach();

        void onDetach();
    }

    public interface OnPreDrawListener {
        void onPreDraw();
    }

    public void setParent(ViewGroup viewGroup) {
        Utils.requireNonNull(viewGroup, "parent == null");
        this.mParent = viewGroup;
    }

    public void setChild(View view) {
        Utils.requireNonNull(view, "child == null");
        this.mChild = view;
    }

    public ViewGroup getParent() {
        return this.mParent;
    }

    public View getChild() {
        return this.mChild;
    }

    private void checkChildParent() {
        ViewGroup viewGroup = (ViewGroup) this.mChild.getParent();
        if (viewGroup == null || viewGroup == this.mParent) {
            return;
        }
        viewGroup.removeView(this.mChild);
    }

    public void attach() {
        if (this.mParent == null) {
            throw new RuntimeException("parent cannot be null on attach");
        }
        if (this.mChild == null) {
            throw new RuntimeException("parent cannot be null on attach");
        }
        checkChildParent();
        if (isAttached()) {
            return;
        }
        this.mParent.post(new Runnable() { // from class: per.goweii.anylayer.ViewManager.1
            @Override // java.lang.Runnable
            public void run() {
                if (ViewManager.this.isAttached()) {
                    return;
                }
                ViewManager.this.onAttach();
            }
        });
    }

    public void detach() {
        if (isAttached()) {
            onDetach();
        }
    }

    public boolean isAttached() {
        View view = this.mChild;
        return (view == null || view.getParent() == null) ? false : true;
    }

    public void setOnLifeListener(OnLifeListener onLifeListener) {
        this.mOnLifeListener = onLifeListener;
    }

    public void setOnPreDrawListener(OnPreDrawListener onPreDrawListener) {
        this.mOnPreDrawListener = onPreDrawListener;
    }

    public void setOnKeyListener(OnKeyListener onKeyListener) {
        this.mOnKeyListener = onKeyListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAttach() {
        this.mChild.setFocusable(true);
        this.mChild.setFocusableInTouchMode(true);
        this.mChild.requestFocus();
        this.currentKeyView = this.mChild;
        if (this.mOnKeyListener != null) {
            this.mLayerGlobalFocusChangeListener = new LayerGlobalFocusChangeListener();
            this.mChild.getViewTreeObserver().addOnGlobalFocusChangeListener(this.mLayerGlobalFocusChangeListener);
            LayerKeyListener layerKeyListener = new LayerKeyListener();
            this.mLayerKeyListener = layerKeyListener;
            this.currentKeyView.setOnKeyListener(layerKeyListener);
        }
        this.mChild.getViewTreeObserver().addOnPreDrawListener(new LayerPreDrawListener());
        this.mParent.addView(this.mChild);
        OnLifeListener onLifeListener = this.mOnLifeListener;
        if (onLifeListener != null) {
            onLifeListener.onAttach();
        }
    }

    private void onDetach() {
        View view = this.currentKeyView;
        if (view != null) {
            view.setOnKeyListener(null);
            this.mLayerKeyListener = null;
            this.mChild.getViewTreeObserver().removeOnGlobalFocusChangeListener(this.mLayerGlobalFocusChangeListener);
            this.mLayerGlobalFocusChangeListener = null;
        }
        this.mParent.removeView(this.mChild);
        OnLifeListener onLifeListener = this.mOnLifeListener;
        if (onLifeListener != null) {
            onLifeListener.onDetach();
        }
    }

    private final class LayerPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        private LayerPreDrawListener() {
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            if (ViewManager.this.mChild.getViewTreeObserver().isAlive()) {
                ViewManager.this.mChild.getViewTreeObserver().removeOnPreDrawListener(this);
            }
            if (ViewManager.this.mOnPreDrawListener == null) {
                return true;
            }
            ViewManager.this.mOnPreDrawListener.onPreDraw();
            return true;
        }
    }

    private final class LayerGlobalFocusChangeListener implements ViewTreeObserver.OnGlobalFocusChangeListener {
        private LayerGlobalFocusChangeListener() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalFocusChangeListener
        public void onGlobalFocusChanged(View view, View view2) {
            if (ViewManager.this.currentKeyView != null) {
                ViewManager.this.currentKeyView.setOnKeyListener(null);
            }
            if (view != null) {
                view.setOnKeyListener(null);
            }
            if (view2 != null) {
                ViewManager.this.currentKeyView = view2;
                ViewManager.this.currentKeyView.setOnKeyListener(ViewManager.this.mLayerKeyListener);
            }
        }
    }

    private final class LayerKeyListener implements View.OnKeyListener {
        private LayerKeyListener() {
        }

        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (ViewManager.this.isAttached() && ViewManager.this.mOnKeyListener != null) {
                return ViewManager.this.mOnKeyListener.onKey(i, keyEvent);
            }
            return false;
        }
    }
}
