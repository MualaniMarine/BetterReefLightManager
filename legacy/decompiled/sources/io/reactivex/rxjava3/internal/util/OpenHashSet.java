package io.reactivex.rxjava3.internal.util;

/* JADX INFO: loaded from: classes.dex */
public final class OpenHashSet<T> {
    private static final int INT_PHI = -1640531527;
    T[] keys;
    final float loadFactor;
    int mask;
    int maxSize;
    int size;

    static int mix(int x) {
        int i = x * INT_PHI;
        return i ^ (i >>> 16);
    }

    public OpenHashSet() {
        this(16, 0.75f);
    }

    public OpenHashSet(int capacity) {
        this(capacity, 0.75f);
    }

    public OpenHashSet(int i, float f) {
        this.loadFactor = f;
        int iRoundToPowerOfTwo = Pow2.roundToPowerOfTwo(i);
        this.mask = iRoundToPowerOfTwo - 1;
        this.maxSize = (int) (f * iRoundToPowerOfTwo);
        this.keys = (T[]) new Object[iRoundToPowerOfTwo];
    }

    public boolean add(T value) {
        T t;
        T[] tArr = this.keys;
        int i = this.mask;
        int iMix = mix(value.hashCode()) & i;
        T t2 = tArr[iMix];
        if (t2 != null) {
            if (t2.equals(value)) {
                return false;
            }
            do {
                iMix = (iMix + 1) & i;
                t = tArr[iMix];
                if (t == null) {
                }
            } while (!t.equals(value));
            return false;
        }
        tArr[iMix] = value;
        int i2 = this.size + 1;
        this.size = i2;
        if (i2 >= this.maxSize) {
            rehash();
        }
        return true;
    }

    public boolean remove(T value) {
        T t;
        T[] tArr = this.keys;
        int i = this.mask;
        int iMix = mix(value.hashCode()) & i;
        T t2 = tArr[iMix];
        if (t2 == null) {
            return false;
        }
        if (t2.equals(value)) {
            return removeEntry(iMix, tArr, i);
        }
        do {
            iMix = (iMix + 1) & i;
            t = tArr[iMix];
            if (t == null) {
                return false;
            }
        } while (!t.equals(value));
        return removeEntry(iMix, tArr, i);
    }

    boolean removeEntry(int pos, T[] a, int m) {
        int i;
        T t;
        this.size--;
        while (true) {
            int i2 = pos + 1;
            while (true) {
                i = i2 & m;
                t = a[i];
                if (t == null) {
                    a[pos] = null;
                    return true;
                }
                int iMix = mix(t.hashCode()) & m;
                if (pos <= i) {
                    if (pos >= iMix || iMix > i) {
                        break;
                    }
                    i2 = i + 1;
                } else if (pos < iMix || iMix <= i) {
                    i2 = i + 1;
                }
            }
            a[pos] = t;
            pos = i;
        }
    }

    void rehash() {
        T[] tArr = this.keys;
        int length = tArr.length;
        int i = length << 1;
        int i2 = i - 1;
        T[] tArr2 = (T[]) new Object[i];
        int i3 = this.size;
        while (true) {
            int i4 = i3 - 1;
            if (i3 != 0) {
                do {
                    length--;
                } while (tArr[length] == null);
                int iMix = mix(tArr[length].hashCode()) & i2;
                if (tArr2[iMix] != null) {
                    do {
                        iMix = (iMix + 1) & i2;
                    } while (tArr2[iMix] != null);
                }
                tArr2[iMix] = tArr[length];
                i3 = i4;
            } else {
                this.mask = i2;
                this.maxSize = (int) (i * this.loadFactor);
                this.keys = tArr2;
                return;
            }
        }
    }

    public Object[] keys() {
        return this.keys;
    }

    public int size() {
        return this.size;
    }
}
