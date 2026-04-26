package com.hjq.permissions;

import android.app.Activity;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface IPermissionInterceptor {
    void deniedPermissions(Activity activity, List<String> list, List<String> list2, boolean z, OnPermissionCallback onPermissionCallback);

    void grantedPermissions(Activity activity, List<String> list, List<String> list2, boolean z, OnPermissionCallback onPermissionCallback);

    void requestPermissions(Activity activity, OnPermissionCallback onPermissionCallback, List<String> list);

    /* JADX INFO: renamed from: com.hjq.permissions.IPermissionInterceptor$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$grantedPermissions(IPermissionInterceptor _this, Activity activity, List list, List list2, boolean z, OnPermissionCallback onPermissionCallback) {
            if (onPermissionCallback == null) {
                return;
            }
            onPermissionCallback.onGranted(list2, z);
        }

        public static void $default$deniedPermissions(IPermissionInterceptor _this, Activity activity, List list, List list2, boolean z, OnPermissionCallback onPermissionCallback) {
            if (onPermissionCallback == null) {
                return;
            }
            onPermissionCallback.onDenied(list2, z);
        }
    }
}
