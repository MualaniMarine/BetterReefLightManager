package com.github.mikephil.charting.data;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;
import com.github.mikephil.charting.utils.Utils;

/* JADX INFO: loaded from: classes.dex */
public class Entry extends BaseEntry implements Parcelable {
    public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator<Entry>() { // from class: com.github.mikephil.charting.data.Entry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Entry createFromParcel(Parcel parcel) {
            return new Entry(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Entry[] newArray(int i) {
            return new Entry[i];
        }
    };

    /* JADX INFO: renamed from: x */
    private float f59x;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Entry() {
        this.f59x = 0.0f;
    }

    public Entry(float f, float f2) {
        super(f2);
        this.f59x = 0.0f;
        this.f59x = f;
    }

    public Entry(float f, float f2, Object obj) {
        super(f2, obj);
        this.f59x = 0.0f;
        this.f59x = f;
    }

    public Entry(float f, float f2, Drawable drawable) {
        super(f2, drawable);
        this.f59x = 0.0f;
        this.f59x = f;
    }

    public Entry(float f, float f2, Drawable drawable, Object obj) {
        super(f2, drawable, obj);
        this.f59x = 0.0f;
        this.f59x = f;
    }

    public float getX() {
        return this.f59x;
    }

    public void setX(float f) {
        this.f59x = f;
    }

    public Entry copy() {
        return new Entry(this.f59x, getY(), getData());
    }

    public boolean equalTo(Entry entry) {
        return entry != null && entry.getData() == getData() && Math.abs(entry.f59x - this.f59x) <= Utils.FLOAT_EPSILON && Math.abs(entry.getY() - getY()) <= Utils.FLOAT_EPSILON;
    }

    public String toString() {
        return "Entry, x: " + this.f59x + " y: " + getY();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.f59x);
        parcel.writeFloat(getY());
        if (getData() != null) {
            if (getData() instanceof Parcelable) {
                parcel.writeInt(1);
                parcel.writeParcelable((Parcelable) getData(), i);
                return;
            }
            throw new ParcelFormatException("Cannot parcel an Entry with non-parcelable data");
        }
        parcel.writeInt(0);
    }

    protected Entry(Parcel parcel) {
        this.f59x = 0.0f;
        this.f59x = parcel.readFloat();
        setY(parcel.readFloat());
        if (parcel.readInt() == 1) {
            setData(parcel.readParcelable(Object.class.getClassLoader()));
        }
    }
}
