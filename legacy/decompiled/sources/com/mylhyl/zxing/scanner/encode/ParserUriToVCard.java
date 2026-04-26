package com.mylhyl.zxing.scanner.encode;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.core.app.NotificationCompat;

/* JADX INFO: loaded from: classes.dex */
public class ParserUriToVCard {
    public static final String NOTE_KEY = "NOTE_KEY";
    public static final String URL_KEY = "URL_KEY";
    public static final String[] PHONE_KEYS = {"phone", "secondary_phone", "tertiary_phone"};
    public static final String[] PHONE_TYPE_KEYS = {"phone_type", "secondary_phone_type", "tertiary_phone_type"};
    public static final String[] EMAIL_KEYS = {NotificationCompat.CATEGORY_EMAIL, "secondary_email", "tertiary_email"};

    public Bundle parserUri(Context context, Uri uri) {
        String string;
        if (context != null && uri != null) {
            ContentResolver contentResolver = context.getContentResolver();
            try {
                Cursor cursorQuery = contentResolver.query(uri, null, null, null, null);
                if (cursorQuery == null) {
                    return null;
                }
                try {
                    if (!cursorQuery.moveToFirst()) {
                        return null;
                    }
                    String string2 = cursorQuery.getString(cursorQuery.getColumnIndex("_id"));
                    String string3 = cursorQuery.getString(cursorQuery.getColumnIndex("display_name"));
                    boolean z = cursorQuery.getInt(cursorQuery.getColumnIndex("has_phone_number")) > 0;
                    cursorQuery.close();
                    Bundle bundle = new Bundle();
                    if (string3 != null && !string3.isEmpty()) {
                        bundle.putString("name", massageContactData(string3));
                    }
                    if (z) {
                        cursorQuery = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "contact_id=" + string2, null, null);
                        if (cursorQuery != null) {
                            try {
                                int columnIndex = cursorQuery.getColumnIndex("data1");
                                int columnIndex2 = cursorQuery.getColumnIndex("data2");
                                for (int i = 0; cursorQuery.moveToNext() && i < PHONE_KEYS.length; i++) {
                                    String string4 = cursorQuery.getString(columnIndex);
                                    if (string4 != null && !string4.isEmpty()) {
                                        bundle.putString(PHONE_KEYS[i], massageContactData(string4));
                                    }
                                    bundle.putInt(PHONE_TYPE_KEYS[i], cursorQuery.getInt(columnIndex2));
                                }
                                cursorQuery.close();
                            } finally {
                            }
                        }
                    }
                    cursorQuery = contentResolver.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, "contact_id=" + string2, null, null);
                    if (cursorQuery != null) {
                        try {
                            if (cursorQuery.moveToNext() && (string = cursorQuery.getString(cursorQuery.getColumnIndex("data1"))) != null && !string.isEmpty()) {
                                bundle.putString("postal", massageContactData(string));
                            }
                        } finally {
                        }
                    }
                    cursorQuery = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, "contact_id=" + string2, null, null);
                    if (cursorQuery != null) {
                        try {
                            int columnIndex3 = cursorQuery.getColumnIndex("data1");
                            for (int i2 = 0; cursorQuery.moveToNext() && i2 < EMAIL_KEYS.length; i2++) {
                                String string5 = cursorQuery.getString(columnIndex3);
                                if (string5 != null && !string5.isEmpty()) {
                                    bundle.putString(EMAIL_KEYS[i2], massageContactData(string5));
                                }
                            }
                        } finally {
                        }
                    }
                    if (bundle.isEmpty()) {
                        return null;
                    }
                    return bundle;
                } finally {
                }
            } catch (IllegalArgumentException unused) {
            }
        }
        return null;
    }

    private static String massageContactData(String str) {
        if (str.indexOf(10) >= 0) {
            str = str.replace("\n", " ");
        }
        return str.indexOf(13) >= 0 ? str.replace("\r", " ") : str;
    }
}
