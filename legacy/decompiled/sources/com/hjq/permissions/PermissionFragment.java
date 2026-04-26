package com.hjq.permissions;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.hjq.permissions.IPermissionInterceptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public final class PermissionFragment extends Fragment implements Runnable {
    private static final String REQUEST_CODE = "request_code";
    private static final List<Integer> REQUEST_CODE_ARRAY = new ArrayList();
    private static final String REQUEST_PERMISSIONS = "request_permissions";
    private OnPermissionCallback mCallBack;
    private boolean mDangerousRequest;
    private IPermissionInterceptor mInterceptor;
    private boolean mRequestFlag;
    private int mScreenOrientation;
    private boolean mSpecialRequest;

    public static void beginRequest(Activity activity, ArrayList<String> arrayList, IPermissionInterceptor iPermissionInterceptor, OnPermissionCallback onPermissionCallback) {
        int iNextInt;
        PermissionFragment permissionFragment = new PermissionFragment();
        Bundle bundle = new Bundle();
        do {
            iNextInt = new Random().nextInt((int) Math.pow(2.0d, 8.0d));
        } while (REQUEST_CODE_ARRAY.contains(Integer.valueOf(iNextInt)));
        REQUEST_CODE_ARRAY.add(Integer.valueOf(iNextInt));
        bundle.putInt(REQUEST_CODE, iNextInt);
        bundle.putStringArrayList(REQUEST_PERMISSIONS, arrayList);
        permissionFragment.setArguments(bundle);
        permissionFragment.setRetainInstance(true);
        permissionFragment.setRequestFlag(true);
        permissionFragment.setCallBack(onPermissionCallback);
        permissionFragment.setInterceptor(iPermissionInterceptor);
        permissionFragment.attachActivity(activity);
    }

    public void attachActivity(Activity activity) {
        activity.getFragmentManager().beginTransaction().add(this, toString()).commitAllowingStateLoss();
    }

    public void detachActivity(Activity activity) {
        activity.getFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }

    public void setCallBack(OnPermissionCallback onPermissionCallback) {
        this.mCallBack = onPermissionCallback;
    }

    public void setRequestFlag(boolean z) {
        this.mRequestFlag = z;
    }

    public void setInterceptor(IPermissionInterceptor iPermissionInterceptor) {
        this.mInterceptor = iPermissionInterceptor;
    }

    @Override // android.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        int requestedOrientation = activity.getRequestedOrientation();
        this.mScreenOrientation = requestedOrientation;
        if (requestedOrientation != -1) {
            return;
        }
        try {
            if (activity.getResources().getConfiguration().orientation == 2) {
                activity.setRequestedOrientation(PermissionUtils.isActivityReverse(activity) ? 8 : 0);
            } else {
                activity.setRequestedOrientation(PermissionUtils.isActivityReverse(activity) ? 9 : 1);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override // android.app.Fragment
    public void onDetach() {
        super.onDetach();
        Activity activity = getActivity();
        if (activity == null || this.mScreenOrientation != -1) {
            return;
        }
        activity.setRequestedOrientation(-1);
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mCallBack = null;
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        if (!this.mRequestFlag) {
            detachActivity(getActivity());
        } else {
            if (this.mSpecialRequest) {
                return;
            }
            this.mSpecialRequest = true;
            requestSpecialPermission();
        }
    }

    public void requestSpecialPermission() {
        Bundle arguments = getArguments();
        Activity activity = getActivity();
        if (arguments == null || activity == null) {
            return;
        }
        boolean z = false;
        for (String str : arguments.getStringArrayList(REQUEST_PERMISSIONS)) {
            if (PermissionUtils.isSpecialPermission(str) && !PermissionUtils.isGrantedPermission(activity, str) && (!Permission.MANAGE_EXTERNAL_STORAGE.equals(str) || PermissionUtils.isAndroid11())) {
                startActivityForResult(PermissionSettingPage.getSmartPermissionIntent(activity, PermissionUtils.asArrayList(str)), getArguments().getInt(REQUEST_CODE));
                z = true;
            }
        }
        if (z) {
            return;
        }
        requestDangerousPermission();
    }

    public void requestDangerousPermission() {
        final Activity activity = getActivity();
        Bundle arguments = getArguments();
        if (activity == null || arguments == null) {
            return;
        }
        final int i = arguments.getInt(REQUEST_CODE);
        final ArrayList<String> stringArrayList = arguments.getStringArrayList(REQUEST_PERMISSIONS);
        if (stringArrayList == null || stringArrayList.isEmpty()) {
            return;
        }
        if (!PermissionUtils.isAndroid6()) {
            int size = stringArrayList.size();
            int[] iArr = new int[size];
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = PermissionUtils.isGrantedPermission(activity, stringArrayList.get(i2)) ? 0 : -1;
            }
            onRequestPermissionsResult(i, (String[]) stringArrayList.toArray(new String[0]), iArr);
            return;
        }
        ArrayList arrayList = null;
        if (PermissionUtils.isAndroid10() && stringArrayList.contains(Permission.ACCESS_BACKGROUND_LOCATION)) {
            arrayList = new ArrayList();
            if (stringArrayList.contains(Permission.ACCESS_COARSE_LOCATION)) {
                arrayList.add(Permission.ACCESS_COARSE_LOCATION);
            }
            if (stringArrayList.contains(Permission.ACCESS_FINE_LOCATION)) {
                arrayList.add(Permission.ACCESS_FINE_LOCATION);
            }
        }
        if (!PermissionUtils.isAndroid10() || arrayList == null || arrayList.isEmpty()) {
            requestPermissions((String[]) stringArrayList.toArray(new String[stringArrayList.size() - 1]), getArguments().getInt(REQUEST_CODE));
        } else {
            beginRequest(activity, arrayList, new IPermissionInterceptor() { // from class: com.hjq.permissions.PermissionFragment.1
                @Override // com.hjq.permissions.IPermissionInterceptor
                public /* synthetic */ void deniedPermissions(Activity activity2, List<String> list, List<String> list2, boolean z, OnPermissionCallback onPermissionCallback) {
                    IPermissionInterceptor.CC.$default$deniedPermissions(this, activity2, list, list2, z, onPermissionCallback);
                }

                @Override // com.hjq.permissions.IPermissionInterceptor
                public /* synthetic */ void grantedPermissions(Activity activity2, List<String> list, List<String> list2, boolean z, OnPermissionCallback onPermissionCallback) {
                    IPermissionInterceptor.CC.$default$grantedPermissions(this, activity2, list, list2, z, onPermissionCallback);
                }

                @Override // com.hjq.permissions.IPermissionInterceptor
                public /* synthetic */ void requestPermissions(Activity activity2, OnPermissionCallback onPermissionCallback, List<String> list) {
                    PermissionFragment.beginRequest(activity2, new ArrayList(list), this, onPermissionCallback);
                }
            }, new OnPermissionCallback() { // from class: com.hjq.permissions.PermissionFragment.2
                @Override // com.hjq.permissions.OnPermissionCallback
                public void onGranted(List<String> list, boolean z) {
                    if (z && PermissionFragment.this.isAdded()) {
                        PermissionFragment.beginRequest(activity, PermissionUtils.asArrayList(Permission.ACCESS_BACKGROUND_LOCATION), new IPermissionInterceptor() { // from class: com.hjq.permissions.PermissionFragment.2.1
                            @Override // com.hjq.permissions.IPermissionInterceptor
                            public /* synthetic */ void deniedPermissions(Activity activity2, List<String> list2, List<String> list3, boolean z2, OnPermissionCallback onPermissionCallback) {
                                IPermissionInterceptor.CC.$default$deniedPermissions(this, activity2, list2, list3, z2, onPermissionCallback);
                            }

                            @Override // com.hjq.permissions.IPermissionInterceptor
                            public /* synthetic */ void grantedPermissions(Activity activity2, List<String> list2, List<String> list3, boolean z2, OnPermissionCallback onPermissionCallback) {
                                IPermissionInterceptor.CC.$default$grantedPermissions(this, activity2, list2, list3, z2, onPermissionCallback);
                            }

                            @Override // com.hjq.permissions.IPermissionInterceptor
                            public /* synthetic */ void requestPermissions(Activity activity2, OnPermissionCallback onPermissionCallback, List<String> list2) {
                                PermissionFragment.beginRequest(activity2, new ArrayList(list2), this, onPermissionCallback);
                            }
                        }, new OnPermissionCallback() { // from class: com.hjq.permissions.PermissionFragment.2.2
                            @Override // com.hjq.permissions.OnPermissionCallback
                            public void onGranted(List<String> list2, boolean z2) {
                                if (z2 && PermissionFragment.this.isAdded()) {
                                    int[] iArr2 = new int[stringArrayList.size()];
                                    Arrays.fill(iArr2, 0);
                                    PermissionFragment.this.onRequestPermissionsResult(i, (String[]) stringArrayList.toArray(new String[0]), iArr2);
                                }
                            }

                            @Override // com.hjq.permissions.OnPermissionCallback
                            public void onDenied(List<String> list2, boolean z2) {
                                if (PermissionFragment.this.isAdded()) {
                                    int[] iArr2 = new int[stringArrayList.size()];
                                    for (int i3 = 0; i3 < stringArrayList.size(); i3++) {
                                        iArr2[i3] = Permission.ACCESS_BACKGROUND_LOCATION.equals(stringArrayList.get(i3)) ? -1 : 0;
                                    }
                                    PermissionFragment.this.onRequestPermissionsResult(i, (String[]) stringArrayList.toArray(new String[0]), iArr2);
                                }
                            }
                        });
                    }
                }

                @Override // com.hjq.permissions.OnPermissionCallback
                public void onDenied(List<String> list, boolean z) {
                    if (PermissionFragment.this.isAdded()) {
                        int[] iArr2 = new int[stringArrayList.size()];
                        Arrays.fill(iArr2, -1);
                        PermissionFragment.this.onRequestPermissionsResult(i, (String[]) stringArrayList.toArray(new String[0]), iArr2);
                    }
                }
            });
        }
    }

    @Override // android.app.Fragment
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (strArr == null || strArr.length == 0 || iArr == null || iArr.length == 0) {
            return;
        }
        Bundle arguments = getArguments();
        Activity activity = getActivity();
        if (activity == null || arguments == null || this.mInterceptor == null || i != arguments.getInt(REQUEST_CODE)) {
            return;
        }
        OnPermissionCallback onPermissionCallback = this.mCallBack;
        this.mCallBack = null;
        IPermissionInterceptor iPermissionInterceptor = this.mInterceptor;
        this.mInterceptor = null;
        PermissionUtils.optimizePermissionResults(activity, strArr, iArr);
        ArrayList arrayListAsArrayList = PermissionUtils.asArrayList(strArr);
        REQUEST_CODE_ARRAY.remove(Integer.valueOf(i));
        detachActivity(activity);
        List<String> grantedPermissions = PermissionUtils.getGrantedPermissions(arrayListAsArrayList, iArr);
        if (grantedPermissions.size() == arrayListAsArrayList.size()) {
            iPermissionInterceptor.grantedPermissions(activity, arrayListAsArrayList, grantedPermissions, true, onPermissionCallback);
            return;
        }
        List<String> deniedPermissions = PermissionUtils.getDeniedPermissions(arrayListAsArrayList, iArr);
        iPermissionInterceptor.deniedPermissions(activity, arrayListAsArrayList, deniedPermissions, PermissionUtils.isPermissionPermanentDenied(activity, deniedPermissions), onPermissionCallback);
        if (grantedPermissions.isEmpty()) {
            return;
        }
        iPermissionInterceptor.grantedPermissions(activity, arrayListAsArrayList, grantedPermissions, false, onPermissionCallback);
    }

    @Override // android.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        Activity activity = getActivity();
        Bundle arguments = getArguments();
        if (activity == null || arguments == null || this.mDangerousRequest || i != arguments.getInt(REQUEST_CODE)) {
            return;
        }
        this.mDangerousRequest = true;
        activity.getWindow().getDecorView().postDelayed(this, 300L);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (isAdded()) {
            requestDangerousPermission();
        }
    }
}
