package com.tencent.bugly.crashreport.crash;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.proguard.C0659z;
import java.util.Map;
import java.util.UUID;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: classes.dex */
public class CrashDetailBean implements Parcelable, Comparable<CrashDetailBean> {
    public static final Parcelable.Creator<CrashDetailBean> CREATOR = new Parcelable.Creator<CrashDetailBean>() { // from class: com.tencent.bugly.crashreport.crash.CrashDetailBean.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ CrashDetailBean createFromParcel(Parcel parcel) {
            return new CrashDetailBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ CrashDetailBean[] newArray(int i) {
            return new CrashDetailBean[i];
        }
    };

    /* JADX INFO: renamed from: A */
    public String f310A;

    /* JADX INFO: renamed from: B */
    public String f311B;

    /* JADX INFO: renamed from: C */
    public long f312C;

    /* JADX INFO: renamed from: D */
    public long f313D;

    /* JADX INFO: renamed from: E */
    public long f314E;

    /* JADX INFO: renamed from: F */
    public long f315F;

    /* JADX INFO: renamed from: G */
    public long f316G;

    /* JADX INFO: renamed from: H */
    public long f317H;

    /* JADX INFO: renamed from: I */
    public String f318I;

    /* JADX INFO: renamed from: J */
    public String f319J;

    /* JADX INFO: renamed from: K */
    public String f320K;

    /* JADX INFO: renamed from: L */
    public String f321L;

    /* JADX INFO: renamed from: M */
    public long f322M;

    /* JADX INFO: renamed from: N */
    public boolean f323N;

    /* JADX INFO: renamed from: O */
    public Map<String, String> f324O;

    /* JADX INFO: renamed from: P */
    public Map<String, String> f325P;

    /* JADX INFO: renamed from: Q */
    public int f326Q;

    /* JADX INFO: renamed from: R */
    public int f327R;

    /* JADX INFO: renamed from: S */
    public Map<String, String> f328S;

    /* JADX INFO: renamed from: T */
    public Map<String, String> f329T;

    /* JADX INFO: renamed from: U */
    public byte[] f330U;

    /* JADX INFO: renamed from: V */
    public String f331V;

    /* JADX INFO: renamed from: W */
    public String f332W;

    /* JADX INFO: renamed from: X */
    private String f333X;

    /* JADX INFO: renamed from: a */
    public long f334a;

    /* JADX INFO: renamed from: b */
    public int f335b;

    /* JADX INFO: renamed from: c */
    public String f336c;

    /* JADX INFO: renamed from: d */
    public boolean f337d;

    /* JADX INFO: renamed from: e */
    public String f338e;

    /* JADX INFO: renamed from: f */
    public String f339f;

    /* JADX INFO: renamed from: g */
    public String f340g;

    /* JADX INFO: renamed from: h */
    public Map<String, PlugInBean> f341h;

    /* JADX INFO: renamed from: i */
    public Map<String, PlugInBean> f342i;

    /* JADX INFO: renamed from: j */
    public boolean f343j;

    /* JADX INFO: renamed from: k */
    public boolean f344k;

    /* JADX INFO: renamed from: l */
    public int f345l;

    /* JADX INFO: renamed from: m */
    public String f346m;

    /* JADX INFO: renamed from: n */
    public String f347n;

    /* JADX INFO: renamed from: o */
    public String f348o;

    /* JADX INFO: renamed from: p */
    public String f349p;

    /* JADX INFO: renamed from: q */
    public String f350q;

    /* JADX INFO: renamed from: r */
    public long f351r;

    /* JADX INFO: renamed from: s */
    public String f352s;

    /* JADX INFO: renamed from: t */
    public int f353t;

    /* JADX INFO: renamed from: u */
    public String f354u;

    /* JADX INFO: renamed from: v */
    public String f355v;

    /* JADX INFO: renamed from: w */
    public String f356w;

    /* JADX INFO: renamed from: x */
    public String f357x;

    /* JADX INFO: renamed from: y */
    public byte[] f358y;

    /* JADX INFO: renamed from: z */
    public Map<String, String> f359z;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(CrashDetailBean crashDetailBean) {
        CrashDetailBean crashDetailBean2 = crashDetailBean;
        if (crashDetailBean2 == null) {
            return 1;
        }
        long j = this.f351r - crashDetailBean2.f351r;
        if (j <= 0) {
            return j < 0 ? -1 : 0;
        }
        return 1;
    }

    public CrashDetailBean() {
        this.f334a = -1L;
        this.f335b = 0;
        this.f336c = UUID.randomUUID().toString();
        this.f337d = false;
        this.f338e = "";
        this.f339f = "";
        this.f340g = "";
        this.f341h = null;
        this.f342i = null;
        this.f343j = false;
        this.f344k = false;
        this.f345l = 0;
        this.f346m = "";
        this.f347n = "";
        this.f348o = "";
        this.f349p = "";
        this.f350q = "";
        this.f351r = -1L;
        this.f352s = null;
        this.f353t = 0;
        this.f354u = "";
        this.f355v = "";
        this.f356w = null;
        this.f357x = null;
        this.f358y = null;
        this.f359z = null;
        this.f310A = "";
        this.f311B = "";
        this.f312C = -1L;
        this.f313D = -1L;
        this.f314E = -1L;
        this.f315F = -1L;
        this.f316G = -1L;
        this.f317H = -1L;
        this.f318I = "";
        this.f333X = "";
        this.f319J = "";
        this.f320K = "";
        this.f321L = "";
        this.f322M = -1L;
        this.f323N = false;
        this.f324O = null;
        this.f325P = null;
        this.f326Q = -1;
        this.f327R = -1;
        this.f328S = null;
        this.f329T = null;
        this.f330U = null;
        this.f331V = null;
        this.f332W = null;
    }

