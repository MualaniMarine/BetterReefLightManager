package com.github.mikephil.charting.highlight;

/* JADX INFO: loaded from: classes.dex */
public final class Range {
    public float from;

    /* JADX INFO: renamed from: to */
    public float f65to;

    public Range(float f, float f2) {
        this.from = f;
        this.f65to = f2;
    }

    public boolean contains(float f) {
        return f > this.from && f <= this.f65to;
    }

    public boolean isLarger(float f) {
        return f > this.f65to;
    }

    public boolean isSmaller(float f) {
        return f < this.from;
    }
}
