package com.github.mikephil.charting.matrix;

/* JADX INFO: loaded from: classes.dex */
public final class Vector3 {

    /* JADX INFO: renamed from: x */
    public float f66x;

    /* JADX INFO: renamed from: y */
    public float f67y;

    /* JADX INFO: renamed from: z */
    public float f68z;
    public static final Vector3 ZERO = new Vector3(0.0f, 0.0f, 0.0f);
    public static final Vector3 UNIT_X = new Vector3(1.0f, 0.0f, 0.0f);
    public static final Vector3 UNIT_Y = new Vector3(0.0f, 1.0f, 0.0f);
    public static final Vector3 UNIT_Z = new Vector3(0.0f, 0.0f, 1.0f);

    public Vector3() {
    }

    public Vector3(float[] fArr) {
        set(fArr[0], fArr[1], fArr[2]);
    }

    public Vector3(float f, float f2, float f3) {
        set(f, f2, f3);
    }

    public Vector3(Vector3 vector3) {
        set(vector3);
    }

    public final void add(Vector3 vector3) {
        this.f66x += vector3.f66x;
        this.f67y += vector3.f67y;
        this.f68z += vector3.f68z;
    }

    public final void add(float f, float f2, float f3) {
        this.f66x += f;
        this.f67y += f2;
        this.f68z += f3;
    }

    public final void subtract(Vector3 vector3) {
        this.f66x -= vector3.f66x;
        this.f67y -= vector3.f67y;
        this.f68z -= vector3.f68z;
    }

    public final void subtractMultiple(Vector3 vector3, float f) {
        this.f66x -= vector3.f66x * f;
        this.f67y -= vector3.f67y * f;
        this.f68z -= vector3.f68z * f;
    }

    public final void multiply(float f) {
        this.f66x *= f;
        this.f67y *= f;
        this.f68z *= f;
    }

    public final void multiply(Vector3 vector3) {
        this.f66x *= vector3.f66x;
        this.f67y *= vector3.f67y;
        this.f68z *= vector3.f68z;
    }

    public final void divide(float f) {
        if (f != 0.0f) {
            this.f66x /= f;
            this.f67y /= f;
            this.f68z /= f;
        }
    }

    public final void set(Vector3 vector3) {
        this.f66x = vector3.f66x;
        this.f67y = vector3.f67y;
        this.f68z = vector3.f68z;
    }

    public final void set(float f, float f2, float f3) {
        this.f66x = f;
        this.f67y = f2;
        this.f68z = f3;
    }

    public final float dot(Vector3 vector3) {
        return (this.f66x * vector3.f66x) + (this.f67y * vector3.f67y) + (this.f68z * vector3.f68z);
    }

    public final Vector3 cross(Vector3 vector3) {
        float f = this.f67y;
        float f2 = vector3.f68z;
        float f3 = this.f68z;
        float f4 = vector3.f67y;
        float f5 = (f * f2) - (f3 * f4);
        float f6 = vector3.f66x;
        float f7 = this.f66x;
        return new Vector3(f5, (f3 * f6) - (f2 * f7), (f7 * f4) - (f * f6));
    }

    public final float length() {
        return (float) Math.sqrt(length2());
    }

    public final float length2() {
        float f = this.f66x;
        float f2 = this.f67y;
        float f3 = (f * f) + (f2 * f2);
        float f4 = this.f68z;
        return f3 + (f4 * f4);
    }

    public final float distance2(Vector3 vector3) {
        float f = this.f66x - vector3.f66x;
        float f2 = this.f67y - vector3.f67y;
        float f3 = this.f68z - vector3.f68z;
        return (f * f) + (f2 * f2) + (f3 * f3);
    }

    public final float normalize() {
        float length = length();
        if (length != 0.0f) {
            this.f66x /= length;
            this.f67y /= length;
            this.f68z /= length;
        }
        return length;
    }

    public final void zero() {
        set(0.0f, 0.0f, 0.0f);
    }

    public final boolean pointsInSameDirection(Vector3 vector3) {
        return dot(vector3) > 0.0f;
    }
}
