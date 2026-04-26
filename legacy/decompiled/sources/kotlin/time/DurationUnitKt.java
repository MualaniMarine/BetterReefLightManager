package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"kotlin/time/DurationUnitKt__DurationUnitJvmKt", "kotlin/time/DurationUnitKt__DurationUnitKt"}, m536k = 4, m537mv = {1, 1, 16}, m539xi = 1)
public final class DurationUnitKt extends DurationUnitKt__DurationUnitKt {

    @Metadata(m533bv = {1, 0, 3}, m536k = 3, m537mv = {1, 1, 16})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TimeUnit.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[TimeUnit.NANOSECONDS.ordinal()] = 1;
            $EnumSwitchMapping$0[TimeUnit.MICROSECONDS.ordinal()] = 2;
            $EnumSwitchMapping$0[TimeUnit.MILLISECONDS.ordinal()] = 3;
            $EnumSwitchMapping$0[TimeUnit.SECONDS.ordinal()] = 4;
            $EnumSwitchMapping$0[TimeUnit.MINUTES.ordinal()] = 5;
            $EnumSwitchMapping$0[TimeUnit.HOURS.ordinal()] = 6;
            $EnumSwitchMapping$0[TimeUnit.DAYS.ordinal()] = 7;
        }
    }

    private DurationUnitKt() {
    }
}
