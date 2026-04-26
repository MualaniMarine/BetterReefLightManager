package com.tencent.bugly.crashreport.common.info;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class PlugInBean implements Parcelable {
    public static final Parcelable.Creator<PlugInBean> CREATOR = new Parcelable.Creator<PlugInBean>() { // from class: com.tencent.bugly.crashreport.common.info.PlugInBean.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ PlugInBean createFromParcel(Parcel parcel) {
            return new PlugInBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ PlugInBean[] newArray(int i) {
            return new PlugInBean[i];
        }
    };

    /* JADX INFO: renamed from: a */
    public final String f199a;

    /* JADX INFO: renamed from: b */
    public final String f200b;

    /* JADX INFO: renamed from: c */
    public final String f201c;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PlugInBean(String str, String str2, String str3) {
        this.f199a = str;
        this.f200b = str2;
        this.f201c = str3;
    }

    public String toString() {
        return "plid:" + this.f199a + " plV:" + this.f200b + " plUUID:" + this.f201c;
    }

    public PlugInBean(Parcel parcel) {
        this.f199a = parcel.readString();
        this.f200b = parcel.readString();
        this.f201c = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f199a);
        parcel.writeString(this.f200b);
        parcel.writeString(this.f201c);
    }
}
