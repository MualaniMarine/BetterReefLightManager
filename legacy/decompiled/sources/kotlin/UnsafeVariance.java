package kotlin;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.MustBeDocumented;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes.dex */
@Target({})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.TYPE})
@Retention(RetentionPolicy.SOURCE)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
@Metadata(m533bv = {1, 0, 3}, m534d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, m535d2 = {"Lkotlin/UnsafeVariance;", "", "kotlin-stdlib"}, m536k = 1, m537mv = {1, 1, 16})
@Documented
public @interface UnsafeVariance {
}
