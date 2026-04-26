package kotlin.collections;

import java.util.Arrays;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;

/* JADX INFO: compiled from: UArraysKt.kt */
/* JADX INFO: loaded from: classes.dex */
@Deprecated(level = DeprecationLevel.HIDDEN, message = "Provided for binary compatibility")
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\t\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\f2\u0006\u0010\u0006\u001a\u00020\fH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u000fH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\tH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\u0017J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0019J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\tH\u0007ø\u0001\u0000¢\u0006\u0004\b \u0010!J\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b\"\u0010#J\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b$\u0010%J\u001e\u0010&\u001a\u00020'*\u00020\u00052\u0006\u0010&\u001a\u00020(H\u0007ø\u0001\u0000¢\u0006\u0004\b)\u0010*J\u001e\u0010&\u001a\u00020+*\u00020\t2\u0006\u0010&\u001a\u00020(H\u0007ø\u0001\u0000¢\u0006\u0004\b,\u0010-J\u001e\u0010&\u001a\u00020.*\u00020\f2\u0006\u0010&\u001a\u00020(H\u0007ø\u0001\u0000¢\u0006\u0004\b/\u00100J\u001e\u0010&\u001a\u000201*\u00020\u000f2\u0006\u0010&\u001a\u00020(H\u0007ø\u0001\u0000¢\u0006\u0004\b2\u00103J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020'05*\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b6\u00107J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020+05*\u00020\tH\u0007ø\u0001\u0000¢\u0006\u0004\b8\u00109J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020.05*\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b:\u0010;J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020105*\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b<\u0010=\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006>"}, m535d2 = {"Lkotlin/collections/UArraysKt;", "", "()V", "contentEquals", "", "Lkotlin/UByteArray;", "other", "contentEquals-kdPth3s", "([B[B)Z", "Lkotlin/UIntArray;", "contentEquals-ctEhBpI", "([I[I)Z", "Lkotlin/ULongArray;", "contentEquals-us8wMrg", "([J[J)Z", "Lkotlin/UShortArray;", "contentEquals-mazbYpA", "([S[S)Z", "contentHashCode", "", "contentHashCode-GBYM_sE", "([B)I", "contentHashCode--ajY-9A", "([I)I", "contentHashCode-QwZRm1k", "([J)I", "contentHashCode-rL5Bavg", "([S)I", "contentToString", "", "contentToString-GBYM_sE", "([B)Ljava/lang/String;", "contentToString--ajY-9A", "([I)Ljava/lang/String;", "contentToString-QwZRm1k", "([J)Ljava/lang/String;", "contentToString-rL5Bavg", "([S)Ljava/lang/String;", "random", "Lkotlin/UByte;", "Lkotlin/random/Random;", "random-oSF2wD8", "([BLkotlin/random/Random;)B", "Lkotlin/UInt;", "random-2D5oskM", "([ILkotlin/random/Random;)I", "Lkotlin/ULong;", "random-JzugnMA", "([JLkotlin/random/Random;)J", "Lkotlin/UShort;", "random-s5X_as8", "([SLkotlin/random/Random;)S", "toTypedArray", "", "toTypedArray-GBYM_sE", "([B)[Lkotlin/UByte;", "toTypedArray--ajY-9A", "([I)[Lkotlin/UInt;", "toTypedArray-QwZRm1k", "([J)[Lkotlin/ULong;", "toTypedArray-rL5Bavg", "([S)[Lkotlin/UShort;", "kotlin-stdlib"}, m536k = 1, m537mv = {1, 1, 16})
public final class UArraysKt {
    public static final UArraysKt INSTANCE = new UArraysKt();

    private UArraysKt() {
    }

    @JvmStatic
    /* JADX INFO: renamed from: random-2D5oskM, reason: not valid java name */
    public static final int m912random2D5oskM(int[] random, Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (UIntArray.m708isEmptyimpl(random)) {
            throw new NoSuchElementException("Array is empty.");
        }
        return UIntArray.m705getimpl(random, random2.nextInt(UIntArray.m706getSizeimpl(random)));
    }

