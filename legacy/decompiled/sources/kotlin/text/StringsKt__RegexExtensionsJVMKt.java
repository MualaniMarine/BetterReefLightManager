package kotlin.text;

import java.util.regex.Pattern;
import kotlin.Metadata;

/* JADX INFO: compiled from: RegexExtensionsJVM.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\b¨\u0006\u0003"}, m535d2 = {"toRegex", "Lkotlin/text/Regex;", "Ljava/util/regex/Pattern;", "kotlin-stdlib"}, m536k = 5, m537mv = {1, 1, 16}, m539xi = 1, m540xs = "kotlin/text/StringsKt")
class StringsKt__RegexExtensionsJVMKt extends StringsKt__IndentKt {
    private static final Regex toRegex(Pattern pattern) {
        return new Regex(pattern);
    }
}
