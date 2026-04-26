package com.mylhyl.zxing.scanner.encode;

import java.util.List;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
final class MECARDContactEncoder extends ContactEncoder {
    private static final char TERMINATOR = ';';

    MECARDContactEncoder() {
    }

    @Override // com.mylhyl.zxing.scanner.encode.ContactEncoder
    public String[] encode(List<String> list, String str, List<String> list2, List<String> list3, List<String> list4, List<String> list5, List<String> list6, String str2) {
        StringBuilder sb = new StringBuilder(100);
        sb.append("MECARD:");
        StringBuilder sb2 = new StringBuilder(100);
        MECARDFieldFormatter mECARDFieldFormatter = new MECARDFieldFormatter();
        appendUpToUnique(sb, sb2, "N", list, 1, new MECARDNameDisplayFormatter(), mECARDFieldFormatter, TERMINATOR);
        append(sb, sb2, "ORG", str, mECARDFieldFormatter, TERMINATOR);
        appendUpToUnique(sb, sb2, "ADR", list2, 1, null, mECARDFieldFormatter, TERMINATOR);
        appendUpToUnique(sb, sb2, "TEL", list3, Integer.MAX_VALUE, new MECARDTelDisplayFormatter(), mECARDFieldFormatter, TERMINATOR);
        appendUpToUnique(sb, sb2, "EMAIL", list5, Integer.MAX_VALUE, null, mECARDFieldFormatter, TERMINATOR);
        appendUpToUnique(sb, sb2, "URL", list6, Integer.MAX_VALUE, null, mECARDFieldFormatter, TERMINATOR);
        append(sb, sb2, "NOTE", str2, mECARDFieldFormatter, TERMINATOR);
        sb.append(TERMINATOR);
        return new String[]{sb.toString(), sb2.toString()};
    }

    private static final class MECARDFieldFormatter implements Formatter {
        private static final Pattern RESERVED_MECARD_CHARS = Pattern.compile("([\\\\:;])");
        private static final Pattern NEWLINE = Pattern.compile("\\n");

        private MECARDFieldFormatter() {
        }

        @Override // com.mylhyl.zxing.scanner.encode.Formatter
        public CharSequence format(CharSequence charSequence, int i) {
            return ':' + NEWLINE.matcher(RESERVED_MECARD_CHARS.matcher(charSequence).replaceAll("\\\\$1")).replaceAll("");
        }
    }

    private static final class MECARDTelDisplayFormatter implements Formatter {
        private static final Pattern NOT_DIGITS_OR_PLUS = Pattern.compile("[^0-9+]+");

        private MECARDTelDisplayFormatter() {
        }

        @Override // com.mylhyl.zxing.scanner.encode.Formatter
        public CharSequence format(CharSequence charSequence, int i) {
            return NOT_DIGITS_OR_PLUS.matcher(ContactEncoder.formatPhone(charSequence.toString())).replaceAll("");
        }
    }

    private static final class MECARDNameDisplayFormatter implements Formatter {
        private static final Pattern COMMA = Pattern.compile(",");

        private MECARDNameDisplayFormatter() {
        }

        @Override // com.mylhyl.zxing.scanner.encode.Formatter
        public CharSequence format(CharSequence charSequence, int i) {
            return COMMA.matcher(charSequence).replaceAll("");
        }
    }
}
