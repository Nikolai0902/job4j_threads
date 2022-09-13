package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS - операции. Эти операции атомарные.
 * Обе нити не блокируются, а выполняются параллельно.
 */
@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int expected;
        do {
            expected = count.get();
        } while (!count.compareAndSet(expected, expected + 1));
    }

    public int get() {
        return count.get();
    }
}
