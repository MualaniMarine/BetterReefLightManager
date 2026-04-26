package io.reactivex.rxjava3.internal.queue;

import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class MpscLinkedQueue<T> implements SimplePlainQueue<T> {
    private final AtomicReference<LinkedQueueNode<T>> producerNode = new AtomicReference<>();
    private final AtomicReference<LinkedQueueNode<T>> consumerNode = new AtomicReference<>();

    public MpscLinkedQueue() {
        LinkedQueueNode<T> linkedQueueNode = new LinkedQueueNode<>();
        spConsumerNode(linkedQueueNode);
        xchgProducerNode(linkedQueueNode);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean offer(final T e) {
        if (e == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        LinkedQueueNode<T> linkedQueueNode = new LinkedQueueNode<>(e);
        xchgProducerNode(linkedQueueNode).soNext(linkedQueueNode);
        return true;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue, io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public T poll() {
        LinkedQueueNode<T> linkedQueueNodeLvNext;
        LinkedQueueNode<T> linkedQueueNodeLpConsumerNode = lpConsumerNode();
        LinkedQueueNode<T> linkedQueueNodeLvNext2 = linkedQueueNodeLpConsumerNode.lvNext();
        if (linkedQueueNodeLvNext2 != null) {
            T andNullValue = linkedQueueNodeLvNext2.getAndNullValue();
            spConsumerNode(linkedQueueNodeLvNext2);
            return andNullValue;
        }
        if (linkedQueueNodeLpConsumerNode == lvProducerNode()) {
            return null;
        }
        do {
            linkedQueueNodeLvNext = linkedQueueNodeLpConsumerNode.lvNext();
        } while (linkedQueueNodeLvNext == null);
        T andNullValue2 = linkedQueueNodeLvNext.getAndNullValue();
        spConsumerNode(linkedQueueNodeLvNext);
        return andNullValue2;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean offer(T v1, T v2) {
        offer(v1);
        offer(v2);
        return true;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public void clear() {
        while (poll() != null && !isEmpty()) {
        }
    }

    LinkedQueueNode<T> lvProducerNode() {
        return this.producerNode.get();
    }

    LinkedQueueNode<T> xchgProducerNode(LinkedQueueNode<T> node) {
        return this.producerNode.getAndSet(node);
    }

    LinkedQueueNode<T> lvConsumerNode() {
        return this.consumerNode.get();
    }

    LinkedQueueNode<T> lpConsumerNode() {
        return this.consumerNode.get();
    }

    void spConsumerNode(LinkedQueueNode<T> node) {
        this.consumerNode.lazySet(node);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean isEmpty() {
        return lvConsumerNode() == lvProducerNode();
    }

    static final class LinkedQueueNode<E> extends AtomicReference<LinkedQueueNode<E>> {
        private static final long serialVersionUID = 2404266111789071508L;
        private E value;

        LinkedQueueNode() {
        }

        LinkedQueueNode(E val) {
            spValue(val);
        }

        public E getAndNullValue() {
            E eLpValue = lpValue();
            spValue(null);
            return eLpValue;
        }

        public E lpValue() {
            return this.value;
        }

        public void spValue(E newValue) {
            this.value = newValue;
        }

        public void soNext(LinkedQueueNode<E> n) {
            lazySet(n);
        }

        public LinkedQueueNode<E> lvNext() {
            return get();
        }
    }
}
