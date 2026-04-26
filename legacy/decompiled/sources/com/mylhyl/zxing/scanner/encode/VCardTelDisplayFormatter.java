package com.mylhyl.zxing.scanner.encode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
final class VCardTelDisplayFormatter implements Formatter {
    private final List<Map<String, Set<String>>> metadataForIndex;

    VCardTelDisplayFormatter() {
        this(null);
    }

    VCardTelDisplayFormatter(List<Map<String, Set<String>>> list) {
        this.metadataForIndex = list;
    }

    private static CharSequence formatMetadata(CharSequence charSequence, Map<String, Set<String>> map) {
        if (map == null || map.isEmpty()) {
            return charSequence;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, Set<String>>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Set<String> value = it.next().getValue();
            if (value != null && !value.isEmpty()) {
                Iterator<String> it2 = value.iterator();
                sb.append(it2.next());
                while (it2.hasNext()) {
                    sb.append(',');
                    sb.append(it2.next());
                }
            }
        }
        if (sb.length() > 0) {
            sb.append(' ');
        }
        sb.append(charSequence);
        return sb;
    }

    @Override // com.mylhyl.zxing.scanner.encode.Formatter
    public CharSequence format(CharSequence charSequence, int i) {
        String phone = ContactEncoder.formatPhone(charSequence.toString());
        List<Map<String, Set<String>>> list = this.metadataForIndex;
        return formatMetadata(phone, (list == null || list.size() <= i) ? null : this.metadataForIndex.get(i));
    }
}
