package kotlin.text;

import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Charsets.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b¨\u0006\u0004"}, m535d2 = {"charset", "Ljava/nio/charset/Charset;", "charsetName", "", "kotlin-stdlib"}, m536k = 2, m537mv = {1, 1, 16})
public final class CharsetsKt {
    private static final Charset charset(String str) {
        Charset charsetForName = Charset.forName(str);
        Intrinsics.checkExpressionValueIsNotNull(charsetForName, "Charset.forName(charsetName)");
        return charsetForName;
    }
}
