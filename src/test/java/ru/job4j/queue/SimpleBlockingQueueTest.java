package ru.job4j.queue;

import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    public void testQueuePoll1() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread producer = new Thread(
                () -> {
                    queue.offer(1);
                    queue.offer(2);
                });
        Thread consumer = new Thread(
                () -> {
                    queue.poll();
                });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        Integer expected = queue.poll();
        assertThat(expected).isEqualTo(2);
    }

    @Test
    public void testQueuePoll2() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread consumer = new Thread(
                () -> {
                    queue.poll();
                });
        Thread producer = new Thread(
                () -> {
                    queue.offer(1);
                    queue.offer(2);
                });
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        Integer expected = queue.poll();
        assertThat(expected).isEqualTo(2);
    }
}