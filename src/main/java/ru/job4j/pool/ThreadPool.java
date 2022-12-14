package ru.job4j.pool;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * ThreadPool - группы рабочих потоков, которые выполняют задачи,
 * каждый поток можно использовать много раз. Если новая задача отправляется,
 * когда все потоки активны, они будут ждать в очереди,
 * пока поток не станет доступным.
 */
@ThreadSafe
public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    private static final Integer LIMIT_QUEUE = 10;
    @GuardedBy("this")
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(LIMIT_QUEUE);


    /**
     * Обработка задач из блокирующей очереди потоками из List<Thread> threads.
     * size - инициализация пула по количеству ядер в системе.
     */
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

    /**
     * Добавляет задачу в блокирующую очередь.
     * @param job - задача.
     * @throws InterruptedException - поток прерван.
     */
    public synchronized void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /**
     * Остановка запущенных потоков.
     */
    public synchronized void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}