    public CrashDetailBean(Parcel parcel) {
        this.f334a = -1L;
        this.f335b = 0;
        this.f336c = UUID.randomUUID().toString();
        this.f337d = false;
        this.f338e = "";
        this.f339f = "";
        this.f340g = "";
        this.f341h = null;
        this.f342i = null;
        this.f343j = false;
        this.f344k = false;
        this.f345l = 0;
        this.f346m = "";
        this.f347n = "";
        this.f348o = "";
        this.f349p = "";
        this.f350q = "";
        this.f351r = -1L;
        this.f352s = null;
        this.f353t = 0;
        this.f354u = "";
        this.f355v = "";
        this.f356w = null;
        this.f357x = null;
        this.f358y = null;
        this.f359z = null;
        this.f310A = "";
        this.f311B = "";
        this.f312C = -1L;
        this.f313D = -1L;
        this.f314E = -1L;
        this.f315F = -1L;
        this.f316G = -1L;
        this.f317H = -1L;
        this.f318I = "";
        this.f333X = "";
        this.f319J = "";
        this.f320K = "";
        this.f321L = "";
        this.f322M = -1L;
        this.f323N = false;
        this.f324O = null;
        this.f325P = null;
        this.f326Q = -1;
        this.f327R = -1;
        this.f328S = null;
        this.f329T = null;
        this.f330U = null;
        this.f331V = null;
        this.f332W = null;
        this.f335b = parcel.readInt();
        this.f336c = parcel.readString();
        this.f337d = parcel.readByte() == 1;
        this.f338e = parcel.readString();
        this.f339f = parcel.readString();
        this.f340g = parcel.readString();
        this.f343j = parcel.readByte() == 1;
        this.f344k = parcel.readByte() == 1;
        this.f345l = parcel.readInt();
        this.f346m = parcel.readString();
        this.f347n = parcel.readString();
        this.f348o = parcel.readString();
        this.f349p = parcel.readString();
        this.f350q = parcel.readString();
        this.f351r = parcel.readLong();
        this.f352s = parcel.readString();
        this.f353t = parcel.readInt();
        this.f354u = parcel.readString();
        this.f355v = parcel.readString();
        this.f356w = parcel.readString();
        this.f359z = C0659z.m516b(parcel);
        this.f310A = parcel.readString();
        this.f311B = parcel.readString();
        this.f312C = parcel.readLong();
        this.f313D = parcel.readLong();
        this.f314E = parcel.readLong();
        this.f315F = parcel.readLong();
        this.f316G = parcel.readLong();
        this.f317H = parcel.readLong();
        this.f318I = parcel.readString();
        this.f333X = parcel.readString();
        this.f319J = parcel.readString();
        this.f320K = parcel.readString();
        this.f321L = parcel.readString();
        this.f322M = parcel.readLong();
        this.f323N = parcel.readByte() == 1;
        this.f324O = C0659z.m516b(parcel);
        this.f341h = C0659z.m503a(parcel);
        this.f342i = C0659z.m503a(parcel);
        this.f326Q = parcel.readInt();
        this.f327R = parcel.readInt();
        this.f328S = C0659z.m516b(parcel);
        this.f329T = C0659z.m516b(parcel);
        this.f330U = parcel.createByteArray();
        this.f358y = parcel.createByteArray();
        this.f331V = parcel.readString();
        this.f332W = parcel.readString();
        this.f357x = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f335b);
        parcel.writeString(this.f336c);
        parcel.writeByte(this.f337d ? (byte) 1 : (byte) 0);
        parcel.writeString(this.f338e);
        parcel.writeString(this.f339f);
        parcel.writeString(this.f340g);
        parcel.writeByte(this.f343j ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f344k ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.f345l);
        parcel.writeString(this.f346m);
        parcel.writeString(this.f347n);
        parcel.writeString(this.f348o);
        parcel.writeString(this.f349p);
        parcel.writeString(this.f350q);
        parcel.writeLong(this.f351r);
        parcel.writeString(this.f352s);
        parcel.writeInt(this.f353t);
        parcel.writeString(this.f354u);
        parcel.writeString(this.f355v);
        parcel.writeString(this.f356w);
        C0659z.m518b(parcel, this.f359z);
        parcel.writeString(this.f310A);
        parcel.writeString(this.f311B);
        parcel.writeLong(this.f312C);
        parcel.writeLong(this.f313D);
        parcel.writeLong(this.f314E);
        parcel.writeLong(this.f315F);
        parcel.writeLong(this.f316G);
        parcel.writeLong(this.f317H);
        parcel.writeString(this.f318I);
        parcel.writeString(this.f333X);
        parcel.writeString(this.f319J);
        parcel.writeString(this.f320K);
        parcel.writeString(this.f321L);
        parcel.writeLong(this.f322M);
        parcel.writeByte(this.f323N ? (byte) 1 : (byte) 0);
        C0659z.m518b(parcel, this.f324O);
        C0659z.m504a(parcel, this.f341h);
        C0659z.m504a(parcel, this.f342i);
        parcel.writeInt(this.f326Q);
        parcel.writeInt(this.f327R);
        C0659z.m518b(parcel, this.f328S);
        C0659z.m518b(parcel, this.f329T);
        parcel.writeByteArray(this.f330U);
        parcel.writeByteArray(this.f358y);
        parcel.writeString(this.f331V);
        parcel.writeString(this.f332W);
        parcel.writeString(this.f357x);
    }
}
