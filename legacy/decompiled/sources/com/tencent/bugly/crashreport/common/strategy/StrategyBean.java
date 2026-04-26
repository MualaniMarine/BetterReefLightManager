package com.tencent.bugly.crashreport.common.strategy;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.bugly.proguard.C0659z;
import java.util.Map;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class StrategyBean implements Parcelable {
    public static final Parcelable.Creator<StrategyBean> CREATOR = new Parcelable.Creator<StrategyBean>() { // from class: com.tencent.bugly.crashreport.common.strategy.StrategyBean.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ StrategyBean createFromParcel(Parcel parcel) {
            return new StrategyBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ StrategyBean[] newArray(int i) {
            return new StrategyBean[i];
        }
    };

    /* JADX INFO: renamed from: a */
    public static String f272a = "https://android.bugly.qq.com/rqd/async";

    /* JADX INFO: renamed from: b */
    public static String f273b = "https://android.bugly.qq.com/rqd/async";

    /* JADX INFO: renamed from: c */
    public long f274c;

    /* JADX INFO: renamed from: d */
    public long f275d;

    /* JADX INFO: renamed from: e */
    public boolean f276e;

    /* JADX INFO: renamed from: f */
    public boolean f277f;

    /* JADX INFO: renamed from: g */
    public boolean f278g;

    /* JADX INFO: renamed from: h */
    public boolean f279h;

    /* JADX INFO: renamed from: i */
    public boolean f280i;

    /* JADX INFO: renamed from: j */
    public boolean f281j;

    /* JADX INFO: renamed from: k */
    public boolean f282k;

    /* JADX INFO: renamed from: l */
    public boolean f283l;

    /* JADX INFO: renamed from: m */
    public boolean f284m;

    /* JADX INFO: renamed from: n */
    public long f285n;

    /* JADX INFO: renamed from: o */
    public long f286o;

    /* JADX INFO: renamed from: p */
    public String f287p;

    /* JADX INFO: renamed from: q */
    public String f288q;

    /* JADX INFO: renamed from: r */
    public String f289r;

    /* JADX INFO: renamed from: s */
    public Map<String, String> f290s;

    /* JADX INFO: renamed from: t */
    public int f291t;

    /* JADX INFO: renamed from: u */
    public long f292u;

    /* JADX INFO: renamed from: v */
    public long f293v;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public StrategyBean() {
        this.f274c = -1L;
        this.f275d = -1L;
        this.f276e = true;
        this.f277f = true;
        this.f278g = true;
        this.f279h = true;
        this.f280i = false;
        this.f281j = true;
        this.f282k = true;
        this.f283l = true;
        this.f284m = true;
        this.f286o = 30000L;
        this.f287p = f272a;
        this.f288q = f273b;
        this.f291t = 10;
        this.f292u = 300000L;
        this.f293v = -1L;
        this.f275d = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("S(@L@L");
        sb.append("@)");
        sb.toString();
        sb.setLength(0);
        sb.append("*^@K#K");
        sb.append("@!");
        this.f289r = sb.toString();
    }

    public StrategyBean(Parcel parcel) {
        this.f274c = -1L;
        this.f275d = -1L;
        boolean z = true;
        this.f276e = true;
        this.f277f = true;
        this.f278g = true;
        this.f279h = true;
        this.f280i = false;
        this.f281j = true;
        this.f282k = true;
        this.f283l = true;
        this.f284m = true;
        this.f286o = 30000L;
        this.f287p = f272a;
        this.f288q = f273b;
        this.f291t = 10;
        this.f292u = 300000L;
        this.f293v = -1L;
        try {
            this.f275d = parcel.readLong();
            this.f276e = parcel.readByte() == 1;
            this.f277f = parcel.readByte() == 1;
            this.f278g = parcel.readByte() == 1;
            this.f287p = parcel.readString();
            this.f288q = parcel.readString();
            this.f289r = parcel.readString();
            this.f290s = C0659z.m516b(parcel);
            this.f279h = parcel.readByte() == 1;
            this.f280i = parcel.readByte() == 1;
            this.f283l = parcel.readByte() == 1;
            this.f284m = parcel.readByte() == 1;
            this.f286o = parcel.readLong();
            this.f281j = parcel.readByte() == 1;
            if (parcel.readByte() != 1) {
                z = false;
            }
            this.f282k = z;
            this.f285n = parcel.readLong();
            this.f291t = parcel.readInt();
            this.f292u = parcel.readLong();
            this.f293v = parcel.readLong();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.f275d);
        parcel.writeByte(this.f276e ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f277f ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f278g ? (byte) 1 : (byte) 0);
        parcel.writeString(this.f287p);
        parcel.writeString(this.f288q);
        parcel.writeString(this.f289r);
        C0659z.m518b(parcel, this.f290s);
        parcel.writeByte(this.f279h ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f280i ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f283l ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f284m ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.f286o);
        parcel.writeByte(this.f281j ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f282k ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.f285n);
        parcel.writeInt(this.f291t);
        parcel.writeLong(this.f292u);
        parcel.writeLong(this.f293v);
    }
}