    @JvmStatic
    /* JADX INFO: renamed from: random-JzugnMA, reason: not valid java name */
    public static final long m913randomJzugnMA(long[] random, Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (ULongArray.m777isEmptyimpl(random)) {
            throw new NoSuchElementException("Array is empty.");
        }
        return ULongArray.m774getimpl(random, random2.nextInt(ULongArray.m775getSizeimpl(random)));
    }

    @JvmStatic
    /* JADX INFO: renamed from: random-oSF2wD8, reason: not valid java name */
    public static final byte m914randomoSF2wD8(byte[] random, Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (UByteArray.m639isEmptyimpl(random)) {
            throw new NoSuchElementException("Array is empty.");
        }
        return UByteArray.m636getimpl(random, random2.nextInt(UByteArray.m637getSizeimpl(random)));
    }

    @JvmStatic
    /* JADX INFO: renamed from: random-s5X_as8, reason: not valid java name */
    public static final short m915randoms5X_as8(short[] random, Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (UShortArray.m872isEmptyimpl(random)) {
            throw new NoSuchElementException("Array is empty.");
        }
        return UShortArray.m869getimpl(random, random2.nextInt(UShortArray.m870getSizeimpl(random)));
    }

    @JvmStatic
    /* JADX INFO: renamed from: contentEquals-ctEhBpI, reason: not valid java name */
    public static final boolean m900contentEqualsctEhBpI(int[] contentEquals, int[] other) {
        Intrinsics.checkParameterIsNotNull(contentEquals, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(contentEquals, other);
    }

    @JvmStatic
    /* JADX INFO: renamed from: contentEquals-us8wMrg, reason: not valid java name */
    public static final boolean m903contentEqualsus8wMrg(long[] contentEquals, long[] other) {
        Intrinsics.checkParameterIsNotNull(contentEquals, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(contentEquals, other);
    }

    @JvmStatic
    /* JADX INFO: renamed from: contentEquals-kdPth3s, reason: not valid java name */
    public static final boolean m901contentEqualskdPth3s(byte[] contentEquals, byte[] other) {
        Intrinsics.checkParameterIsNotNull(contentEquals, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(contentEquals, other);
    }

    @JvmStatic
    /* JADX INFO: renamed from: contentEquals-mazbYpA, reason: not valid java name */
    public static final boolean m902contentEqualsmazbYpA(short[] contentEquals, short[] other) {
        Intrinsics.checkParameterIsNotNull(contentEquals, "$this$contentEquals");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(contentEquals, other);
    }

    @JvmStatic
    /* JADX INFO: renamed from: contentHashCode--ajY-9A, reason: not valid java name */
    public static final int m904contentHashCodeajY9A(int[] contentHashCode) {
        Intrinsics.checkParameterIsNotNull(contentHashCode, "$this$contentHashCode");
        return Arrays.hashCode(contentHashCode);
    }

    @JvmStatic
    /* JADX INFO: renamed from: contentHashCode-QwZRm1k, reason: not valid java name */
    public static final int m906contentHashCodeQwZRm1k(long[] contentHashCode) {
        Intrinsics.checkParameterIsNotNull(contentHashCode, "$this$contentHashCode");
        return Arrays.hashCode(contentHashCode);
    }

    @JvmStatic
    /* JADX INFO: renamed from: contentHashCode-GBYM_sE, reason: not valid java name */
    public static final int m905contentHashCodeGBYM_sE(byte[] contentHashCode) {
        Intrinsics.checkParameterIsNotNull(contentHashCode, "$this$contentHashCode");
        return Arrays.hashCode(contentHashCode);
    }

    @JvmStatic
    /* JADX INFO: renamed from: contentHashCode-rL5Bavg, reason: not valid java name */
    public static final int m907contentHashCoderL5Bavg(short[] contentHashCode) {
        Intrinsics.checkParameterIsNotNull(contentHashCode, "$this$contentHashCode");
        return Arrays.hashCode(contentHashCode);
    }

    @JvmStatic
    /* JADX INFO: renamed from: contentToString--ajY-9A, reason: not valid java name */
    public static final String m908contentToStringajY9A(int[] contentToString) {
        Intrinsics.checkParameterIsNotNull(contentToString, "$this$contentToString");
        return CollectionsKt.joinToString$default(UIntArray.m698boximpl(contentToString), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    /* JADX INFO: renamed from: contentToString-QwZRm1k, reason: not valid java name */
    public static final String m910contentToStringQwZRm1k(long[] contentToString) {
        Intrinsics.checkParameterIsNotNull(contentToString, "$this$contentToString");
        return CollectionsKt.joinToString$default(ULongArray.m767boximpl(contentToString), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    /* JADX INFO: renamed from: contentToString-GBYM_sE, reason: not valid java name */
    public static final String m909contentToStringGBYM_sE(byte[] contentToString) {
        Intrinsics.checkParameterIsNotNull(contentToString, "$this$contentToString");
        return CollectionsKt.joinToString$default(UByteArray.m629boximpl(contentToString), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    /* JADX INFO: renamed from: contentToString-rL5Bavg, reason: not valid java name */
    public static final String m911contentToStringrL5Bavg(short[] contentToString) {
        Intrinsics.checkParameterIsNotNull(contentToString, "$this$contentToString");
        return CollectionsKt.joinToString$default(UShortArray.m862boximpl(contentToString), ", ", "[", "]", 0, null, null, 56, null);
    }

    @JvmStatic
    /* JADX INFO: renamed from: toTypedArray--ajY-9A, reason: not valid java name */
    public static final UInt[] m916toTypedArrayajY9A(int[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        int iM706getSizeimpl = UIntArray.m706getSizeimpl(toTypedArray);
        UInt[] uIntArr = new UInt[iM706getSizeimpl];
        for (int i = 0; i < iM706getSizeimpl; i++) {
            uIntArr[i] = UInt.m648boximpl(UIntArray.m705getimpl(toTypedArray, i));
        }
        return uIntArr;
    }

    @JvmStatic
    /* JADX INFO: renamed from: toTypedArray-QwZRm1k, reason: not valid java name */
    public static final ULong[] m918toTypedArrayQwZRm1k(long[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        int iM775getSizeimpl = ULongArray.m775getSizeimpl(toTypedArray);
        ULong[] uLongArr = new ULong[iM775getSizeimpl];
        for (int i = 0; i < iM775getSizeimpl; i++) {
            uLongArr[i] = ULong.m717boximpl(ULongArray.m774getimpl(toTypedArray, i));
        }
        return uLongArr;
    }

    @JvmStatic
    /* JADX INFO: renamed from: toTypedArray-GBYM_sE, reason: not valid java name */
    public static final UByte[] m917toTypedArrayGBYM_sE(byte[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        int iM637getSizeimpl = UByteArray.m637getSizeimpl(toTypedArray);
        UByte[] uByteArr = new UByte[iM637getSizeimpl];
        for (int i = 0; i < iM637getSizeimpl; i++) {
            uByteArr[i] = UByte.m581boximpl(UByteArray.m636getimpl(toTypedArray, i));
        }
        return uByteArr;
    }

    @JvmStatic
    /* JADX INFO: renamed from: toTypedArray-rL5Bavg, reason: not valid java name */
    public static final UShort[] m919toTypedArrayrL5Bavg(short[] toTypedArray) {
        Intrinsics.checkParameterIsNotNull(toTypedArray, "$this$toTypedArray");
        int iM870getSizeimpl = UShortArray.m870getSizeimpl(toTypedArray);
        UShort[] uShortArr = new UShort[iM870getSizeimpl];
        for (int i = 0; i < iM870getSizeimpl; i++) {
            uShortArr[i] = UShort.m814boximpl(UShortArray.m869getimpl(toTypedArray, i));
        }
        return uShortArr;
    }
}
