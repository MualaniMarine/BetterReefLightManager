package com.github.mikephil.charting.data;

import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseEntry {
    private Object mData;
    private Drawable mIcon;

    /* JADX INFO: renamed from: y */
    private float f57y;

    public BaseEntry() {
        this.f57y = 0.0f;
        this.mData = null;
        this.mIcon = null;
    }

    public BaseEntry(float f) {
        this.f57y = 0.0f;
        this.mData = null;
        this.mIcon = null;
        this.f57y = f;
    }

    public BaseEntry(float f, Object obj) {
        this(f);
        this.mData = obj;
    }

    public BaseEntry(float f, Drawable drawable) {
        this(f);
        this.mIcon = drawable;
    }

    public BaseEntry(float f, Drawable drawable, Object obj) {
        this(f);
        this.mIcon = drawable;
        this.mData = obj;
    }

    public float getY() {
        return this.f57y;
    }

    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    public void setY(float f) {
        this.f57y = f;
    }

    public Object getData() {
        return this.mData;
    }

    public void setData(Object obj) {
        this.mData = obj;
    }
}
