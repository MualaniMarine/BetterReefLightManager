package com.hjq.permissions;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import com.hjq.permissions.Permission;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
final class PermissionUtils {
    static String getAndroidNamespace() {
        return "http://schemas.android.com/apk/res/android";
    }

    PermissionUtils() {
    }

    static boolean isAndroid12() {
        return Build.VERSION.SDK_INT >= 31;
    }

    static boolean isAndroid11() {
        return Build.VERSION.SDK_INT >= 30;
    }

    static boolean isAndroid10() {
        return Build.VERSION.SDK_INT >= 29;
    }

    static boolean isAndroid9() {
        return Build.VERSION.SDK_INT >= 28;
    }

    static boolean isAndroid8() {
        return Build.VERSION.SDK_INT >= 26;
    }

    static boolean isAndroid6() {
        return Build.VERSION.SDK_INT >= 23;
    }

    static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= 21;
    }

    static boolean isDebugMode(Context context) {
        return (context.getApplicationInfo().flags & 2) != 0;
    }

    static HashMap<String, Integer> getManifestPermissions(Context context) {
        HashMap<String, Integer> map = new HashMap<>();
        XmlResourceParser androidManifest = parseAndroidManifest(context);
        if (androidManifest != null) {
            do {
                try {
                    try {
                        if (androidManifest.getEventType() == 2 && "uses-permission".equals(androidManifest.getName())) {
                            map.put(androidManifest.getAttributeValue(getAndroidNamespace(), "name"), Integer.valueOf(androidManifest.getAttributeIntValue(getAndroidNamespace(), "maxSdkVersion", Integer.MAX_VALUE)));
                        }
                    } catch (IOException | XmlPullParserException e) {
                        e.printStackTrace();
                    }
                } finally {
                    androidManifest.close();
                }
            } while (androidManifest.next() != 1);
        }
        if (map.isEmpty()) {
            try {
                String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
                if (strArr != null) {
                    for (String str : strArr) {
                        map.put(str, Integer.MAX_VALUE);
                    }
                }
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        return map;
    }

    static boolean isGrantedStoragePermission(Context context) {
        if (isAndroid11()) {
            return Environment.isExternalStorageManager();
        }
        return isGrantedPermissions(context, asArrayList(Permission.Group.STORAGE));
    }

    static boolean isGrantedInstallPermission(Context context) {
        if (isAndroid8()) {
            return context.getPackageManager().canRequestPackageInstalls();
        }
        return true;
    }

    static boolean isGrantedWindowPermission(Context context) {
        if (isAndroid6()) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }

    static boolean isGrantedSettingPermission(Context context) {
        if (isAndroid6()) {
            return Settings.System.canWrite(context);
        }
        return true;
    }

    static boolean isGrantedNotifyPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return ((NotificationManager) context.getSystemService(NotificationManager.class)).areNotificationsEnabled();
        }
        if (Build.VERSION.SDK_INT >= 19) {
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
            try {
                return ((Integer) appOpsManager.getClass().getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class).invoke(appOpsManager, Integer.valueOf(((Integer) appOpsManager.getClass().getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(context.getApplicationInfo().uid), context.getPackageName())).intValue() == 0;
            } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | RuntimeException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    static boolean isGrantedPackagePermission(Context context) {
        int iCheckOpNoThrow;
        if (!isAndroid5()) {
            return true;
        }
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
        if (isAndroid10()) {
            iCheckOpNoThrow = appOpsManager.unsafeCheckOpNoThrow("android:get_usage_stats", context.getApplicationInfo().uid, context.getPackageName());
        } else {
            iCheckOpNoThrow = appOpsManager.checkOpNoThrow("android:get_usage_stats", context.getApplicationInfo().uid, context.getPackageName());
        }
        return iCheckOpNoThrow == 0;
    }

    static boolean containsSpecialPermission(List<String> list) {
        if (list != null && !list.isEmpty()) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                if (isSpecialPermission(it.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean isSpecialPermission(String str) {
        return Permission.MANAGE_EXTERNAL_STORAGE.equals(str) || Permission.REQUEST_INSTALL_PACKAGES.equals(str) || Permission.SYSTEM_ALERT_WINDOW.equals(str) || Permission.WRITE_SETTINGS.equals(str) || Permission.NOTIFICATION_SERVICE.equals(str) || Permission.PACKAGE_USAGE_STATS.equals(str);
    }

    static boolean isGrantedPermissions(Context context, List<String> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (!isGrantedPermission(context, it.next())) {
                return false;
            }
        }
        return true;
    }

    static List<String> getDeniedPermissions(Context context, List<String> list) {
        ArrayList arrayList = new ArrayList(list.size());
        if (!isAndroid6()) {
            return arrayList;
        }
        for (String str : list) {
            if (!isGrantedPermission(context, str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    static boolean isGrantedPermission(Context context, String str) {
        if (Permission.NOTIFICATION_SERVICE.equals(str)) {
            return isGrantedNotifyPermission(context);
        }
        if (Permission.PACKAGE_USAGE_STATS.equals(str)) {
            return isGrantedPackagePermission(context);
        }
        if (!isAndroid6()) {
            return true;
        }
        if (Permission.MANAGE_EXTERNAL_STORAGE.equals(str)) {
            return isGrantedStoragePermission(context);
        }
        if (Permission.REQUEST_INSTALL_PACKAGES.equals(str)) {
            return isGrantedInstallPermission(context);
        }
        if (Permission.SYSTEM_ALERT_WINDOW.equals(str)) {
            return isGrantedWindowPermission(context);
        }
        if (Permission.WRITE_SETTINGS.equals(str)) {
            return isGrantedSettingPermission(context);
        }
        if (!isAndroid12()) {
            if (Permission.BLUETOOTH_SCAN.equals(str)) {
                return context.checkSelfPermission(Permission.ACCESS_COARSE_LOCATION) == 0;
            }
            if (Permission.BLUETOOTH_CONNECT.equals(str) || Permission.BLUETOOTH_ADVERTISE.equals(str)) {
                return true;
            }
        }
        if (!isAndroid10()) {
            if (Permission.ACCESS_BACKGROUND_LOCATION.equals(str)) {
                return context.checkSelfPermission(Permission.ACCESS_FINE_LOCATION) == 0;
            }
            if (Permission.ACTIVITY_RECOGNITION.equals(str)) {
                return context.checkSelfPermission(Permission.BODY_SENSORS) == 0;
            }
            if (Permission.ACCESS_MEDIA_LOCATION.equals(str)) {
                return true;
            }
        }
        if (!isAndroid9() && Permission.ACCEPT_HANDOVER.equals(str)) {
            return true;
        }
        if (!isAndroid8()) {
            if (Permission.ANSWER_PHONE_CALLS.equals(str)) {
                return true;
            }
            if (Permission.READ_PHONE_NUMBERS.equals(str)) {
                return context.checkSelfPermission(Permission.READ_PHONE_STATE) == 0;
            }
        }
        return context.checkSelfPermission(str) == 0;
    }

    static void optimizePermissionResults(Activity activity, String[] strArr, int[] iArr) {
        for (int i = 0; i < strArr.length; i++) {
            String str = strArr[i];
            boolean zIsSpecialPermission = isSpecialPermission(str);
            boolean z = true;
            if (!isAndroid12() && (Permission.BLUETOOTH_SCAN.equals(str) || Permission.BLUETOOTH_CONNECT.equals(str) || Permission.BLUETOOTH_ADVERTISE.equals(str))) {
                zIsSpecialPermission = true;
            }
            if (!isAndroid10() && (Permission.ACCESS_BACKGROUND_LOCATION.equals(str) || Permission.ACTIVITY_RECOGNITION.equals(str) || Permission.ACCESS_MEDIA_LOCATION.equals(str))) {
                zIsSpecialPermission = true;
            }
            if (!isAndroid9() && Permission.ACCEPT_HANDOVER.equals(str)) {
                zIsSpecialPermission = true;
            }
            if (isAndroid8() || (!Permission.ANSWER_PHONE_CALLS.equals(str) && !Permission.READ_PHONE_NUMBERS.equals(str))) {
                z = zIsSpecialPermission;
            }
            if (z) {
                iArr[i] = isGrantedPermission(activity, str) ? 0 : -1;
            }
        }
    }

    static boolean isPermissionPermanentDenied(Activity activity, List<String> list) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (isPermissionPermanentDenied(activity, it.next())) {
                return true;
            }
        }
        return false;
    }

    static boolean isPermissionPermanentDenied(Activity activity, String str) {
        if (!isAndroid6() || isSpecialPermission(str)) {
            return false;
        }
        if (!isAndroid12()) {
            if (Permission.BLUETOOTH_SCAN.equals(str)) {
                return (isGrantedPermission(activity, Permission.ACCESS_COARSE_LOCATION) || activity.shouldShowRequestPermissionRationale(Permission.ACCESS_COARSE_LOCATION)) ? false : true;
            }
            if (Permission.BLUETOOTH_CONNECT.equals(str) || Permission.BLUETOOTH_ADVERTISE.equals(str)) {
                return false;
            }
        }
        if (isAndroid10() && Permission.ACCESS_BACKGROUND_LOCATION.equals(str) && !isGrantedPermission(activity, Permission.ACCESS_BACKGROUND_LOCATION) && !isGrantedPermission(activity, Permission.ACCESS_FINE_LOCATION)) {
            return !activity.shouldShowRequestPermissionRationale(Permission.ACCESS_FINE_LOCATION);
        }
        if (!isAndroid10()) {
            if (Permission.ACCESS_BACKGROUND_LOCATION.equals(str)) {
                return (isGrantedPermission(activity, Permission.ACCESS_FINE_LOCATION) || activity.shouldShowRequestPermissionRationale(Permission.ACCESS_FINE_LOCATION)) ? false : true;
            }
            if (Permission.ACTIVITY_RECOGNITION.equals(str)) {
                return (isGrantedPermission(activity, Permission.BODY_SENSORS) || activity.shouldShowRequestPermissionRationale(Permission.BODY_SENSORS)) ? false : true;
            }
            if (Permission.ACCESS_MEDIA_LOCATION.equals(str)) {
                return false;
            }
        }
        if (!isAndroid9() && Permission.ACCEPT_HANDOVER.equals(str)) {
            return false;
        }
        if (!isAndroid8()) {
            if (Permission.ANSWER_PHONE_CALLS.equals(str)) {
                return false;
            }
            if (Permission.READ_PHONE_NUMBERS.equals(str)) {
                return (isGrantedPermission(activity, Permission.READ_PHONE_STATE) || activity.shouldShowRequestPermissionRationale(Permission.READ_PHONE_STATE)) ? false : true;
            }
        }
        return (isGrantedPermission(activity, str) || activity.shouldShowRequestPermissionRationale(str)) ? false : true;
    }

    static List<String> getDeniedPermissions(List<String> list, int[] iArr) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < iArr.length; i++) {
            if (iArr[i] == -1) {
                arrayList.add(list.get(i));
            }
        }
        return arrayList;
    }

    static List<String> getGrantedPermissions(List<String> list, int[] iArr) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < iArr.length; i++) {
            if (iArr[i] == 0) {
                arrayList.add(list.get(i));
            }
        }
        return arrayList;
    }

    static <T> ArrayList<T> asArrayList(T... tArr) {
        ArrayList<T> arrayList = new ArrayList<>(tArr.length);
        if (tArr != null && tArr.length != 0) {
            for (T t : tArr) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    @SafeVarargs
    static <T> ArrayList<T> asArrayLists(T[]... tArr) {
        ArrayList<T> arrayList = new ArrayList<>();
        if (tArr != null && tArr.length != 0) {
            for (T[] tArr2 : tArr) {
                arrayList.addAll(asArrayList(tArr2));
            }
        }
        return arrayList;
    }

    static Activity findActivity(Context context) {
        while (!(context instanceof Activity)) {
            if (!(context instanceof ContextWrapper) || (context = ((ContextWrapper) context).getBaseContext()) == null) {
                return null;
            }
        }
        return (Activity) context;
    }

    static int findApkPathCookie(Context context) {
        AssetManager assets = context.getAssets();
        try {
            Integer num = (Integer) assets.getClass().getDeclaredMethod("addAssetPath", String.class).invoke(assets, context.getApplicationInfo().sourceDir);
            if (num != null) {
                return num.intValue();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        return 0;
    }

    static XmlResourceParser parseAndroidManifest(Context context) {
        int iFindApkPathCookie = findApkPathCookie(context);
        if (iFindApkPathCookie == 0) {
            return null;
        }
        try {
            XmlResourceParser xmlResourceParserOpenXmlResourceParser = context.getAssets().openXmlResourceParser(iFindApkPathCookie, "AndroidManifest.xml");
            do {
                if (xmlResourceParserOpenXmlResourceParser.getEventType() == 2 && "manifest".equals(xmlResourceParserOpenXmlResourceParser.getName()) && TextUtils.equals(context.getPackageName(), xmlResourceParserOpenXmlResourceParser.getAttributeValue(null, "package"))) {
                    return xmlResourceParserOpenXmlResourceParser;
                }
            } while (xmlResourceParserOpenXmlResourceParser.next() != 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    static boolean isScopedStorage(Context context) {
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle == null || !bundle.containsKey("ScopedStorage")) {
                return false;
            }
            return Boolean.parseBoolean(String.valueOf(bundle.get("ScopedStorage")));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean isActivityReverse(Activity activity) {
        int rotation;
        if (Build.VERSION.SDK_INT >= 30) {
            rotation = activity.getDisplay().getRotation();
        } else {
            rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        }
        return rotation == 2 || rotation == 3;
    }
}
