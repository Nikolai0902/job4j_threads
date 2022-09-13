package ru.job4j.pool;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

@ThreadSafe
public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();

    @GuardedBy("this")
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public synchronized void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}