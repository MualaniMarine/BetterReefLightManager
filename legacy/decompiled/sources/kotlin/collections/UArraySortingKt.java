package kotlin.collections;

import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UArraySorting.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0012\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\bH\u0001ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001f\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000bH\u0001ø\u0001\u0000¢\u0006\u0004\b \u0010!\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000eH\u0001ø\u0001\u0000¢\u0006\u0004\b\"\u0010#\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006$"}, m535d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "sortArray-GBYM_sE", "([B)V", "sortArray--ajY-9A", "([I)V", "sortArray-QwZRm1k", "([J)V", "sortArray-rL5Bavg", "([S)V", "kotlin-stdlib"}, m536k = 2, m537mv = {1, 1, 16})
public final class UArraySortingKt {
    /* JADX INFO: renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m889partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte bM636getimpl = UByteArray.m636getimpl(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM636getimpl = UByteArray.m636getimpl(bArr, i) & UByte.MAX_VALUE;
                i3 = bM636getimpl & UByte.MAX_VALUE;
                if (Intrinsics.compare(iM636getimpl, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m636getimpl(bArr, i2) & UByte.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte bM636getimpl2 = UByteArray.m636getimpl(bArr, i);
                UByteArray.m641setVurrAj0(bArr, i, UByteArray.m636getimpl(bArr, i2));
                UByteArray.m641setVurrAj0(bArr, i2, bM636getimpl2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m893quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int iM889partition4UcCI2c = m889partition4UcCI2c(bArr, i, i2);
        int i3 = iM889partition4UcCI2c - 1;
        if (i < i3) {
            m893quickSort4UcCI2c(bArr, i, i3);
        }
        if (iM889partition4UcCI2c < i2) {
            m893quickSort4UcCI2c(bArr, iM889partition4UcCI2c, i2);
        }
    }

    /* JADX INFO: renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m890partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short sM869getimpl = UShortArray.m869getimpl(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM869getimpl = UShortArray.m869getimpl(sArr, i) & UShort.MAX_VALUE;
                i3 = sM869getimpl & UShort.MAX_VALUE;
                if (Intrinsics.compare(iM869getimpl, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m869getimpl(sArr, i2) & UShort.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short sM869getimpl2 = UShortArray.m869getimpl(sArr, i);
                UShortArray.m874set01HTLdE(sArr, i, UShortArray.m869getimpl(sArr, i2));
                UShortArray.m874set01HTLdE(sArr, i2, sM869getimpl2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m894quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int iM890partitionAa5vz7o = m890partitionAa5vz7o(sArr, i, i2);
        int i3 = iM890partitionAa5vz7o - 1;
        if (i < i3) {
            m894quickSortAa5vz7o(sArr, i, i3);
        }
        if (iM890partitionAa5vz7o < i2) {
            m894quickSortAa5vz7o(sArr, iM890partitionAa5vz7o, i2);
        }
    }

    /* JADX INFO: renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m891partitionoBK06Vg(int[] iArr, int i, int i2) {
        int iM705getimpl = UIntArray.m705getimpl(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.uintCompare(UIntArray.m705getimpl(iArr, i), iM705getimpl) < 0) {
                i++;
            }
            while (UnsignedKt.uintCompare(UIntArray.m705getimpl(iArr, i2), iM705getimpl) > 0) {
                i2--;
            }
            if (i <= i2) {
                int iM705getimpl2 = UIntArray.m705getimpl(iArr, i);
                UIntArray.m710setVXSXFK8(iArr, i, UIntArray.m705getimpl(iArr, i2));
                UIntArray.m710setVXSXFK8(iArr, i2, iM705getimpl2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m895quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int iM891partitionoBK06Vg = m891partitionoBK06Vg(iArr, i, i2);
        int i3 = iM891partitionoBK06Vg - 1;
        if (i < i3) {
            m895quickSortoBK06Vg(iArr, i, i3);
        }
        if (iM891partitionoBK06Vg < i2) {
            m895quickSortoBK06Vg(iArr, iM891partitionoBK06Vg, i2);
        }
    }

    /* JADX INFO: renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m888partitionnroSd4(long[] jArr, int i, int i2) {
        long jM774getimpl = ULongArray.m774getimpl(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.ulongCompare(ULongArray.m774getimpl(jArr, i), jM774getimpl) < 0) {
                i++;
            }
            while (UnsignedKt.ulongCompare(ULongArray.m774getimpl(jArr, i2), jM774getimpl) > 0) {
                i2--;
            }
            if (i <= i2) {
                long jM774getimpl2 = ULongArray.m774getimpl(jArr, i);
                ULongArray.m779setk8EXiF4(jArr, i, ULongArray.m774getimpl(jArr, i2));
                ULongArray.m779setk8EXiF4(jArr, i2, jM774getimpl2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m892quickSortnroSd4(long[] jArr, int i, int i2) {
        int iM888partitionnroSd4 = m888partitionnroSd4(jArr, i, i2);
        int i3 = iM888partitionnroSd4 - 1;
        if (i < i3) {
            m892quickSortnroSd4(jArr, i, i3);
        }
        if (iM888partitionnroSd4 < i2) {
            m892quickSortnroSd4(jArr, iM888partitionnroSd4, i2);
        }
    }

    /* JADX INFO: renamed from: sortArray-GBYM_sE, reason: not valid java name */
    public static final void m897sortArrayGBYM_sE(byte[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m893quickSort4UcCI2c(array, 0, UByteArray.m637getSizeimpl(array) - 1);
    }

    /* JADX INFO: renamed from: sortArray-rL5Bavg, reason: not valid java name */
    public static final void m899sortArrayrL5Bavg(short[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m894quickSortAa5vz7o(array, 0, UShortArray.m870getSizeimpl(array) - 1);
    }

    /* JADX INFO: renamed from: sortArray--ajY-9A, reason: not valid java name */
    public static final void m896sortArrayajY9A(int[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m895quickSortoBK06Vg(array, 0, UIntArray.m706getSizeimpl(array) - 1);
    }

    /* JADX INFO: renamed from: sortArray-QwZRm1k, reason: not valid java name */
    public static final void m898sortArrayQwZRm1k(long[] array) {
        Intrinsics.checkParameterIsNotNull(array, "array");
        m892quickSortnroSd4(array, 0, ULongArray.m775getSizeimpl(array) - 1);
    }
}
