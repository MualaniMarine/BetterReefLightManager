package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.utils.ObjectPool;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MPPointD extends ObjectPool.Poolable {
    private static ObjectPool<MPPointD> pool;

    /* JADX INFO: renamed from: x */
    public double f74x;

    /* JADX INFO: renamed from: y */
    public double f75y;

    static {
        ObjectPool<MPPointD> objectPoolCreate = ObjectPool.create(64, new MPPointD(0.0d, 0.0d));
        pool = objectPoolCreate;
        objectPoolCreate.setReplenishPercentage(0.5f);
    }

    public static MPPointD getInstance(double d, double d2) {
        MPPointD mPPointD = (MPPointD) pool.get();
        mPPointD.f74x = d;
        mPPointD.f75y = d2;
        return mPPointD;
    }

    public static void recycleInstance(MPPointD mPPointD) {
        pool.recycle(mPPointD);
    }

    public static void recycleInstances(List<MPPointD> list) {
        pool.recycle(list);
    }

    @Override // com.github.mikephil.charting.utils.ObjectPool.Poolable
    protected ObjectPool.Poolable instantiate() {
        return new MPPointD(0.0d, 0.0d);
    }

    private MPPointD(double d, double d2) {
        this.f74x = d;
        this.f75y = d2;
    }

    public String toString() {
        return "MPPointD, x: " + this.f74x + ", y: " + this.f75y;
    }
}
