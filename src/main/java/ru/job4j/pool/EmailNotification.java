package ru.job4j.pool;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Сервис для рассылки почты.
 * Реализован через пакет concurrent в котором уже есть готовая реализация pool нитей.
 * Poll также создается по количеству доступных процессоров - availableProcessors().
 */
@ThreadSafe
public class EmailNotification {

    @GuardedBy("this")
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public synchronized void emailTo(User user) {
        pool.submit(() -> send(
                "Notification {" + user.username() + "} to email {" + user.email() + "}",
                "Add a new event to {" + user.username() + "}",
                user.email())
        );
    }

    /**
     * Закрываем pool и ждем пока все задачи завершатся.
     */
    public synchronized void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }
}
