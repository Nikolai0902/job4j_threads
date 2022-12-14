package ru.job4j.buffer;

import ru.job4j.queue.SimpleBlockingQueue;

/**
 * Остановка потока.
 * Для корректной остановки потока можно использовать метод класса Thread - interrupt().
 * и флаг осановки Thread.currentThread().isInterrupted().
 * При этом нужно обрабатывать исключение InterruptedException.
 * Если исклбчение воникает в классе очередей его нужно пробрасывать выше
 * - это нужно для контроля и обработки исключений в вышестоящем коде.
 */
public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(5);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    consumer.interrupt();
                }

        ).start();
    }
}
