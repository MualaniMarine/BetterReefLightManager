package kotlin;

/* JADX INFO: loaded from: classes.dex */
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"kotlin/LazyKt__LazyJVMKt", "kotlin/LazyKt__LazyKt"}, m536k = 4, m537mv = {1, 1, 16}, m539xi = 1)
public final class LazyKt extends LazyKt__LazyKt {

    @Metadata(m533bv = {1, 0, 3}, m536k = 3, m537mv = {1, 1, 16})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LazyThreadSafetyMode.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[LazyThreadSafetyMode.SYNCHRONIZED.ordinal()] = 1;
            $EnumSwitchMapping$0[LazyThreadSafetyMode.PUBLICATION.ordinal()] = 2;
            $EnumSwitchMapping$0[LazyThreadSafetyMode.NONE.ordinal()] = 3;
        }
    }

    private LazyKt() {
    }
}
