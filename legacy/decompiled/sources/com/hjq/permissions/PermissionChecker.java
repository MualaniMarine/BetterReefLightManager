package com.hjq.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Build;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
final class PermissionChecker {
    PermissionChecker() {
    }

    static boolean checkActivityStatus(Activity activity, boolean z) {
        if (activity == null) {
            if (z) {
                throw new IllegalArgumentException("The instance of the context must be an activity object");
            }
            return false;
        }
        if (activity.isFinishing()) {
            if (z) {
                throw new IllegalStateException("The activity has been finishing, please manually determine the status of the activity");
            }
            return false;
        }
        if (Build.VERSION.SDK_INT < 17 || !activity.isDestroyed()) {
            return true;
        }
        if (z) {
            throw new IllegalStateException("The activity has been destroyed, please manually determine the status of the activity");
        }
        return false;
    }

    static boolean checkPermissionArgument(List<String> list, boolean z) {
        if (list == null || list.isEmpty()) {
            if (z) {
                throw new IllegalArgumentException("The requested permission cannot be empty");
            }
            return false;
        }
        if (z) {
            ArrayList arrayList = new ArrayList();
            Field[] declaredFields = Permission.class.getDeclaredFields();
            if (declaredFields.length == 0) {
                return true;
            }
            for (Field field : declaredFields) {
                if (String.class.equals(field.getType())) {
                    try {
                        arrayList.add((String) field.get(null));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            for (String str : list) {
                if (!arrayList.contains(str)) {
                    throw new IllegalArgumentException("The " + str + " is not a dangerous permission or special permission, please do not apply dynamically");
                }
            }
        }
        return true;
    }

    static void checkStoragePermission(Context context, List<String> list) {
        if (list.contains(Permission.MANAGE_EXTERNAL_STORAGE) || list.contains(Permission.READ_EXTERNAL_STORAGE) || list.contains(Permission.WRITE_EXTERNAL_STORAGE)) {
            boolean zIsScopedStorage = PermissionUtils.isScopedStorage(context);
            XmlResourceParser androidManifest = PermissionUtils.parseAndroidManifest(context);
            if (androidManifest == null) {
                return;
            }
            while (true) {
                try {
                    try {
                        if (androidManifest.getEventType() == 2 && "application".equals(androidManifest.getName())) {
                            int i = context.getApplicationInfo().targetSdkVersion;
                            boolean attributeBooleanValue = androidManifest.getAttributeBooleanValue(PermissionUtils.getAndroidNamespace(), "requestLegacyExternalStorage", false);
                            if (i >= 29 && !attributeBooleanValue && (list.contains(Permission.MANAGE_EXTERNAL_STORAGE) || !zIsScopedStorage)) {
                                throw new IllegalStateException("Please register the android:requestLegacyExternalStorage=\"true\" attribute in the AndroidManifest.xml file, otherwise it will cause incompatibility with the old version");
                            }
                            if (i >= 30 && !list.contains(Permission.MANAGE_EXTERNAL_STORAGE) && !zIsScopedStorage) {
                                throw new IllegalArgumentException("The storage permission application is abnormal. If you have adapted the scope storage, please register the <meta-data android:name=\"ScopedStorage\" android:value=\"true\" /> attribute in the AndroidManifest.xml file. If there is no adaptation scope storage, please use MANAGE_EXTERNAL_STORAGE to apply for permission");
                            }
                        } else if (androidManifest.next() == 1) {
                            break;
                        }
                    } catch (IOException | XmlPullParserException e) {
                        e.printStackTrace();
                    }
                } finally {
                    androidManifest.close();
                }
            }
        }
    }

    static void checkLocationPermission(Context context, List<String> list) {
        if (context.getApplicationInfo().targetSdkVersion >= 31 && list.contains(Permission.ACCESS_FINE_LOCATION) && !list.contains(Permission.ACCESS_COARSE_LOCATION)) {
            throw new IllegalArgumentException("If your app targets Android 12 or higher and requests the ACCESS_FINE_LOCATION runtime permission, you must also request the ACCESS_COARSE_LOCATION permission. You must include both permissions in a single runtime request.");
        }
        if (list.contains(Permission.ACCESS_BACKGROUND_LOCATION)) {
            if (list.contains(Permission.ACCESS_COARSE_LOCATION) && !list.contains(Permission.ACCESS_FINE_LOCATION)) {
                throw new IllegalArgumentException("The application for background location permissions must include precise location permissions");
            }
            for (String str : list) {
                if (!Permission.ACCESS_FINE_LOCATION.equals(str) && !Permission.ACCESS_COARSE_LOCATION.equals(str) && !Permission.ACCESS_BACKGROUND_LOCATION.equals(str)) {
                    throw new IllegalArgumentException("Because it includes background location permissions, do not apply for permissions unrelated to location");
                }
            }
        }
    }

    static void checkTargetSdkVersion(Context context, List<String> list) {
        int i;
        if (list.contains(Permission.BLUETOOTH_SCAN) || list.contains(Permission.BLUETOOTH_CONNECT) || list.contains(Permission.BLUETOOTH_ADVERTISE)) {
            i = 31;
        } else if (list.contains(Permission.MANAGE_EXTERNAL_STORAGE)) {
            i = 30;
        } else if (list.contains(Permission.ACCEPT_HANDOVER)) {
            i = 28;
        } else if (list.contains(Permission.ACCESS_BACKGROUND_LOCATION) || list.contains(Permission.ACTIVITY_RECOGNITION) || list.contains(Permission.ACCESS_MEDIA_LOCATION)) {
            i = 29;
        } else {
            i = (list.contains(Permission.REQUEST_INSTALL_PACKAGES) || list.contains(Permission.ANSWER_PHONE_CALLS) || list.contains(Permission.READ_PHONE_NUMBERS)) ? 26 : 23;
        }
        if (context.getApplicationInfo().targetSdkVersion >= i) {
            return;
        }
        throw new RuntimeException("The targetSdkVersion SDK must be " + i + " or more, if you do not want to upgrade targetSdkVersion, please apply with the old permissions");
    }

    static void checkManifestPermissions(Context context, List<String> list) {
        HashMap<String, Integer> manifestPermissions = PermissionUtils.getManifestPermissions(context);
        if (manifestPermissions.isEmpty()) {
            throw new IllegalStateException("No permissions are registered in the AndroidManifest.xml file");
        }
        int i = Build.VERSION.SDK_INT >= 24 ? context.getApplicationInfo().minSdkVersion : 23;
        for (String str : list) {
            if (!Permission.NOTIFICATION_SERVICE.equals(str)) {
                if (i < 31) {
                    if (Permission.BLUETOOTH_SCAN.equals(str)) {
                        checkManifestPermission(manifestPermissions, "android.permission.BLUETOOTH_ADMIN", 30);
                        checkManifestPermission(manifestPermissions, Permission.ACCESS_COARSE_LOCATION, 30);
                    }
                    if (Permission.BLUETOOTH_CONNECT.equals(str)) {
                        checkManifestPermission(manifestPermissions, "android.permission.BLUETOOTH", 30);
                    }
                    if (Permission.BLUETOOTH_ADVERTISE.equals(str)) {
                        checkManifestPermission(manifestPermissions, "android.permission.BLUETOOTH_ADMIN", 30);
                    }
                }
                if (i < 30 && Permission.MANAGE_EXTERNAL_STORAGE.equals(str)) {
                    checkManifestPermission(manifestPermissions, Permission.READ_EXTERNAL_STORAGE, 29);
                    checkManifestPermission(manifestPermissions, Permission.WRITE_EXTERNAL_STORAGE, 29);
                }
                if (i < 29 && Permission.ACTIVITY_RECOGNITION.equals(str)) {
                    checkManifestPermission(manifestPermissions, Permission.BODY_SENSORS, 26);
                }
                if (i < 26 && Permission.READ_PHONE_NUMBERS.equals(str)) {
                    checkManifestPermission(manifestPermissions, Permission.READ_PHONE_STATE, 25);
                }
                checkManifestPermission(manifestPermissions, str, Integer.MAX_VALUE);
            }
        }
    }

    static void checkManifestPermission(HashMap<String, Integer> map, String str, int i) {
        String str2;
        if (!map.containsKey(str)) {
            throw new IllegalStateException("Please register permissions in the AndroidManifest.xml file <uses-permission android:name=\"" + str + "\" />");
        }
        Integer num = map.get(str);
        if (num != null && num.intValue() < i) {
            StringBuilder sb = new StringBuilder();
            sb.append("The AndroidManifest.xml file <uses-permission android:name=\"");
            sb.append(str);
            sb.append("\" android:maxSdkVersion=\"");
            sb.append(num);
            sb.append("\" /> does not meet the requirements, ");
            if (i != Integer.MAX_VALUE) {
                str2 = "the minimum requirement for maxSdkVersion is " + i;
            } else {
                str2 = "please delete the android:maxSdkVersion=\"" + num + "\" attribute";
            }
            sb.append(str2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static void optimizeDeprecatedPermission(List<String> list) {
        if (!PermissionUtils.isAndroid12() && list.contains(Permission.BLUETOOTH_SCAN) && !list.contains(Permission.ACCESS_COARSE_LOCATION)) {
            list.add(Permission.ACCESS_COARSE_LOCATION);
        }
        if (!PermissionUtils.isAndroid12() && list.contains(Permission.BLUETOOTH_SCAN)) {
            list.add(Permission.ACCESS_COARSE_LOCATION);
        }
        if (list.contains(Permission.MANAGE_EXTERNAL_STORAGE)) {
            if (list.contains(Permission.READ_EXTERNAL_STORAGE) || list.contains(Permission.WRITE_EXTERNAL_STORAGE)) {
                throw new IllegalArgumentException("If you have applied for MANAGE_EXTERNAL_STORAGE permissions, do not apply for the READ_EXTERNAL_STORAGE and WRITE_EXTERNAL_STORAGE permissions");
            }
            if (!PermissionUtils.isAndroid11()) {
                list.add(Permission.READ_EXTERNAL_STORAGE);
                list.add(Permission.WRITE_EXTERNAL_STORAGE);
            }
        }
        if (!PermissionUtils.isAndroid8() && list.contains(Permission.READ_PHONE_NUMBERS) && !list.contains(Permission.READ_PHONE_STATE)) {
            list.add(Permission.READ_PHONE_STATE);
        }
        if (PermissionUtils.isAndroid10() || !list.contains(Permission.ACTIVITY_RECOGNITION) || list.contains(Permission.BODY_SENSORS)) {
            return;
        }
        list.add(Permission.BODY_SENSORS);
    }
}
