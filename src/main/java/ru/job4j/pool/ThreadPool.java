package ru.job4j.pool;

import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * ThreadPool - группы рабочих потоков, которые выполняют задачи,
 * каждый поток можно использовать много раз. Если новая задача отправляется,
 * когда все потоки активны, они будут ждать в очереди,
 * пока поток не станет доступным.
 */
public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);


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
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /**
     * Остановка запущенных потоков.
     */
    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}