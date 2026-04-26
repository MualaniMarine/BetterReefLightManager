package butterknife;

import butterknife.Unbinder;

/* JADX INFO: loaded from: classes.dex */
public interface Unbinder {
    public static final Unbinder EMPTY = new Unbinder() { // from class: butterknife.-$$Lambda$Unbinder$aAZZEjRDCcxQlrGZAdKWmLXqruY
        @Override // butterknife.Unbinder
        public final void unbind() {
            Unbinder.CC.lambda$static$0();
        }
    };

    /* JADX INFO: renamed from: butterknife.Unbinder$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static /* synthetic */ void lambda$static$0() {
        }
    }

    void unbind();
}
