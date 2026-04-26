package com.github.mikephil.charting.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import com.github.mikephil.charting.animation.Easing;

/* JADX INFO: loaded from: classes.dex */
public class ChartAnimator {
    private ValueAnimator.AnimatorUpdateListener mListener;
    protected float mPhaseY = 1.0f;
    protected float mPhaseX = 1.0f;

    public ChartAnimator() {
    }

    public ChartAnimator(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.mListener = animatorUpdateListener;
    }

    public void animateXY(int i, int i2, EasingFunction easingFunction, EasingFunction easingFunction2) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "phaseY", 0.0f, 1.0f);
        objectAnimatorOfFloat.setInterpolator(easingFunction2);
        objectAnimatorOfFloat.setDuration(i2);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, "phaseX", 0.0f, 1.0f);
        objectAnimatorOfFloat2.setInterpolator(easingFunction);
        objectAnimatorOfFloat2.setDuration(i);
        if (i > i2) {
            objectAnimatorOfFloat2.addUpdateListener(this.mListener);
        } else {
            objectAnimatorOfFloat.addUpdateListener(this.mListener);
        }
        objectAnimatorOfFloat2.start();
        objectAnimatorOfFloat.start();
    }

    public void animateX(int i, EasingFunction easingFunction) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "phaseX", 0.0f, 1.0f);
        objectAnimatorOfFloat.setInterpolator(easingFunction);
        objectAnimatorOfFloat.setDuration(i);
        objectAnimatorOfFloat.addUpdateListener(this.mListener);
        objectAnimatorOfFloat.start();
    }

    public void animateY(int i, EasingFunction easingFunction) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "phaseY", 0.0f, 1.0f);
        objectAnimatorOfFloat.setInterpolator(easingFunction);
        objectAnimatorOfFloat.setDuration(i);
        objectAnimatorOfFloat.addUpdateListener(this.mListener);
        objectAnimatorOfFloat.start();
    }

    public void animateXY(int i, int i2, Easing.EasingOption easingOption, Easing.EasingOption easingOption2) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "phaseY", 0.0f, 1.0f);
        objectAnimatorOfFloat.setInterpolator(Easing.getEasingFunctionFromOption(easingOption2));
        objectAnimatorOfFloat.setDuration(i2);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, "phaseX", 0.0f, 1.0f);
        objectAnimatorOfFloat2.setInterpolator(Easing.getEasingFunctionFromOption(easingOption));
        objectAnimatorOfFloat2.setDuration(i);
        if (i > i2) {
            objectAnimatorOfFloat2.addUpdateListener(this.mListener);
        } else {
            objectAnimatorOfFloat.addUpdateListener(this.mListener);
        }
        objectAnimatorOfFloat2.start();
        objectAnimatorOfFloat.start();
    }

    public void animateX(int i, Easing.EasingOption easingOption) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "phaseX", 0.0f, 1.0f);
        objectAnimatorOfFloat.setInterpolator(Easing.getEasingFunctionFromOption(easingOption));
        objectAnimatorOfFloat.setDuration(i);
        objectAnimatorOfFloat.addUpdateListener(this.mListener);
        objectAnimatorOfFloat.start();
    }

    public void animateY(int i, Easing.EasingOption easingOption) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "phaseY", 0.0f, 1.0f);
        objectAnimatorOfFloat.setInterpolator(Easing.getEasingFunctionFromOption(easingOption));
        objectAnimatorOfFloat.setDuration(i);
        objectAnimatorOfFloat.addUpdateListener(this.mListener);
        objectAnimatorOfFloat.start();
    }

    public void animateXY(int i, int i2) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "phaseY", 0.0f, 1.0f);
        objectAnimatorOfFloat.setDuration(i2);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, "phaseX", 0.0f, 1.0f);
        objectAnimatorOfFloat2.setDuration(i);
        if (i > i2) {
            objectAnimatorOfFloat2.addUpdateListener(this.mListener);
        } else {
            objectAnimatorOfFloat.addUpdateListener(this.mListener);
        }
        objectAnimatorOfFloat2.start();
        objectAnimatorOfFloat.start();
    }

    public void animateX(int i) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "phaseX", 0.0f, 1.0f);
        objectAnimatorOfFloat.setDuration(i);
        objectAnimatorOfFloat.addUpdateListener(this.mListener);
        objectAnimatorOfFloat.start();
    }

    public void animateY(int i) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "phaseY", 0.0f, 1.0f);
        objectAnimatorOfFloat.setDuration(i);
        objectAnimatorOfFloat.addUpdateListener(this.mListener);
        objectAnimatorOfFloat.start();
    }

    public float getPhaseY() {
        return this.mPhaseY;
    }

    public void setPhaseY(float f) {
        this.mPhaseY = f;
    }

    public float getPhaseX() {
        return this.mPhaseX;
    }

    public void setPhaseX(float f) {
        this.mPhaseX = f;
    }
}
