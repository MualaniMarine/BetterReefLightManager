package per.goweii.anylayer;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.Stack;

/* JADX INFO: loaded from: classes.dex */
final class ActivityHolder implements Application.ActivityLifecycleCallbacks {
    private static ActivityHolder INSTANCE;
    private final Stack<Activity> mActivityStack = new Stack<>();
    private final Application mApplication;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    private ActivityHolder(Application application) {
        Utils.requireNonNull(application, "application == null");
        this.mApplication = application;
        application.registerActivityLifecycleCallbacks(this);
    }

    static void init(Application application) {
        Utils.requireNonNull(application, "application == null");
        if (INSTANCE == null) {
            INSTANCE = new ActivityHolder(application);
        }
    }

    static Application getApplication() {
        ActivityHolder activityHolder = INSTANCE;
        if (activityHolder == null) {
            return null;
        }
        return activityHolder.mApplication;
    }

    static Activity getActivity(Class<Activity> cls) {
        Utils.requireNonNull(cls, "clazz == null");
        ActivityHolder activityHolder = INSTANCE;
        if (activityHolder == null || activityHolder.mActivityStack.empty()) {
            return null;
        }
        for (int size = INSTANCE.mActivityStack.size() - 1; size >= 0; size--) {
            Activity activity = INSTANCE.mActivityStack.get(size);
            if (TextUtils.equals(cls.getName(), activity.getClass().getName())) {
                return activity;
            }
        }
        return null;
    }

    static Activity getCurrentActivity() {
        ActivityHolder activityHolder = INSTANCE;
        if (activityHolder == null || activityHolder.mActivityStack.empty()) {
            return null;
        }
        return INSTANCE.mActivityStack.peek();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        this.mActivityStack.push(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        this.mActivityStack.pop();
    }
}
