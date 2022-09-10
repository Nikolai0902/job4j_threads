package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        if (this.queue.size() == this.limit) {
                wait();
        }
        this.queue.add(value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (this.queue.isEmpty()) {
                wait();
        }
        T result = this.queue.remove();
        notify();
        return result;
    }
}
