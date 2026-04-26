package com.tencent.bugly.crashreport.biz;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.core.os.EnvironmentCompat;
import com.tencent.bugly.proguard.C0659z;
import java.util.Map;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class UserInfoBean implements Parcelable {
    public static final Parcelable.Creator<UserInfoBean> CREATOR = new Parcelable.Creator<UserInfoBean>() { // from class: com.tencent.bugly.crashreport.biz.UserInfoBean.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ UserInfoBean createFromParcel(Parcel parcel) {
            return new UserInfoBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ UserInfoBean[] newArray(int i) {
            return new UserInfoBean[i];
        }
    };

    /* JADX INFO: renamed from: a */
    public long f151a;

    /* JADX INFO: renamed from: b */
    public int f152b;

    /* JADX INFO: renamed from: c */
    public String f153c;

    /* JADX INFO: renamed from: d */
    public String f154d;

    /* JADX INFO: renamed from: e */
    public long f155e;

    /* JADX INFO: renamed from: f */
    public long f156f;

    /* JADX INFO: renamed from: g */
    public long f157g;

    /* JADX INFO: renamed from: h */
    public long f158h;

    /* JADX INFO: renamed from: i */
    public long f159i;

    /* JADX INFO: renamed from: j */
    public String f160j;

    /* JADX INFO: renamed from: k */
    public long f161k;

    /* JADX INFO: renamed from: l */
    public boolean f162l;

    /* JADX INFO: renamed from: m */
    public String f163m;

    /* JADX INFO: renamed from: n */
    public String f164n;

    /* JADX INFO: renamed from: o */
    public int f165o;

    /* JADX INFO: renamed from: p */
    public int f166p;

    /* JADX INFO: renamed from: q */
    public int f167q;

    /* JADX INFO: renamed from: r */
    public Map<String, String> f168r;

    /* JADX INFO: renamed from: s */
    public Map<String, String> f169s;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UserInfoBean() {
        this.f161k = 0L;
        this.f162l = false;
        this.f163m = EnvironmentCompat.MEDIA_UNKNOWN;
        this.f166p = -1;
        this.f167q = -1;
        this.f168r = null;
        this.f169s = null;
    }

    public UserInfoBean(Parcel parcel) {
        this.f161k = 0L;
        this.f162l = false;
        this.f163m = EnvironmentCompat.MEDIA_UNKNOWN;
        this.f166p = -1;
        this.f167q = -1;
        this.f168r = null;
        this.f169s = null;
        this.f152b = parcel.readInt();
        this.f153c = parcel.readString();
        this.f154d = parcel.readString();
        this.f155e = parcel.readLong();
        this.f156f = parcel.readLong();
        this.f157g = parcel.readLong();
        this.f158h = parcel.readLong();
        this.f159i = parcel.readLong();
        this.f160j = parcel.readString();
        this.f161k = parcel.readLong();
        this.f162l = parcel.readByte() == 1;
        this.f163m = parcel.readString();
        this.f166p = parcel.readInt();
        this.f167q = parcel.readInt();
        this.f168r = C0659z.m516b(parcel);
        this.f169s = C0659z.m516b(parcel);
        this.f164n = parcel.readString();
        this.f165o = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f152b);
        parcel.writeString(this.f153c);
        parcel.writeString(this.f154d);
        parcel.writeLong(this.f155e);
        parcel.writeLong(this.f156f);
        parcel.writeLong(this.f157g);
        parcel.writeLong(this.f158h);
        parcel.writeLong(this.f159i);
        parcel.writeString(this.f160j);
        parcel.writeLong(this.f161k);
        parcel.writeByte(this.f162l ? (byte) 1 : (byte) 0);
        parcel.writeString(this.f163m);
        parcel.writeInt(this.f166p);
        parcel.writeInt(this.f167q);
        C0659z.m518b(parcel, this.f168r);
        C0659z.m518b(parcel, this.f169s);
        parcel.writeString(this.f164n);
        parcel.writeInt(this.f165o);
    }
}
