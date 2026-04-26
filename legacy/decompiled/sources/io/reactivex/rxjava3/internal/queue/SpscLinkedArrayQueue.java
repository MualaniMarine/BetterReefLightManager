package io.reactivex.rxjava3.internal.queue;

import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* JADX INFO: loaded from: classes.dex */
public final class SpscLinkedArrayQueue<T> implements SimplePlainQueue<T> {
    AtomicReferenceArray<Object> consumerBuffer;
    final int consumerMask;
    AtomicReferenceArray<Object> producerBuffer;
    long producerLookAhead;
    int producerLookAheadStep;
    final int producerMask;
    static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096).intValue();
    private static final Object HAS_NEXT = new Object();
    final AtomicLong producerIndex = new AtomicLong();
    final AtomicLong consumerIndex = new AtomicLong();

    private static int calcDirectOffset(int index) {
        return index;
    }

    public SpscLinkedArrayQueue(final int bufferSize) {
        int iRoundToPowerOfTwo = Pow2.roundToPowerOfTwo(Math.max(8, bufferSize));
        int i = iRoundToPowerOfTwo - 1;
        AtomicReferenceArray<Object> atomicReferenceArray = new AtomicReferenceArray<>(iRoundToPowerOfTwo + 1);
        this.producerBuffer = atomicReferenceArray;
        this.producerMask = i;
        adjustLookAheadStep(iRoundToPowerOfTwo);
        this.consumerBuffer = atomicReferenceArray;
        this.consumerMask = i;
        this.producerLookAhead = i - 1;
        soProducerIndex(0L);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean offer(final T e) {
        if (e == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        AtomicReferenceArray<Object> atomicReferenceArray = this.producerBuffer;
        long jLpProducerIndex = lpProducerIndex();
        int i = this.producerMask;
        int iCalcWrappedOffset = calcWrappedOffset(jLpProducerIndex, i);
        if (jLpProducerIndex < this.producerLookAhead) {
            return writeToQueue(atomicReferenceArray, e, jLpProducerIndex, iCalcWrappedOffset);
        }
        long j = ((long) this.producerLookAheadStep) + jLpProducerIndex;
        if (lvElement(atomicReferenceArray, calcWrappedOffset(j, i)) == null) {
            this.producerLookAhead = j - 1;
            return writeToQueue(atomicReferenceArray, e, jLpProducerIndex, iCalcWrappedOffset);
        }
        if (lvElement(atomicReferenceArray, calcWrappedOffset(1 + jLpProducerIndex, i)) == null) {
            return writeToQueue(atomicReferenceArray, e, jLpProducerIndex, iCalcWrappedOffset);
        }
        resize(atomicReferenceArray, jLpProducerIndex, iCalcWrappedOffset, e, i);
        return true;
    }

    private boolean writeToQueue(final AtomicReferenceArray<Object> buffer, final T e, final long index, final int offset) {
        soElement(buffer, offset, e);
        soProducerIndex(index + 1);
        return true;
    }

    private void resize(final AtomicReferenceArray<Object> oldBuffer, final long currIndex, final int offset, final T e, final long mask) {
        AtomicReferenceArray<Object> atomicReferenceArray = new AtomicReferenceArray<>(oldBuffer.length());
        this.producerBuffer = atomicReferenceArray;
        this.producerLookAhead = (mask + currIndex) - 1;
        soElement(atomicReferenceArray, offset, e);
        soNext(oldBuffer, atomicReferenceArray);
        soElement(oldBuffer, offset, HAS_NEXT);
        soProducerIndex(currIndex + 1);
    }

    private void soNext(AtomicReferenceArray<Object> curr, AtomicReferenceArray<Object> next) {
        soElement(curr, calcDirectOffset(curr.length() - 1), next);
    }

    private AtomicReferenceArray<Object> lvNextBufferAndUnlink(AtomicReferenceArray<Object> curr, int nextIndex) {
        int iCalcDirectOffset = calcDirectOffset(nextIndex);
        AtomicReferenceArray<Object> atomicReferenceArray = (AtomicReferenceArray) lvElement(curr, iCalcDirectOffset);
        soElement(curr, iCalcDirectOffset, null);
        return atomicReferenceArray;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue, io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public T poll() {
        AtomicReferenceArray<Object> atomicReferenceArray = this.consumerBuffer;
        long jLpConsumerIndex = lpConsumerIndex();
        int i = this.consumerMask;
        int iCalcWrappedOffset = calcWrappedOffset(jLpConsumerIndex, i);
        T t = (T) lvElement(atomicReferenceArray, iCalcWrappedOffset);
        boolean z = t == HAS_NEXT;
        if (t == null || z) {
            if (z) {
                return newBufferPoll(lvNextBufferAndUnlink(atomicReferenceArray, i + 1), jLpConsumerIndex, i);
            }
            return null;
        }
        soElement(atomicReferenceArray, iCalcWrappedOffset, null);
        soConsumerIndex(jLpConsumerIndex + 1);
        return t;
    }

    private T newBufferPoll(AtomicReferenceArray<Object> atomicReferenceArray, long j, int i) {
        this.consumerBuffer = atomicReferenceArray;
        int iCalcWrappedOffset = calcWrappedOffset(j, i);
        T t = (T) lvElement(atomicReferenceArray, iCalcWrappedOffset);
        if (t != null) {
            soElement(atomicReferenceArray, iCalcWrappedOffset, null);
            soConsumerIndex(j + 1);
        }
        return t;
    }

    public T peek() {
        AtomicReferenceArray<Object> atomicReferenceArray = this.consumerBuffer;
        long jLpConsumerIndex = lpConsumerIndex();
        int i = this.consumerMask;
        T t = (T) lvElement(atomicReferenceArray, calcWrappedOffset(jLpConsumerIndex, i));
        return t == HAS_NEXT ? newBufferPeek(lvNextBufferAndUnlink(atomicReferenceArray, i + 1), jLpConsumerIndex, i) : t;
    }

    private T newBufferPeek(AtomicReferenceArray<Object> atomicReferenceArray, long j, int i) {
        this.consumerBuffer = atomicReferenceArray;
        return (T) lvElement(atomicReferenceArray, calcWrappedOffset(j, i));
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }

    public int size() {
        long jLvConsumerIndex = lvConsumerIndex();
        while (true) {
            long jLvProducerIndex = lvProducerIndex();
            long jLvConsumerIndex2 = lvConsumerIndex();
            if (jLvConsumerIndex == jLvConsumerIndex2) {
                return (int) (jLvProducerIndex - jLvConsumerIndex2);
            }
            jLvConsumerIndex = jLvConsumerIndex2;
        }
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean isEmpty() {
        return lvProducerIndex() == lvConsumerIndex();
    }

    private void adjustLookAheadStep(int capacity) {
        this.producerLookAheadStep = Math.min(capacity / 4, MAX_LOOK_AHEAD_STEP);
    }

    private long lvProducerIndex() {
        return this.producerIndex.get();
    }

    private long lvConsumerIndex() {
        return this.consumerIndex.get();
    }

    private long lpProducerIndex() {
        return this.producerIndex.get();
    }

    private long lpConsumerIndex() {
        return this.consumerIndex.get();
    }

    private void soProducerIndex(long v) {
        this.producerIndex.lazySet(v);
    }

    private void soConsumerIndex(long v) {
        this.consumerIndex.lazySet(v);
    }

    private static int calcWrappedOffset(long index, int mask) {
        return calcDirectOffset(((int) index) & mask);
    }

    private static void soElement(AtomicReferenceArray<Object> buffer, int offset, Object e) {
        buffer.lazySet(offset, e);
    }

    private static Object lvElement(AtomicReferenceArray<Object> buffer, int offset) {
        return buffer.get(offset);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean offer(T first, T second) {
        AtomicReferenceArray<Object> atomicReferenceArray = this.producerBuffer;
        long jLvProducerIndex = lvProducerIndex();
        int i = this.producerMask;
        long j = 2 + jLvProducerIndex;
        if (lvElement(atomicReferenceArray, calcWrappedOffset(j, i)) == null) {
            int iCalcWrappedOffset = calcWrappedOffset(jLvProducerIndex, i);
            soElement(atomicReferenceArray, iCalcWrappedOffset + 1, second);
            soElement(atomicReferenceArray, iCalcWrappedOffset, first);
            soProducerIndex(j);
            return true;
        }
        AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(atomicReferenceArray.length());
        this.producerBuffer = atomicReferenceArray2;
        int iCalcWrappedOffset2 = calcWrappedOffset(jLvProducerIndex, i);
        soElement(atomicReferenceArray2, iCalcWrappedOffset2 + 1, second);
        soElement(atomicReferenceArray2, iCalcWrappedOffset2, first);
        soNext(atomicReferenceArray, atomicReferenceArray2);
        soElement(atomicReferenceArray, iCalcWrappedOffset2, HAS_NEXT);
        soProducerIndex(j);
        return true;
    }
}
