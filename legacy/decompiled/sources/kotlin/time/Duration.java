package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: Duration.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b&\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0012\b\u0087@\u0018\u0000 s2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001sB\u0014\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\u0000H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b'\u0010(J\u001b\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0003H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010,J\u001b\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\tH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010-J\u001b\u0010)\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b.\u0010,J\u0013\u0010/\u001a\u0002002\b\u0010&\u001a\u0004\u0018\u000101HÖ\u0003J\t\u00102\u001a\u00020\tHÖ\u0001J\r\u00103\u001a\u000200¢\u0006\u0004\b4\u00105J\r\u00106\u001a\u000200¢\u0006\u0004\b7\u00105J\r\u00108\u001a\u000200¢\u0006\u0004\b9\u00105J\r\u0010:\u001a\u000200¢\u0006\u0004\b;\u00105J\u001b\u0010<\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b=\u0010,J\u001b\u0010>\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b?\u0010,J\u0017\u0010@\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¢\u0006\u0004\bA\u0010(J\u001b\u0010B\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0003H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bC\u0010,J\u001b\u0010B\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\tH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bC\u0010-J\u008d\u0001\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2u\u0010F\u001aq\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(J\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(K\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0GH\u0086\b¢\u0006\u0004\bO\u0010PJx\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2`\u0010F\u001a\\\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(K\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0QH\u0086\b¢\u0006\u0004\bO\u0010RJc\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2K\u0010F\u001aG\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0SH\u0086\b¢\u0006\u0004\bO\u0010TJN\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E26\u0010F\u001a2\u0012\u0013\u0012\u00110V¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0UH\u0086\b¢\u0006\u0004\bO\u0010WJ\u0019\u0010X\u001a\u00020\u00032\n\u0010Y\u001a\u00060Zj\u0002`[¢\u0006\u0004\b\\\u0010]J\u0019\u0010^\u001a\u00020\t2\n\u0010Y\u001a\u00060Zj\u0002`[¢\u0006\u0004\b_\u0010`J\r\u0010a\u001a\u00020b¢\u0006\u0004\bc\u0010dJ\u0019\u0010e\u001a\u00020V2\n\u0010Y\u001a\u00060Zj\u0002`[¢\u0006\u0004\bf\u0010gJ\r\u0010h\u001a\u00020V¢\u0006\u0004\bi\u0010jJ\r\u0010k\u001a\u00020V¢\u0006\u0004\bl\u0010jJ\u000f\u0010m\u001a\u00020bH\u0016¢\u0006\u0004\bn\u0010dJ#\u0010m\u001a\u00020b2\n\u0010Y\u001a\u00060Zj\u0002`[2\b\b\u0002\u0010o\u001a\u00020\t¢\u0006\u0004\bn\u0010pJ\u0013\u0010q\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\br\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00008Fø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u001a\u0010\b\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005R\u0011\u0010\u0010\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005R\u0011\u0010\u0012\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005R\u0011\u0010\u0014\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005R\u0011\u0010\u0016\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005R\u0011\u0010\u0018\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0005R\u0011\u0010\u001a\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005R\u001a\u0010\u001c\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001e\u0010\rR\u001a\u0010\u001f\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b \u0010\u000b\u001a\u0004\b!\u0010\rR\u001a\u0010\"\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b#\u0010\u000b\u001a\u0004\b$\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\u0002\n\u0000ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006t"}, m535d2 = {"Lkotlin/time/Duration;", "", "value", "", "constructor-impl", "(D)D", "absoluteValue", "getAbsoluteValue-impl", "hoursComponent", "", "hoursComponent$annotations", "()V", "getHoursComponent-impl", "(D)I", "inDays", "getInDays-impl", "inHours", "getInHours-impl", "inMicroseconds", "getInMicroseconds-impl", "inMilliseconds", "getInMilliseconds-impl", "inMinutes", "getInMinutes-impl", "inNanoseconds", "getInNanoseconds-impl", "inSeconds", "getInSeconds-impl", "minutesComponent", "minutesComponent$annotations", "getMinutesComponent-impl", "nanosecondsComponent", "nanosecondsComponent$annotations", "getNanosecondsComponent-impl", "secondsComponent", "secondsComponent$annotations", "getSecondsComponent-impl", "compareTo", "other", "compareTo-LRDsOJo", "(DD)I", "div", "scale", "div-impl", "(DD)D", "(DI)D", "div-LRDsOJo", "equals", "", "", "hashCode", "isFinite", "isFinite-impl", "(D)Z", "isInfinite", "isInfinite-impl", "isNegative", "isNegative-impl", "isPositive", "isPositive-impl", "minus", "minus-LRDsOJo", "plus", "plus-LRDsOJo", "precision", "precision-impl", "times", "times-impl", "toComponents", "T", "action", "Lkotlin/Function5;", "Lkotlin/ParameterName;", "name", "days", "hours", "minutes", "seconds", "nanoseconds", "toComponents-impl", "(DLkotlin/jvm/functions/Function5;)Ljava/lang/Object;", "Lkotlin/Function4;", "(DLkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "Lkotlin/Function3;", "(DLkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "Lkotlin/Function2;", "", "(DLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "toDouble", "unit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "toDouble-impl", "(DLjava/util/concurrent/TimeUnit;)D", "toInt", "toInt-impl", "(DLjava/util/concurrent/TimeUnit;)I", "toIsoString", "", "toIsoString-impl", "(D)Ljava/lang/String;", "toLong", "toLong-impl", "(DLjava/util/concurrent/TimeUnit;)J", "toLongMilliseconds", "toLongMilliseconds-impl", "(D)J", "toLongNanoseconds", "toLongNanoseconds-impl", "toString", "toString-impl", "decimals", "(DLjava/util/concurrent/TimeUnit;I)Ljava/lang/String;", "unaryMinus", "unaryMinus-impl", "Companion", "kotlin-stdlib"}, m536k = 1, m537mv = {1, 1, 16})
public final class Duration implements Comparable<Duration> {
    private final double value;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final double ZERO = m1522constructorimpl(0.0d);
    private static final double INFINITE = m1522constructorimpl(DoubleCompanionObject.INSTANCE.getPOSITIVE_INFINITY());

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Duration m1520boximpl(double d) {
        return new Duration(d);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static double m1522constructorimpl(double d) {
        return d;
    }

    /* JADX INFO: renamed from: div-LRDsOJo, reason: not valid java name */
    public static final double m1523divLRDsOJo(double d, double d2) {
        return d / d2;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1526equalsimpl(double d, Object obj) {
        return (obj instanceof Duration) && Double.compare(d, ((Duration) obj).getValue()) == 0;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1527equalsimpl0(double d, double d2) {
        return Double.compare(d, d2) == 0;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1540hashCodeimpl(double d) {
        long jDoubleToLongBits = Double.doubleToLongBits(d);
        return (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
    }

    public static /* synthetic */ void hoursComponent$annotations() {
    }

    /* JADX INFO: renamed from: isNegative-impl, reason: not valid java name */
    public static final boolean m1543isNegativeimpl(double d) {
        return d < ((double) 0);
    }

    /* JADX INFO: renamed from: isPositive-impl, reason: not valid java name */
    public static final boolean m1544isPositiveimpl(double d) {
        return d > ((double) 0);
    }

    public static /* synthetic */ void minutesComponent$annotations() {
    }

    public static /* synthetic */ void nanosecondsComponent$annotations() {
    }

    /* JADX INFO: renamed from: precision-impl, reason: not valid java name */
    private static final int m1547precisionimpl(double d, double d2) {
        if (d2 < 1) {
            return 3;
        }
        if (d2 < 10) {
            return 2;
        }
        return d2 < ((double) 100) ? 1 : 0;
    }

    public static /* synthetic */ void secondsComponent$annotations() {
    }

    /* JADX INFO: renamed from: compareTo-LRDsOJo, reason: not valid java name */
    public int m1564compareToLRDsOJo(double d) {
        return m1521compareToLRDsOJo(this.value, d);
    }

    public boolean equals(Object other) {
        return m1526equalsimpl(this.value, other);
    }

    public int hashCode() {
        return m1540hashCodeimpl(this.value);
    }

    public String toString() {
        return m1560toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ double getValue() {
        return this.value;
    }

    private /* synthetic */ Duration(double d) {
        this.value = d;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Duration duration) {
        return m1564compareToLRDsOJo(duration.getValue());
    }

    /* JADX INFO: compiled from: Duration.kt */
    @Metadata(m533bv = {1, 0, 3}, m534d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\n\u0010\u0010\u001a\u00060\u000ej\u0002`\u000fR\u0016\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\b\u001a\u00020\u0004ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\t\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"}, m535d2 = {"Lkotlin/time/Duration$Companion;", "", "()V", "INFINITE", "Lkotlin/time/Duration;", "getINFINITE", "()D", "D", "ZERO", "getZERO", "convert", "", "value", "sourceUnit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "targetUnit", "kotlin-stdlib"}, m536k = 1, m537mv = {1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final double getZERO() {
            return Duration.ZERO;
        }

        public final double getINFINITE() {
            return Duration.INFINITE;
        }

        public final double convert(double value, TimeUnit sourceUnit, TimeUnit targetUnit) {
            Intrinsics.checkParameterIsNotNull(sourceUnit, "sourceUnit");
            Intrinsics.checkParameterIsNotNull(targetUnit, "targetUnit");
            return DurationUnitKt.convertDurationUnit(value, sourceUnit, targetUnit);
        }
    }

    /* JADX INFO: renamed from: unaryMinus-impl, reason: not valid java name */
    public static final double m1563unaryMinusimpl(double d) {
        return m1522constructorimpl(-d);
    }

    /* JADX INFO: renamed from: plus-LRDsOJo, reason: not valid java name */
    public static final double m1546plusLRDsOJo(double d, double d2) {
        return m1522constructorimpl(d + d2);
    }

    /* JADX INFO: renamed from: minus-LRDsOJo, reason: not valid java name */
    public static final double m1545minusLRDsOJo(double d, double d2) {
        return m1522constructorimpl(d - d2);
    }

    /* JADX INFO: renamed from: times-impl, reason: not valid java name */
    public static final double m1549timesimpl(double d, int i) {
        return m1522constructorimpl(d * ((double) i));
    }

    /* JADX INFO: renamed from: times-impl, reason: not valid java name */
    public static final double m1548timesimpl(double d, double d2) {
        return m1522constructorimpl(d * d2);
    }

    /* JADX INFO: renamed from: div-impl, reason: not valid java name */
    public static final double m1525divimpl(double d, int i) {
        return m1522constructorimpl(d / ((double) i));
    }

    /* JADX INFO: renamed from: div-impl, reason: not valid java name */
    public static final double m1524divimpl(double d, double d2) {
        return m1522constructorimpl(d / d2);
    }

    /* JADX INFO: renamed from: isInfinite-impl, reason: not valid java name */
    public static final boolean m1542isInfiniteimpl(double d) {
        return Double.isInfinite(d);
    }

    /* JADX INFO: renamed from: isFinite-impl, reason: not valid java name */
    public static final boolean m1541isFiniteimpl(double d) {
        return (Double.isInfinite(d) || Double.isNaN(d)) ? false : true;
    }

    /* JADX INFO: renamed from: getAbsoluteValue-impl, reason: not valid java name */
    public static final double m1528getAbsoluteValueimpl(double d) {
        return m1543isNegativeimpl(d) ? m1563unaryMinusimpl(d) : d;
    }

    /* JADX INFO: renamed from: compareTo-LRDsOJo, reason: not valid java name */
    public static int m1521compareToLRDsOJo(double d, double d2) {
        return Double.compare(d, d2);
    }

    /* JADX INFO: renamed from: toComponents-impl, reason: not valid java name */
    public static final <T> T m1553toComponentsimpl(double d, Function5<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        return action.invoke(Integer.valueOf((int) m1530getInDaysimpl(d)), Integer.valueOf(m1529getHoursComponentimpl(d)), Integer.valueOf(m1537getMinutesComponentimpl(d)), Integer.valueOf(m1539getSecondsComponentimpl(d)), Integer.valueOf(m1538getNanosecondsComponentimpl(d)));
    }

    /* JADX INFO: renamed from: toComponents-impl, reason: not valid java name */
    public static final <T> T m1552toComponentsimpl(double d, Function4<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        return action.invoke(Integer.valueOf((int) m1531getInHoursimpl(d)), Integer.valueOf(m1537getMinutesComponentimpl(d)), Integer.valueOf(m1539getSecondsComponentimpl(d)), Integer.valueOf(m1538getNanosecondsComponentimpl(d)));
    }

    /* JADX INFO: renamed from: toComponents-impl, reason: not valid java name */
    public static final <T> T m1551toComponentsimpl(double d, Function3<? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        return action.invoke(Integer.valueOf((int) m1534getInMinutesimpl(d)), Integer.valueOf(m1539getSecondsComponentimpl(d)), Integer.valueOf(m1538getNanosecondsComponentimpl(d)));
    }

    /* JADX INFO: renamed from: toComponents-impl, reason: not valid java name */
    public static final <T> T m1550toComponentsimpl(double d, Function2<? super Long, ? super Integer, ? extends T> action) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        return action.invoke(Long.valueOf((long) m1536getInSecondsimpl(d)), Integer.valueOf(m1538getNanosecondsComponentimpl(d)));
    }

    /* JADX INFO: renamed from: getHoursComponent-impl, reason: not valid java name */
    public static final int m1529getHoursComponentimpl(double d) {
        return (int) (m1531getInHoursimpl(d) % ((double) 24));
    }

    /* JADX INFO: renamed from: getMinutesComponent-impl, reason: not valid java name */
    public static final int m1537getMinutesComponentimpl(double d) {
        return (int) (m1534getInMinutesimpl(d) % ((double) 60));
    }

    /* JADX INFO: renamed from: getSecondsComponent-impl, reason: not valid java name */
    public static final int m1539getSecondsComponentimpl(double d) {
        return (int) (m1536getInSecondsimpl(d) % ((double) 60));
    }

    /* JADX INFO: renamed from: getNanosecondsComponent-impl, reason: not valid java name */
    public static final int m1538getNanosecondsComponentimpl(double d) {
        return (int) (m1535getInNanosecondsimpl(d) % 1.0E9d);
    }

    /* JADX INFO: renamed from: toDouble-impl, reason: not valid java name */
    public static final double m1554toDoubleimpl(double d, TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        return DurationUnitKt.convertDurationUnit(d, DurationKt.getStorageUnit(), unit);
    }

    /* JADX INFO: renamed from: toLong-impl, reason: not valid java name */
    public static final long m1557toLongimpl(double d, TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        return (long) m1554toDoubleimpl(d, unit);
    }

    /* JADX INFO: renamed from: toInt-impl, reason: not valid java name */
    public static final int m1555toIntimpl(double d, TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        return (int) m1554toDoubleimpl(d, unit);
    }

    /* JADX INFO: renamed from: getInDays-impl, reason: not valid java name */
    public static final double m1530getInDaysimpl(double d) {
        return m1554toDoubleimpl(d, TimeUnit.DAYS);
    }

    /* JADX INFO: renamed from: getInHours-impl, reason: not valid java name */
    public static final double m1531getInHoursimpl(double d) {
        return m1554toDoubleimpl(d, TimeUnit.HOURS);
    }

    /* JADX INFO: renamed from: getInMinutes-impl, reason: not valid java name */
    public static final double m1534getInMinutesimpl(double d) {
        return m1554toDoubleimpl(d, TimeUnit.MINUTES);
    }

    /* JADX INFO: renamed from: getInSeconds-impl, reason: not valid java name */
    public static final double m1536getInSecondsimpl(double d) {
        return m1554toDoubleimpl(d, TimeUnit.SECONDS);
    }

    /* JADX INFO: renamed from: getInMilliseconds-impl, reason: not valid java name */
    public static final double m1533getInMillisecondsimpl(double d) {
        return m1554toDoubleimpl(d, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: renamed from: getInMicroseconds-impl, reason: not valid java name */
    public static final double m1532getInMicrosecondsimpl(double d) {
        return m1554toDoubleimpl(d, TimeUnit.MICROSECONDS);
    }

    /* JADX INFO: renamed from: getInNanoseconds-impl, reason: not valid java name */
    public static final double m1535getInNanosecondsimpl(double d) {
        return m1554toDoubleimpl(d, TimeUnit.NANOSECONDS);
    }

    /* JADX INFO: renamed from: toLongNanoseconds-impl, reason: not valid java name */
    public static final long m1559toLongNanosecondsimpl(double d) {
        return m1557toLongimpl(d, TimeUnit.NANOSECONDS);
    }

    /* JADX INFO: renamed from: toLongMilliseconds-impl, reason: not valid java name */
    public static final long m1558toLongMillisecondsimpl(double d) {
        return m1557toLongimpl(d, TimeUnit.MILLISECONDS);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a0  */
    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String m1560toStringimpl(double r8) {
        /*
            boolean r0 = m1542isInfiniteimpl(r8)
            if (r0 == 0) goto Lc
            java.lang.String r8 = java.lang.String.valueOf(r8)
            goto Lc1
        Lc:
            r0 = 0
            int r0 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r0 != 0) goto L16
            java.lang.String r8 = "0s"
            goto Lc1
        L16:
            double r0 = m1528getAbsoluteValueimpl(r8)
            double r0 = m1535getInNanosecondsimpl(r0)
            r2 = 4517329193108106637(0x3eb0c6f7a0b5ed8d, double:1.0E-6)
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r3 = 0
            r4 = 1
            if (r2 >= 0) goto L2f
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS
        L2b:
            r1 = r3
            r3 = r4
            goto L90
        L2f:
            double r5 = (double) r4
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 >= 0) goto L38
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.NANOSECONDS
            r1 = 7
            goto L90
        L38:
            r5 = 4652007308841189376(0x408f400000000000, double:1000.0)
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 >= 0) goto L45
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.NANOSECONDS
        L43:
            r1 = r3
            goto L90
        L45:
            r5 = 4696837146684686336(0x412e848000000000, double:1000000.0)
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 >= 0) goto L51
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.MICROSECONDS
            goto L43
        L51:
            r5 = 4741671816366391296(0x41cdcd6500000000, double:1.0E9)
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 >= 0) goto L5d
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.MILLISECONDS
            goto L43
        L5d:
            r5 = 4786511204640096256(0x426d1a94a2000000, double:1.0E12)
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 >= 0) goto L69
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS
            goto L43
        L69:
            r5 = 4813020802404319232(0x42cb48eb57e00000, double:6.0E13)
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 >= 0) goto L75
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.MINUTES
            goto L43
        L75:
            r5 = 4839562400168542208(0x4329945ca2620000, double:3.6E15)
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 >= 0) goto L81
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.HOURS
            goto L43
        L81:
            r5 = 4920018990336211136(0x44476b344f2a78c0, double:8.64E20)
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 >= 0) goto L8d
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.DAYS
            goto L43
        L8d:
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.DAYS
            goto L2b
        L90:
            double r4 = m1554toDoubleimpl(r8, r0)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            if (r3 == 0) goto La0
            java.lang.String r8 = kotlin.time.FormatToDecimalsKt.formatScientific(r4)
            goto Lb3
        La0:
            if (r1 <= 0) goto La7
            java.lang.String r8 = kotlin.time.FormatToDecimalsKt.formatUpToDecimals(r4, r1)
            goto Lb3
        La7:
            double r6 = java.lang.Math.abs(r4)
            int r8 = m1547precisionimpl(r8, r6)
            java.lang.String r8 = kotlin.time.FormatToDecimalsKt.formatToExactDecimals(r4, r8)
        Lb3:
            r2.append(r8)
            java.lang.String r8 = kotlin.time.DurationUnitKt.shortName(r0)
            r2.append(r8)
            java.lang.String r8 = r2.toString()
        Lc1:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.time.Duration.m1560toStringimpl(double):java.lang.String");
    }

    /* JADX INFO: renamed from: toString-impl$default, reason: not valid java name */
    public static /* synthetic */ String m1562toStringimpl$default(double d, TimeUnit timeUnit, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return m1561toStringimpl(d, timeUnit, i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static final String m1561toStringimpl(double d, TimeUnit unit, int i) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("decimals must be not negative, but was " + i).toString());
        }
        if (m1542isInfiniteimpl(d)) {
            return String.valueOf(d);
        }
        double dM1554toDoubleimpl = m1554toDoubleimpl(d, unit);
        StringBuilder sb = new StringBuilder();
        sb.append(Math.abs(dM1554toDoubleimpl) < 1.0E14d ? FormatToDecimalsKt.formatToExactDecimals(dM1554toDoubleimpl, RangesKt.coerceAtMost(i, 12)) : FormatToDecimalsKt.formatScientific(dM1554toDoubleimpl));
        sb.append(DurationUnitKt.shortName(unit));
        return sb.toString();
    }

    /* JADX INFO: renamed from: toIsoString-impl, reason: not valid java name */
    public static final String m1556toIsoStringimpl(double d) {
        StringBuilder sb = new StringBuilder();
        if (m1543isNegativeimpl(d)) {
            sb.append('-');
        }
        sb.append("PT");
        double dM1528getAbsoluteValueimpl = m1528getAbsoluteValueimpl(d);
        int iM1531getInHoursimpl = (int) m1531getInHoursimpl(dM1528getAbsoluteValueimpl);
        int iM1537getMinutesComponentimpl = m1537getMinutesComponentimpl(dM1528getAbsoluteValueimpl);
        int iM1539getSecondsComponentimpl = m1539getSecondsComponentimpl(dM1528getAbsoluteValueimpl);
        int iM1538getNanosecondsComponentimpl = m1538getNanosecondsComponentimpl(dM1528getAbsoluteValueimpl);
        boolean z = true;
        boolean z2 = iM1531getInHoursimpl != 0;
        boolean z3 = (iM1539getSecondsComponentimpl == 0 && iM1538getNanosecondsComponentimpl == 0) ? false : true;
        if (iM1537getMinutesComponentimpl == 0 && (!z3 || !z2)) {
            z = false;
        }
        if (z2) {
            sb.append(iM1531getInHoursimpl);
            sb.append('H');
        }
        if (z) {
            sb.append(iM1537getMinutesComponentimpl);
            sb.append('M');
        }
        if (z3 || (!z2 && !z)) {
            sb.append(iM1539getSecondsComponentimpl);
            if (iM1538getNanosecondsComponentimpl != 0) {
                sb.append('.');
                String strPadStart = StringsKt.padStart(String.valueOf(iM1538getNanosecondsComponentimpl), 9, '0');
                if (iM1538getNanosecondsComponentimpl % 1000000 == 0) {
                    sb.append((CharSequence) strPadStart, 0, 3);
                    Intrinsics.checkExpressionValueIsNotNull(sb, "this.append(value, startIndex, endIndex)");
                } else if (iM1538getNanosecondsComponentimpl % 1000 == 0) {
                    sb.append((CharSequence) strPadStart, 0, 6);
                    Intrinsics.checkExpressionValueIsNotNull(sb, "this.append(value, startIndex, endIndex)");
                } else {
                    sb.append(strPadStart);
                }
            }
            sb.append('S');
        }
        String string = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }
}
