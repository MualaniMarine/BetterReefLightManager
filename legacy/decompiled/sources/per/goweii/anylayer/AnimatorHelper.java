package per.goweii.anylayer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class AnimatorHelper {
    public static Animator createAlphaInAnim(View view) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        return objectAnimatorOfFloat;
    }

    public static Animator createAlphaOutAnim(View view) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", view.getAlpha(), 0.0f);
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        return objectAnimatorOfFloat;
    }

    public static Animator createZoomAlphaInAnim(View view, int i, int i2, float f) {
        if (view == null) {
            return null;
        }
        view.setPivotX(i);
        view.setPivotY(i2);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "scaleX", f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, "scaleY", f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    public static Animator createZoomAlphaInAnim(View view, int i, int i2) {
        return createZoomAlphaInAnim(view, i, i2, 0.618f);
    }

    public static Animator createZoomAlphaOutAnim(View view, int i, int i2, float f) {
        if (view == null) {
            return null;
        }
        view.setPivotX(i);
        view.setPivotY(i2);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", view.getAlpha(), 0.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "scaleX", view.getScaleX(), f);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, "scaleY", view.getScaleY(), f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    public static Animator createZoomAlphaOutAnim(View view, int i, int i2) {
        return createZoomAlphaOutAnim(view, i, i2, 0.618f);
    }

    public static Animator createZoomAlphaInAnim(View view, float f, float f2) {
        if (view == null) {
            return null;
        }
        return createZoomAlphaInAnim(view, (int) (view.getMeasuredWidth() * Utils.floatRange01(f)), (int) (view.getMeasuredHeight() * Utils.floatRange01(f2)));
    }

    public static Animator createZoomAlphaInAnim(View view, float f, float f2, float f3) {
        if (view == null) {
            return null;
        }
        return createZoomAlphaInAnim(view, (int) (view.getMeasuredWidth() * Utils.floatRange01(f)), (int) (view.getMeasuredHeight() * Utils.floatRange01(f2)), f3);
    }

    public static Animator createZoomAlphaOutAnim(View view, float f, float f2) {
        if (view == null) {
            return null;
        }
        return createZoomAlphaOutAnim(view, (int) (view.getMeasuredWidth() * Utils.floatRange01(f)), (int) (view.getMeasuredHeight() * Utils.floatRange01(f2)));
    }

    public static Animator createZoomAlphaOutAnim(View view, float f, float f2, float f3) {
        if (view == null) {
            return null;
        }
        return createZoomAlphaOutAnim(view, (int) (view.getMeasuredWidth() * Utils.floatRange01(f)), (int) (view.getMeasuredHeight() * Utils.floatRange01(f2)), f3);
    }

    public static Animator createZoomAlphaInAnim(View view, float f) {
        return createZoomAlphaInAnim(view, 0.5f, 0.5f, f);
    }

    public static Animator createZoomAlphaInAnim(View view) {
        if (view == null) {
            return null;
        }
        return createZoomAlphaInAnim(view, 0.5f, 0.5f);
    }

    public static Animator createZoomAlphaOutAnim(View view, float f) {
        if (view == null) {
            return null;
        }
        return createZoomAlphaOutAnim(view, 0.5f, 0.5f, f);
    }

    public static Animator createZoomAlphaOutAnim(View view) {
        if (view == null) {
            return null;
        }
        return createZoomAlphaOutAnim(view, 0.5f, 0.5f);
    }

    public static Animator createTopInAnim(View view) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationY", -view.getBottom(), 0.0f);
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        return objectAnimatorOfFloat;
    }

    public static Animator createTopOutAnim(View view) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), -view.getBottom());
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        return objectAnimatorOfFloat;
    }

    public static Animator createTopAlphaInAnim(View view, float f) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "translationY", -(f * view.getMeasuredHeight()), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    public static Animator createTopAlphaInAnim(View view) {
        return createTopAlphaInAnim(view, 0.38200003f);
    }

    public static Animator createTopAlphaOutAnim(View view, float f) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), -(f * view.getMeasuredHeight()));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    public static Animator createTopAlphaOutAnim(View view) {
        return createTopAlphaOutAnim(view, 0.38200003f);
    }

    public static Animator createBottomInAnim(View view) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationY", ((ViewGroup) view.getParent()).getMeasuredHeight() - view.getTop(), 0.0f);
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        return objectAnimatorOfFloat;
    }

    public static Animator createBottomOutAnim(View view) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), ((ViewGroup) view.getParent()).getMeasuredHeight() - view.getTop());
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        return objectAnimatorOfFloat;
    }

    public static Animator createBottomAlphaInAnim(View view, float f) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "translationY", f * view.getMeasuredHeight(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    public static Animator createBottomAlphaInAnim(View view) {
        return createBottomAlphaInAnim(view, 0.38200003f);
    }

    public static Animator createBottomAlphaOutAnim(View view, float f) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), f * view.getMeasuredHeight());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    public static Animator createBottomAlphaOutAnim(View view) {
        return createBottomAlphaOutAnim(view, 0.38200003f);
    }

    public static Animator createLeftInAnim(View view) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationX", -view.getRight(), 0.0f);
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        objectAnimatorOfFloat.start();
        return objectAnimatorOfFloat;
    }

    public static Animator createLeftOutAnim(View view) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationX", view.getTranslationX(), -view.getRight());
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        return objectAnimatorOfFloat;
    }

    public static Animator createLeftAlphaInAnim(View view, float f) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "translationX", -(f * view.getMeasuredWidth()), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    public static Animator createLeftAlphaInAnim(View view) {
        return createLeftAlphaInAnim(view, 0.38200003f);
    }

    public static Animator createLeftAlphaOutAnim(View view, float f) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "translationX", view.getTranslationX(), -(f * view.getMeasuredWidth()));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    public static Animator createLeftAlphaOutAnim(View view) {
        return createLeftAlphaOutAnim(view, 0.38200003f);
    }

    public static Animator createRightInAnim(View view) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationX", ((ViewGroup) view.getParent()).getMeasuredWidth() - view.getLeft(), 0.0f);
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        return objectAnimatorOfFloat;
    }

    public static Animator createRightOutAnim(View view) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationX", view.getTranslationX(), ((ViewGroup) view.getParent()).getMeasuredWidth() - view.getLeft());
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        return objectAnimatorOfFloat;
    }

    public static Animator createRightAlphaInAnim(View view, float f) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "translationX", f * view.getMeasuredWidth(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    public static Animator createRightAlphaInAnim(View view) {
        return createRightAlphaInAnim(view, 0.38200003f);
    }

    public static Animator createRightAlphaOutAnim(View view, float f) {
        if (view == null) {
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "translationX", view.getTranslationX(), f * view.getMeasuredWidth());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    public static Animator createRightAlphaOutAnim(View view) {
        return createRightAlphaOutAnim(view, 0.38200003f);
    }

    public static Animator createCircularRevealInAnim(View view, int i, int i2) {
        if (view == null || Build.VERSION.SDK_INT < 21) {
            return null;
        }
        Animator animatorCreateCircularReveal = ViewAnimationUtils.createCircularReveal(view, i, i2, 0.0f, (int) Math.sqrt(Math.pow(Math.max(i, view.getMeasuredWidth() - i), 2.0d) + Math.pow(Math.max(i2, view.getMeasuredHeight() - i2), 2.0d)));
        animatorCreateCircularReveal.setInterpolator(new DecelerateInterpolator());
        return animatorCreateCircularReveal;
    }

    public static Animator createCircularRevealInAnim(View view, float f, float f2) {
        if (view == null) {
            return null;
        }
        return createCircularRevealInAnim(view, (int) (view.getMeasuredWidth() * Utils.floatRange01(f)), (int) (view.getMeasuredHeight() * Utils.floatRange01(f2)));
    }

    public static Animator createCircularRevealOutAnim(View view, int i, int i2) {
        if (view == null || Build.VERSION.SDK_INT < 21) {
            return null;
        }
        Animator animatorCreateCircularReveal = ViewAnimationUtils.createCircularReveal(view, i, i2, (int) Math.sqrt(Math.pow(Math.max(i, view.getMeasuredWidth() - i), 2.0d) + Math.pow(Math.max(i2, view.getMeasuredHeight() - i2), 2.0d)), 0.0f);
        animatorCreateCircularReveal.setInterpolator(new DecelerateInterpolator());
        return animatorCreateCircularReveal;
    }

    public static Animator createCircularRevealOutAnim(View view, float f, float f2) {
        if (view == null) {
            return null;
        }
        return createCircularRevealOutAnim(view, (int) (view.getMeasuredWidth() * Utils.floatRange01(f)), (int) (view.getMeasuredHeight() * Utils.floatRange01(f2)));
    }

    public static Animator createZoomInAnim(View view, int i, int i2) {
        if (view == null) {
            return null;
        }
        view.setPivotX(i);
        view.setPivotY(i2);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    public static Animator createZoomOutAnim(View view, int i, int i2) {
        if (view == null) {
            return null;
        }
        view.setPivotX(i);
        view.setPivotY(i2);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "scaleX", view.getScaleX(), 0.618f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "scaleY", view.getScaleY(), 0.618f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        return animatorSet;
    }

    public static Animator createZoomInAnim(View view, float f, float f2) {
        if (view == null) {
            return null;
        }
        return createZoomInAnim(view, (int) (view.getMeasuredWidth() * Utils.floatRange01(f)), (int) (view.getMeasuredHeight() * Utils.floatRange01(f2)));
    }

    public static Animator createZoomOutAnim(View view, float f, float f2) {
        if (view == null) {
            return null;
        }
        return createZoomOutAnim(view, (int) (view.getMeasuredWidth() * Utils.floatRange01(f)), (int) (view.getMeasuredHeight() * Utils.floatRange01(f2)));
    }

    public static Animator createZoomInAnim(View view) {
        if (view == null) {
            return null;
        }
        return createZoomInAnim(view, 0.5f, 0.5f);
    }

    public static Animator createZoomOutAnim(View view) {
        if (view == null) {
            return null;
        }
        return createZoomOutAnim(view, 0.5f, 0.5f);
    }

    public static Animator createDelayedZoomInAnim(View view, int i, int i2) {
        if (view == null) {
            return null;
        }
        if (!(view instanceof ViewGroup)) {
            return createZoomInAnim(view, i, i2);
        }
        final ViewGroup viewGroup = (ViewGroup) view;
        for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
            viewGroup.getChildAt(i3).setAlpha(0.0f);
        }
        viewGroup.setPivotX(i);
        viewGroup.setPivotY(i2);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(viewGroup, "scaleX", 0.0f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(viewGroup, "scaleY", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        objectAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: per.goweii.anylayer.AnimatorHelper.1
            private boolean isChildAnimStart = false;

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                if (this.isChildAnimStart || animatedFraction <= 0.618f) {
                    return;
                }
                this.isChildAnimStart = true;
                ArrayList arrayList = new ArrayList(viewGroup.getChildCount());
                for (int i4 = 0; i4 < viewGroup.getChildCount(); i4++) {
                    ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(viewGroup.getChildAt(i4), "alpha", 0.0f, 1.0f);
                    objectAnimatorOfFloat3.setStartDelay(i4 * 18);
                    objectAnimatorOfFloat3.setDuration(50L);
                    arrayList.add(objectAnimatorOfFloat3);
                }
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(arrayList);
                animatorSet2.setInterpolator(new DecelerateInterpolator());
                animatorSet2.start();
            }
        });
        return animatorSet;
    }

    public static Animator createDelayedZoomOutAnim(View view, int i, int i2) {
        if (view == null) {
            return null;
        }
        if (!(view instanceof ViewGroup)) {
            return createZoomInAnim(view, i, i2);
        }
        final ViewGroup viewGroup = (ViewGroup) view;
        viewGroup.setPivotX(i);
        viewGroup.setPivotY(i2);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(viewGroup, "scaleX", viewGroup.getScaleX(), 0.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(viewGroup, "scaleY", viewGroup.getScaleY(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        objectAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: per.goweii.anylayer.AnimatorHelper.2
            private boolean isChildAnimStart = false;

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (this.isChildAnimStart) {
                    return;
                }
                this.isChildAnimStart = true;
                ArrayList arrayList = new ArrayList(viewGroup.getChildCount());
                for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                    View childAt = viewGroup.getChildAt(childCount);
                    ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(childAt, "alpha", childAt.getAlpha(), 0.0f);
                    objectAnimatorOfFloat3.setStartDelay(((viewGroup.getChildCount() - 1) - childCount) * 18);
                    objectAnimatorOfFloat3.setDuration(50L);
                    arrayList.add(objectAnimatorOfFloat3);
                }
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(arrayList);
                animatorSet2.setInterpolator(new DecelerateInterpolator());
                animatorSet2.start();
            }
        });
        return animatorSet;
    }

    public static Animator createDelayedZoomInAnim(View view, float f, float f2) {
        if (view == null) {
            return null;
        }
        return createDelayedZoomInAnim(view, (int) (view.getMeasuredWidth() * Utils.floatRange01(f)), (int) (view.getMeasuredHeight() * Utils.floatRange01(f2)));
    }

    public static Animator createDelayedZoomOutAnim(View view, float f, float f2) {
        if (view == null) {
            return null;
        }
        return createDelayedZoomOutAnim(view, (int) (view.getMeasuredWidth() * Utils.floatRange01(f)), (int) (view.getMeasuredHeight() * Utils.floatRange01(f2)));
    }
}
