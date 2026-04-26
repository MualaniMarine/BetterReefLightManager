package io.reactivex.rxjava3.observers;

import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.VolatileSizeArrayList;
import io.reactivex.rxjava3.observers.BaseTestConsumer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseTestConsumer<T, U extends BaseTestConsumer<T, U>> {
    protected boolean checkSubscriptionOnce;
    protected long completions;
    protected Thread lastThread;
    protected CharSequence tag;
    protected boolean timeout;
    protected final List<T> values = new VolatileSizeArrayList();
    protected final List<Throwable> errors = new VolatileSizeArrayList();
    protected final CountDownLatch done = new CountDownLatch(1);

    protected abstract U assertSubscribed();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void dispose();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract boolean isDisposed();

    public final List<T> values() {
        return this.values;
    }

    protected final AssertionError fail(String message) {
        StringBuilder sb = new StringBuilder(message.length() + 64);
        sb.append(message);
        sb.append(" (");
        sb.append("latch = ");
        sb.append(this.done.getCount());
        sb.append(", ");
        sb.append("values = ");
        sb.append(this.values.size());
        sb.append(", ");
        sb.append("errors = ");
        sb.append(this.errors.size());
        sb.append(", ");
        sb.append("completions = ");
        sb.append(this.completions);
        if (this.timeout) {
            sb.append(", timeout!");
        }
        if (isDisposed()) {
            sb.append(", disposed!");
        }
        CharSequence charSequence = this.tag;
        if (charSequence != null) {
            sb.append(", tag = ");
            sb.append(charSequence);
        }
        sb.append(')');
        AssertionError assertionError = new AssertionError(sb.toString());
        if (!this.errors.isEmpty()) {
            if (this.errors.size() == 1) {
                assertionError.initCause(this.errors.get(0));
            } else {
                assertionError.initCause(new CompositeException(this.errors));
            }
        }
        return assertionError;
    }

    public final U await() throws InterruptedException {
        if (this.done.getCount() == 0) {
            return this;
        }
        this.done.await();
        return this;
    }

    public final boolean await(long time, TimeUnit unit) throws InterruptedException {
        boolean z = this.done.getCount() == 0 || this.done.await(time, unit);
        this.timeout = !z;
        return z;
    }

    public final U assertComplete() {
        long j = this.completions;
        if (j == 0) {
            throw fail("Not completed");
        }
        if (j <= 1) {
            return this;
        }
        throw fail("Multiple completions: " + j);
    }

    public final U assertNotComplete() {
        long j = this.completions;
        if (j == 1) {
            throw fail("Completed!");
        }
        if (j <= 1) {
            return this;
        }
        throw fail("Multiple completions: " + j);
    }

    public final U assertNoErrors() {
        if (this.errors.size() == 0) {
            return this;
        }
        throw fail("Error(s) present: " + this.errors);
    }

    public final U assertError(Throwable th) {
        return (U) assertError(Functions.equalsWith(th));
    }

    public final U assertError(Class<? extends Throwable> cls) {
        return (U) assertError(Functions.isInstanceOf(cls));
    }

    public final U assertError(Predicate<Throwable> errorPredicate) {
        int size = this.errors.size();
        if (size == 0) {
            throw fail("No errors");
        }
        boolean z = false;
        Iterator<Throwable> it = this.errors.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            try {
                if (errorPredicate.test(it.next())) {
                    z = true;
                    break;
                }
            } catch (Throwable th) {
                throw ExceptionHelper.wrapOrThrow(th);
            }
        }
        if (!z) {
            throw fail("Error not present");
        }
        if (size == 1) {
            return this;
        }
        throw fail("Error present but other errors as well");
    }

    public final U assertValue(T value) {
        if (this.values.size() != 1) {
            throw fail("expected: " + valueAndClass(value) + " but was: " + this.values);
        }
        T t = this.values.get(0);
        if (Objects.equals(value, t)) {
            return this;
        }
        throw fail("expected: " + valueAndClass(value) + " but was: " + valueAndClass(t));
    }

    public final U assertValue(Predicate<T> valuePredicate) {
        assertValueAt(0, (Predicate) valuePredicate);
        if (this.values.size() <= 1) {
            return this;
        }
        throw fail("Value present but other values as well");
    }

    public final U assertValueAt(int index, T value) {
        int size = this.values.size();
        if (size == 0) {
            throw fail("No values");
        }
        if (index >= size) {
            throw fail("Invalid index: " + index);
        }
        T t = this.values.get(index);
        if (Objects.equals(value, t)) {
            return this;
        }
        throw fail("expected: " + valueAndClass(value) + " but was: " + valueAndClass(t));
    }

    public final U assertValueAt(int index, Predicate<T> valuePredicate) {
        if (this.values.size() == 0) {
            throw fail("No values");
        }
        if (index >= this.values.size()) {
            throw fail("Invalid index: " + index);
        }
        try {
            if (valuePredicate.test(this.values.get(index))) {
                return this;
            }
            throw fail("Value not present");
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    public static String valueAndClass(Object o) {
        if (o == null) {
            return "null";
        }
        return o + " (class: " + o.getClass().getSimpleName() + ")";
    }

    public final U assertValueCount(int count) {
        int size = this.values.size();
        if (size == count) {
            return this;
        }
        throw fail("Value counts differ; expected: " + count + " but was: " + size);
    }

    public final U assertNoValues() {
        return (U) assertValueCount(0);
    }

    @SafeVarargs
    public final U assertValues(T... values) {
        int size = this.values.size();
        if (size != values.length) {
            throw fail("Value count differs; expected: " + values.length + " " + Arrays.toString(values) + " but was: " + size + " " + this.values);
        }
        for (int i = 0; i < size; i++) {
            T t = this.values.get(i);
            T t2 = values[i];
            if (!Objects.equals(t2, t)) {
                throw fail("Values at position " + i + " differ; expected: " + valueAndClass(t2) + " but was: " + valueAndClass(t));
            }
        }
        return this;
    }

    @SafeVarargs
    public final U assertValuesOnly(T... tArr) {
        return (U) assertSubscribed().assertValues(tArr).assertNoErrors().assertNotComplete();
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0059, code lost:
    
        if (r3 != false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x005b, code lost:
    
        if (r2 != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x005d, code lost:
    
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0076, code lost:
    
        throw fail("Fewer values received than expected (" + r1 + ")");
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x008f, code lost:
    
        throw fail("More values received than expected (" + r1 + ")");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final U assertValueSequence(java.lang.Iterable<? extends T> r6) {
        /*
            r5 = this;
            java.util.List<T> r0 = r5.values
            java.util.Iterator r0 = r0.iterator()
            java.util.Iterator r6 = r6.iterator()
            r1 = 0
        Lb:
            boolean r2 = r6.hasNext()
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L57
            if (r2 != 0) goto L18
            goto L57
        L18:
            java.lang.Object r2 = r6.next()
            java.lang.Object r3 = r0.next()
            boolean r4 = java.util.Objects.equals(r2, r3)
            if (r4 == 0) goto L29
            int r1 = r1 + 1
            goto Lb
        L29:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "Values at position "
            r6.append(r0)
            r6.append(r1)
            java.lang.String r0 = " differ; expected: "
            r6.append(r0)
            java.lang.String r0 = valueAndClass(r2)
            r6.append(r0)
            java.lang.String r0 = " but was: "
            r6.append(r0)
            java.lang.String r0 = valueAndClass(r3)
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            java.lang.AssertionError r6 = r5.fail(r6)
            throw r6
        L57:
            java.lang.String r6 = ")"
            if (r3 != 0) goto L77
            if (r2 != 0) goto L5e
            return r5
        L5e:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "Fewer values received than expected ("
            r0.append(r2)
            r0.append(r1)
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            java.lang.AssertionError r6 = r5.fail(r6)
            throw r6
        L77:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "More values received than expected ("
            r0.append(r2)
            r0.append(r1)
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            java.lang.AssertionError r6 = r5.fail(r6)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.observers.BaseTestConsumer.assertValueSequence(java.lang.Iterable):io.reactivex.rxjava3.observers.BaseTestConsumer");
    }

    @SafeVarargs
    public final U assertResult(T... tArr) {
        return (U) assertSubscribed().assertValues(tArr).assertNoErrors().assertComplete();
    }

    @SafeVarargs
    public final U assertFailure(Class<? extends Throwable> cls, T... tArr) {
        return (U) assertSubscribed().assertValues(tArr).assertError(cls).assertNotComplete();
    }

    public final U awaitDone(long time, TimeUnit unit) {
        try {
            if (!this.done.await(time, unit)) {
                this.timeout = true;
                dispose();
            }
            return this;
        } catch (InterruptedException e) {
            dispose();
            throw ExceptionHelper.wrapOrThrow(e);
        }
    }

    public final U assertEmpty() {
        return (U) assertSubscribed().assertNoValues().assertNoErrors().assertNotComplete();
    }

    public final U withTag(CharSequence tag) {
        this.tag = tag;
        return this;
    }

    public final U awaitCount(int atLeast) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - jCurrentTimeMillis >= 5000) {
                this.timeout = true;
                break;
            }
            if (this.done.getCount() == 0 || this.values.size() >= atLeast) {
                break;
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }
}
